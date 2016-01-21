package model;

import java.util.ArrayList;
import java.util.HashMap;

import model.OptionsManager;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.PlayerMovedReport;
import data.Position;
import datasource.AdventureRecord;
import datasource.AdventureTableDataGateway;
import datasource.AdventureTableDataGatewayMock;
import datasource.AdventureTableDataGatewayRDS;
import datasource.DatabaseException;
import datasource.QuestRowDataGateway;
import datasource.QuestRowDataGatewayMock;
import datasource.QuestRowDataGatewayRDS;

/**
 * Retrieves the list of quest and adventures from the database and sends them
 * to the PlayerManager?
 * 
 * @author lavonne
 */
public class QuestManager implements QualifiedObserver
{
	private AdventureTableDataGateway adventureGateway;
	private HashMap<Integer, ArrayList<QuestState>> questStates;

	/**
	 * The method returns a singleton of QuestManager
	 * 
	 * @return the only QuestManager in the system
	 */
	public synchronized static QuestManager getSingleton()
	{
		if (singleton == null)
		{
			singleton = new QuestManager();
		}

		return singleton;
	}

	private QuestManager()
	{
		QualifiedObservableConnector.getSingleton().registerObserver(this,
				PlayerMovedReport.class);
		questStates = new HashMap<Integer, ArrayList<QuestState>>();
		if (OptionsManager.getSingleton().isTestMode())
		{
			this.adventureGateway = new AdventureTableDataGatewayMock();
		} else
		{
			this.adventureGateway = new AdventureTableDataGatewayRDS();
		}
	}

	/**
	 * Reset the singleton to null
	 */
	public static void resetSingleton()
	{
		if (singleton != null)
		{
			singleton = null;
		}
	}

	private static QuestManager singleton;

	/**
	 * Gets a specific quest from the database. Creates a Quest and passes it on
	 * the player mapper?
	 * 
	 * @param questID
	 *            the quest to retrieve
	 * @return quest the retrieved request
	 * @throws DatabaseException
	 *             throw an exception if the quest id isn't found
	 */
	public Quest getQuest(int questID) throws DatabaseException
	{

		QuestRowDataGateway questGateway;

		if (OptionsManager.getSingleton().isTestMode())
		{
			questGateway = new QuestRowDataGatewayMock(questID);
		} else
		{
			questGateway = new QuestRowDataGatewayRDS(questID);
		}

		Quest quest = new Quest(questGateway.getQuestID(),
				questGateway.getQuestDescription(), questGateway.getTriggerMapName(),
				questGateway.getTriggerPosition(),
				adventureGateway.getAdventuresForQuest(questID),
				questGateway.getExperiencePointsGained(),
				questGateway.getAdventuresForFulfillment());

		return quest;
	}

	/**
	 * Return the quests that are associated with a certain map and position
	 * 
	 * @param pos
	 *            the position of the quest
	 * @param mapName
	 *            the map that the quest is on
	 * @return an array list of questIDs
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public ArrayList<Integer> getQuestsByPosition(Position pos, String mapName)
			throws DatabaseException
	{
		if (OptionsManager.getSingleton().isTestMode())
		{
			return QuestRowDataGatewayMock.findQuestsForMapLocation(mapName, pos);
		} else
		{
			return QuestRowDataGatewayRDS.findQuestsForMapLocation(mapName, pos);
		}
	}

	/**
	 * Trigger a quest for a given player
	 * 
	 * @param playerID
	 *            the player
	 * @param questID
	 *            the quest to be triggered
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public void triggerQuest(int playerID, int questID)
			throws IllegalAdventureChangeException, IllegalQuestChangeException,
			DatabaseException
	{
		QuestState qs = getQuestStateByID(playerID, questID);
		if (qs != null)
		{
			qs.trigger();
		}
	}

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		PlayerMovedReport myReport = (PlayerMovedReport) report;
		try
		{
			QuestManager qm = QuestManager.getSingleton();
			ArrayList<Integer> questIDs = new ArrayList<Integer>();
			questIDs = qm.getQuestsByPosition(myReport.getNewPosition(),
					myReport.getMapName());

			for (Integer q : questIDs)
			{
				this.triggerQuest(myReport.getPlayerID(), q);
			}
		} catch (DatabaseException | IllegalAdventureChangeException
				| IllegalQuestChangeException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Add a quest to the player's questList
	 * 
	 * @param playerID
	 *            the player we are adding the quest id
	 * @param quest
	 *            the quest being added
	 */
	public void addQuestState(int playerID, QuestState quest)
	{
		ArrayList<QuestState> questStateList;
		if (!questStates.containsKey(playerID))
		{
			questStateList = new ArrayList<QuestState>();
			questStates.put(playerID, questStateList);
		} else
		{
			questStateList = questStates.get(playerID);
		}
		questStateList.add(quest);
		quest.setPlayerID(playerID);
	}

