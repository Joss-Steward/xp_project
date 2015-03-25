package edu.ship.shipsim.areaserver.model;

import java.util.ArrayList;
import java.util.HashMap;

import model.OptionsManager;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import data.Position;
import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.datasource.AdventureRecord;
import edu.ship.shipsim.areaserver.datasource.AdventureTableDataGateway;
import edu.ship.shipsim.areaserver.datasource.AdventureTableDataGatewayMock;
import edu.ship.shipsim.areaserver.datasource.AdventureTableDataGatewayRDS;
import edu.ship.shipsim.areaserver.datasource.QuestRowDataGateway;
import edu.ship.shipsim.areaserver.datasource.QuestRowDataGatewayMock;
import edu.ship.shipsim.areaserver.datasource.QuestRowDataGatewayRDS;
import edu.ship.shipsim.areaserver.model.reports.PlayerMovedReport;

/**
 * Retrieves the list of quest and adventures from the database
 * and sends them to the PlayerManager?
 * @author lavonne
 */
public class QuestManager implements QualifiedObserver
{
	
	private QuestRowDataGateway questGateway;
	private AdventureTableDataGateway adventureGateway;
	private HashMap<Integer,ArrayList<QuestState>> questStates;
	

	/**
	 * The method returns a singleton of QuestManager
	 * @return the only QuestManager in the system
	 */
	public synchronized static QuestManager getSingleton()
	{
		if(singleton == null)
		{
			singleton = new QuestManager();
		}
		
		return singleton;
	}
	
	private QuestManager()
	{
		QualifiedObservableConnector.getSingleton().registerObserver(this, PlayerMovedReport.class);
		questStates = new HashMap<Integer, ArrayList<QuestState>>();
	}
	/**
	 * Reset the singleton to null
	 */
	public static void resetSingleton()
	{
		if(singleton != null)
		{
			singleton = null;
		}
	}
	
	private static QuestManager singleton;

	/**
	 * Gets a specific quest from the database. Creates a Quest and passes it on the player mapper?
	 * @param questID the quest to retrieve
	 * @return quest the retrieved request
	 * @throws DatabaseException throw an exception if the quest id isn't found
	 */
	public Quest getQuest(int questID) throws DatabaseException {
		
		if(OptionsManager.getSingleton().isTestMode())
		{
			this.questGateway = new QuestRowDataGatewayMock(questID);
			this.adventureGateway = new AdventureTableDataGatewayMock();
		} else
		{
			this.questGateway = new QuestRowDataGatewayRDS(questID);
			this.adventureGateway = new AdventureTableDataGatewayRDS();
		}
		
		ArrayList<Adventure> adventureList = new ArrayList<Adventure>();
				
		for(AdventureRecord ar : adventureGateway.getAdventuresForQuest(questID))
		{
			adventureList.add(new Adventure(ar.getAdventureID(), ar.getAdventureDescription()));
		}
		
		Quest quest = new Quest(questGateway.getQuestID(), questGateway.getQuestDescription(), null, null, adventureList);
		return quest;
	}

	/**
	 * Return the quests that are associated with a certain map and position
	 * @param pos the position of the quest
	 * @param mapName the map that the quest is on
	 * @return an array list of questIDs
	 * @throws DatabaseException shouldn't
	 */
	public ArrayList<Integer> getQuestsByPosition(Position pos, String mapName) throws DatabaseException {
		if(OptionsManager.getSingleton().isTestMode())
		{
			return QuestRowDataGatewayMock.findQuestsForMapLocation(mapName, pos);
		} else
		{
			return QuestRowDataGatewayRDS.findQuestsForMapLocation(mapName, pos);
		}
	}

	/**
	 * Trigger a quest for a given player
	 * @param playerID the player
	 * @param questID the quest to be triggered
	 */
	public void triggerQuest(int playerID, int questID)
	{
		QuestState qs = getQuestStateByID(playerID, questID);
		qs.trigger();
	}

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		PlayerMovedReport myReport = (PlayerMovedReport)report;
		try
		{
			QuestManager qm = QuestManager.getSingleton();
			ArrayList<Integer> questIDs = new ArrayList<Integer>();
			questIDs = qm.getQuestsByPosition(myReport.getNewPosition(), myReport.getMapName());
			
			for(Integer q : questIDs)
			{
				this.triggerQuest(myReport.getPlayerID(), q);
			}
		} 
		catch (DatabaseException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a quest to the player's questList
	 * @param playerID the player we are adding the quest id
	 * @param quest the quest being added
	 */
	public void addQuestState(int playerID, QuestState quest) 
	{
		ArrayList<QuestState> questStateList;
		if (!questStates.containsKey(playerID))
		{
			questStateList = new ArrayList<QuestState>();
			questStates.put(playerID, questStateList);
		}
		else
		{
			questStateList = questStates.get(playerID);
		}
		questStateList.add(quest);
	}

	/**
	 * Go through the questList and get the state of the quest based on the id
	 * @param playerID the id of the player
	 * @param questID the id of the quest we are interested in
	 * @return the state of the quest
	 */
	QuestState getQuestStateByID(int playerID, int questID) 
	{
		ArrayList<QuestState> questStateList = questStates.get(playerID);
		for(QuestState q : questStateList)
		{
			if(q.getID()==questID)
			{
				return q;
			}
		}
		return null;
	}

	/**
	 * Get a list of all of the current quest states for a given player
	 * @param playerID the player's id
	 * @return the states
	 */
	public ArrayList<QuestState> getQuestList(int playerID)
	{
		return questStates.get(playerID);
	}

	/**
	 * Remove all of the quest states for a given player
	 * @param playerID the player we are removing
	 */
	public void removeQuestStatesForPlayer(int playerID)
	{
		questStates.remove(playerID);
	}
}