	/**
	 * Go through the questList and get the state of the quest based on the id
	 * 
	 * @param playerID
	 *            the id of the player
	 * @param questID
	 *            the id of the quest we are interested in
	 * @return the state of the quest
	 */
	QuestState getQuestStateByID(int playerID, int questID)
	{
		ArrayList<QuestState> questStateList = questStates.get(playerID);
		for (QuestState q : questStateList)
		{
			if (q.getID() == questID)
			{
				return q;
			}
		}
		return null;
	}

	/**
	 * Use the id's of the player quest and adventure to find a specified
	 * adventure state
	 * 
	 * @param playerID
	 *            the id of the player
	 * @param questID
	 *            the id of the quest
	 * @param adventureID
	 *            the id of the adventure
	 * @return the state of the adventure
	 */
	AdventureState getAdventureStateByID(int playerID, int questID, int adventureID)
	{
		ArrayList<QuestState> questStateList = questStates.get(playerID);
		for (QuestState q : questStateList)
		{
			if (q.getID() == questID)
			{
				ArrayList<AdventureState> adventureList = q.getAdventureList();

				for (AdventureState a : adventureList)
				{
					if (a.getID() == adventureID)
					{
						return a;
					}
				}

			}
		}
		return null;
	}

	/**
	 * Get a list of all of the current quest states for a given player
	 * 
	 * @param playerID
	 *            the player's id
	 * @return the states
	 */
	public ArrayList<QuestState> getQuestList(int playerID)
	{
		return questStates.get(playerID);
	}

	/**
	 * Remove all of the quest states for a given player
	 * 
	 * @param playerID
	 *            the player we are removing
	 */
	public void removeQuestStatesForPlayer(int playerID)
	{
		questStates.remove(playerID);
	}

	/**
	 * Get the information about a specific adventure
	 * 
	 * @param questID
	 *            the quest that contains the adventure
	 * @param adventureID
	 *            the adventure ID within that quest
	 * @return the requested details
	 * @throws DatabaseException
	 *             if the data source can't respond well
	 */
	public AdventureRecord getAdventure(int questID, int adventureID)
			throws DatabaseException
	{
		return this.adventureGateway.getAdventure(questID, adventureID);
	}

	/**
	 * If quest state is not null, finish
	 * 
	 * @param playerID
	 *            the player ID
	 * @param questID
	 *            the quest ID
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public void finishQuest(int playerID, int questID)
			throws IllegalQuestChangeException, DatabaseException
	{
		QuestState qs = getQuestStateByID(playerID, questID);
		if (qs != null)
		{
			qs.finish();
		}
	}

	/**
	 * Get the adventure state from the id's and set the adventure state to
	 * complete if it is pending
	 * 
	 * @param playerID
	 *            the id of the player
	 * @param questID
	 *            the id of the quest
	 * @param adventureID
	 *            the id of the adventure
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 */
	public void completeAdventure(int playerID, int questID, int adventureID)
			throws DatabaseException, IllegalAdventureChangeException,
			IllegalQuestChangeException
	{
		getAdventureStateByID(playerID, questID, adventureID).complete();

	}

	/**
	 * Set needing notification to false
	 * 
	 * @param playerID
	 *            the id of the player
	 * @param questID
	 *            the id of the quest
	 * @param adventureID
	 *            the id of the adventure
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 */
	public void turnOffNotification(int playerID, int questID, int adventureID)
			throws DatabaseException, IllegalAdventureChangeException,
			IllegalQuestChangeException
	{
		getAdventureStateByID(playerID, questID, adventureID).turnOffNotification();
		PlayerManager.getSingleton().persistPlayer(playerID);
	}
}
