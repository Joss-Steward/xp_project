package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import model.reports.KeyInputRecievedReport;
import model.reports.KnowledgePointsChangeReport;
import model.reports.PlayerLeaveReport;
import model.reports.PlayerMovedReport;
import model.reports.SendChatMessageReport;
import data.AdventureCompletionType;
import data.CriteriaString;
import data.CriteriaInteger;
import datasource.AdventureTableDataGateway;
import datasource.AdventureTableDataGatewayMock;
import datasource.AdventureTableDataGatewayRDS;
import datasource.DatabaseException;
import datasource.QuestRowDataGateway;
import datasource.QuestRowDataGatewayMock;
import datasource.QuestRowDataGatewayRDS;
import datatypes.AdventureStateEnum;
import datatypes.Position;
import datatypes.QuestStateEnum;

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
		QualifiedObservableConnector.getSingleton().registerObserver(this, PlayerMovedReport.class);
		QualifiedObservableConnector.getSingleton().registerObserver(this, PlayerLeaveReport.class);
		QualifiedObservableConnector.getSingleton().registerObserver(this, KeyInputRecievedReport.class);
		QualifiedObservableConnector.getSingleton().registerObserver(this, SendChatMessageReport.class);
		QualifiedObservableConnector.getSingleton().registerObserver(this, KnowledgePointsChangeReport.class);
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
	 * @param questID the quest to retrieve
	 * @return quest the retrieved request
	 * @throws DatabaseException throw an exception if the quest id isn't found
	 */
	public QuestRecord getQuest(int questID) throws DatabaseException
	{

		QuestRowDataGateway questGateway;

		if (OptionsManager.getSingleton().isTestMode())
		{
			questGateway = new QuestRowDataGatewayMock(questID);
		} else
		{
			questGateway = new QuestRowDataGatewayRDS(questID);
		}

		QuestRecord quest = new QuestRecord(questGateway.getQuestID(), questGateway.getQuestTitle(),
				questGateway.getQuestDescription(), questGateway.getTriggerMapName(), questGateway.getTriggerPosition(),
				adventureGateway.getAdventuresForQuest(questID), questGateway.getExperiencePointsGained(),
				questGateway.getAdventuresForFulfillment(), questGateway.getCompletionActionType(),
				questGateway.getCompletionActionParameter(), questGateway.getStartDate(), questGateway.getEndDate());

		return quest;
	}

	/**
	 * Return the quests that are associated with a certain map and position
	 *
	 * @param pos the position of the quest
	 * @param mapName the map that the quest is on
	 * @return an array list of questIDs
	 * @throws DatabaseException shouldn't
	 */
	public ArrayList<Integer> getQuestsByPosition(Position pos, String mapName) throws DatabaseException
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
	 * Returns a list of AdventureRecord objects based on completion at
	 * specified map and position
	 *
	 * @param pos - the position of the adventure
	 * @param mapName - the map that the adventure is on
	 * @return an array list of AdventureRecords at this position
	 * @throws DatabaseException shouldn't
	 */
	public ArrayList<AdventureRecord> getAdventuresByPosition(Position pos, String mapName) throws DatabaseException
	{
		return adventureGateway.findAdventuresCompletedForMapLocation(mapName, pos);
	}

	/**
	 * Trigger a quest for a given player
	 *
	 * @param playerID the player
	 * @param questID the quest to be triggered
	 * @throws IllegalAdventureChangeException thrown if changing to a wrong
	 *             state
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 * @throws DatabaseException shouldn't
	 */
	public void triggerQuest(int playerID, int questID)
			throws IllegalAdventureChangeException, IllegalQuestChangeException, DatabaseException
	{
		QuestState qs = getQuestStateByID(playerID, questID);
		Calendar.getInstance().set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
		Date now = Calendar.getInstance().getTime();
		QuestRecord q = getQuest(questID);

		if ((qs != null) && (qs.getStateValue() != QuestStateEnum.TRIGGERED))
		{
			if (now.after(q.getStartDate()) && now.before(q.getEndDate()))
			{
				qs.trigger();

			}
		}
	}

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		if (report.getClass() == PlayerMovedReport.class)
		{
			handlePlayerMovement(report);
		} else if (report.getClass() == PlayerLeaveReport.class)
		{
			PlayerLeaveReport myReport = (PlayerLeaveReport) report;
			removeQuestStatesForPlayer(myReport.getPlayerID());
		} else if (report.getClass() == KeyInputRecievedReport.class)
		{
			handlePlayerInput(report);
		} else if (report.getClass() == SendChatMessageReport.class)
		{
			handlePlayerChatCriteriaCompletion(report);
		} else if (report.getClass() == KnowledgePointsChangeReport.class)
		{
			handleKnowledgePointsChanged(report);
		}
	}

	/**
	 * Iterates through all quests and adventures and validates if it's the
	 * correct criteria for keyboard input.
	 *
	 * @param report
	 */
	private void handlePlayerInput(QualifiedObservableReport report)
	{
		QuestManager qm = QuestManager.getSingleton();
		KeyInputRecievedReport myReport = (KeyInputRecievedReport) report;
		ArrayList<QuestState> questStates = qm.getQuestList(myReport.getPlayerId());
		if (questStates != null)
		{
			for (QuestState qs : questStates)
			{
				for (AdventureState as : qs.getAdventureList())
				{

					validateInputCriteriaForAdventures(myReport.getInput(), myReport.getPlayerId(), qs.getID(),
							as.getID());
				}
			}
		}
	}

	/**
	 * Will listen for SendChatMessageReports and check to see if they help
	 * complete any of the current adventure that a player is doing Adventure
	 * must be off AdventureCompletion type chat and the players must be within
	 * a certain distance of each other
	 *
	 */
	private void handlePlayerChatCriteriaCompletion(QualifiedObservableReport report)
	{
		QuestManager qm = QuestManager.getSingleton();
		SendChatMessageReport myReport = (SendChatMessageReport) report;
		PlayerManager PM = PlayerManager.getSingleton();

		try
		{
			int reportPlayerID = PM.getPlayerIDFromPlayerName(myReport.getSenderName());
			ArrayList<QuestState> questStateList = qm.getQuestList(reportPlayerID);

			if (questStateList != null)
			{
				for (QuestState q : questStateList)
				{
					checkAllAdventuresForCompletion(reportPlayerID, q);
				}
			}
		} catch (PlayerNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 *
	 * @param report
	 */
	private void handleKnowledgePointsChanged(QualifiedObservableReport report)
	{
		QuestManager qm = QuestManager.getSingleton();
		KnowledgePointsChangeReport myReport = (KnowledgePointsChangeReport) report;
		PlayerManager PM = PlayerManager.getSingleton();

		ArrayList<QuestState> questStates = qm.getQuestList(myReport.getPlayerID());

		ArrayList<AdventureRecord> adventuresForCompletion = new ArrayList<AdventureRecord>();

		for (QuestState q : questStates)
		{
			adventuresForCompletion.addAll(getAdventuresByKnowledgePoints(q.getID(), q.getPlayerID()));
		}

		try
		{
			if (adventuresForCompletion.size() != 0)
			{
				for (AdventureRecord a : adventuresForCompletion)
				{
					CriteriaInteger criteria = (CriteriaInteger) a.getCompletionCriteria();

					if (criteria.getTarget() <= PM.getPlayerFromID(myReport.getPlayerID()).getQuizScore())
					{
						qm.completeAdventure(myReport.getPlayerID(), a.getQuestID(), a.getAdventureID());
					}
				}
			}
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * checks if adventure meets chat criteria and completes it if it does
	 *
	 * @param reportPlayerID player who sent chat message
	 * @param q quest to get adventure for
	 * @throws PlayerNotFoundException
	 */
	private void checkAllAdventuresForCompletion(int reportPlayerID, QuestState q) throws PlayerNotFoundException
	{
		PlayerManager PM = PlayerManager.getSingleton();
		for (AdventureRecord a : getPendingChatAdventures(q.getID(), reportPlayerID))
		{
			Player npc = PM.getPlayerFromID(PM.getPlayerIDFromPlayerName(a.getCompletionCriteria().toString()));
			if (PM.getPlayerFromID(reportPlayerID).canReceiveLocalMessage(npc.getPlayerPosition()))
			{
				try
				{
					QuestManager.getSingleton().completeAdventure(reportPlayerID, q.getID(), a.getAdventureID());

				} catch (DatabaseException | IllegalAdventureChangeException | IllegalQuestChangeException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param questID the id of the quest we're checking
	 * @param reportPlayerId the player's id
	 * @return the list of points adventures ready for completion
	 */
	public ArrayList<AdventureRecord> getAdventuresByKnowledgePoints(int questID, int reportPlayerId)
	{
		ArrayList<AdventureRecord> questAdventures = new ArrayList<AdventureRecord>();
		ArrayList<AdventureRecord> pointsAdventures = new ArrayList<AdventureRecord>();

		try
		{
			questAdventures = QuestManager.getSingleton().getQuest(questID).getAdventures();
			if (questAdventures.size() != 0)
			{
				for (AdventureRecord A : questAdventures)
				{
					AdventureState currentState = QuestManager.getSingleton().getAdventureStateByID(reportPlayerId,
							questID, A.getAdventureID());

					if (A.getCompletionType() == AdventureCompletionType.KNOWLEDGE_POINTS && currentState != null
							&& currentState.getState() == AdventureStateEnum.TRIGGERED)
					{
						pointsAdventures.add(A);
					}
				}
			}
		}

		catch (DatabaseException e)
		{
			e.printStackTrace();
		}

		return pointsAdventures;
	}

	/**
	 * returns all AdventureRecords which the type is chat
	 *
	 * @param questID the quest to get adventures for
	 * @param reportPlayerID the player who sent a chat message
	 * @return all adventures that have completionType chat
	 */
	public ArrayList<AdventureRecord> getPendingChatAdventures(int questID, int reportPlayerID)
	{
		ArrayList<AdventureRecord> questAdventures = new ArrayList<AdventureRecord>();
		try
		{

			for (AdventureRecord AR : getQuest(questID).getAdventures())
			{
				AdventureState adventureState = getAdventureStateByID(reportPlayerID, questID, AR.getAdventureID());
				if (adventureState != null)
				{
					AdventureStateEnum currentAdventuresState = adventureState.getState();
					if (AdventureStateEnum.TRIGGERED == currentAdventuresState)
					{
						if (AR.getCompletionType() == AdventureCompletionType.CHAT)
						{
							questAdventures.add(AR);
						}
					}
				}
			}
		} catch (DatabaseException e)
		{
			e.printStackTrace();
		}

		return questAdventures;
	}

	/**
	 * Validates if an input string matches the criteria string.
	 *
	 * @param input
	 * @param playerId
	 * @param questId
	 * @param adventureId
	 */
	private void validateInputCriteriaForAdventures(String input, int playerId, int questId, int adventureId)
	{
		QuestManager qm = QuestManager.getSingleton();
		try
		{
			AdventureRecord ar = getAdventure(questId, adventureId);
			if (ar.getCompletionType() == AdventureCompletionType.KEYSTROKE && qm
					.getAdventureStateByID(playerId, questId, adventureId).getState() == AdventureStateEnum.TRIGGERED)
			{
				CriteriaString cs = (CriteriaString) ar.getCompletionCriteria();
				if (cs.toString().equalsIgnoreCase(input))
				{
					qm.completeAdventure(playerId, questId, adventureId);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void handlePlayerMovement(QualifiedObservableReport report)
	{
		PlayerMovedReport myReport = (PlayerMovedReport) report;
		try
		{
			QuestManager qm = QuestManager.getSingleton();
			ArrayList<Integer> questIDs = new ArrayList<Integer>();
			questIDs = qm.getQuestsByPosition(myReport.getNewPosition(), myReport.getMapName());

			for (Integer q : questIDs)
			{
				this.triggerQuest(myReport.getPlayerID(), q);
			}

			ArrayList<AdventureRecord> adventures = getAdventuresByPosition(myReport.getNewPosition(),
					myReport.getMapName());
			for (AdventureRecord a : adventures)
			{
				AdventureState adventureStateByID = getAdventureStateByID(myReport.getPlayerID(), a.getQuestID(),
						a.getAdventureID());
				if (adventureStateByID != null && adventureStateByID.getState() != AdventureStateEnum.COMPLETED)
				{
					this.completeAdventure(myReport.getPlayerID(), a.getQuestID(), a.getAdventureID());
				}
			}

		} catch (DatabaseException | IllegalAdventureChangeException | IllegalQuestChangeException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Add a quest to the player's questList
	 *
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
	 * @param playerID the id of the player
	 * @param questID the id of the quest we are interested in
	 * @return the state of the quest
	 */
	QuestState getQuestStateByID(int playerID, int questID)
	{

		ArrayList<QuestState> questStateList = QuestManager.getSingleton().getQuestList(playerID);
		QuestRecord quest = null;
		try
		{
			quest = QuestManager.getSingleton().getQuest(questID);
		} catch (DatabaseException e)
		{
			e.printStackTrace();
		}

		QuestState qState = null;
		for (QuestState q : questStateList)
		{
			if (q.getID() == questID)
			{
				qState = q;
			}
		}

		if (quest != null && qState != null)
		{
			Date now = Calendar.getInstance().getTime();
			if (now.after(quest.getEndDate()) && qState.getStateValue() != QuestStateEnum.EXPIRED
					&& qState.getStateValue() != QuestStateEnum.COMPLETED)
			{
				try
				{
					qState.changeState(QuestStateEnum.EXPIRED, qState.isNeedingNotification());
				} catch (IllegalQuestChangeException | DatabaseException e)
				{
					e.printStackTrace();
				}
			}
		}

		return qState;
	}

	/**
	 * Use the id's of the player quest and adventure to find a specified
	 * adventure state
	 *
	 * @param playerID the id of the player
	 * @param questID the id of the quest
	 * @param adventureID the id of the adventure
	 * @return the state of the adventurenew Grego
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
	 * @param playerID the player's id
	 * @return the states
	 */
	public ArrayList<QuestState> getQuestList(int playerID)
	{
		return questStates.get(playerID);
	}

	/**
	 * Remove all of the quest states for a given player
	 *
	 * @param playerID the player we are removing
	 */
	public void removeQuestStatesForPlayer(int playerID)
	{
		questStates.remove(playerID);
	}

	/**
	 * Get the information about a specific adventure
	 *
	 * @param questID the quest that contains the adventure
	 * @param adventureID the adventure ID within that quest
	 * @return the requested details
	 * @throws DatabaseException if the data source can't respond well
	 */
	public AdventureRecord getAdventure(int questID, int adventureID) throws DatabaseException
	{
		return this.adventureGateway.getAdventure(questID, adventureID);
	}

	/**
	 * If quest state is not null, finish
	 *
	 * @param playerID the player ID
	 * @param questID the quest ID
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 * @throws DatabaseException shouldn't
	 */
	public void finishQuest(int playerID, int questID) throws IllegalQuestChangeException, DatabaseException
	{
		QuestState qs = getQuestStateByID(playerID, questID);
		if (qs != null)
		{
			qs.complete();
		}
	}

	/**
	 * Get the adventure state from the id's and set the adventure state to
	 * complete if it is pending
	 *
	 * @param playerID the id of the player
	 * @param questID the id of the quest
	 * @param adventureID the id of the adventure
	 * @throws DatabaseException shouldn't
	 * @throws IllegalAdventureChangeException thrown if changing to a wrong
	 *             state
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 */
	public void completeAdventure(int playerID, int questID, int adventureID)
			throws DatabaseException, IllegalAdventureChangeException, IllegalQuestChangeException
	{
		// QuestState qs =
		// QuestManager.getSingleton().getQuestStateByID(playerID, questID);
		AdventureState adventureStateByID = getAdventureStateByID(playerID, questID, adventureID);
		if (adventureStateByID != null)
		{
			adventureStateByID.complete();
		}

	}

	/**
	 * Set needing notification to false
	 *
	 * @param playerID the id of the player
	 * @param questID the id of the quest
	 * @param adventureID the id of the adventure
	 * @throws DatabaseException shouldn't
	 * @throws IllegalAdventureChangeException thrown if changing to a wrong
	 *             state
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 */
	public void turnOffNotification(int playerID, int questID, int adventureID)
			throws DatabaseException, IllegalAdventureChangeException, IllegalQuestChangeException
	{
		getAdventureStateByID(playerID, questID, adventureID).turnOffNotification();
	}

	/**
	 * Called when the user no longer needs to be notified about the current
	 * state of a quest
	 * 
	 * @param playerID the player
	 * @param questID the quest that should be quieted
	 */
	public void turnOffQuestNotification(int playerID, int questID)
	{
		QuestState qs = getQuestStateByID(playerID, questID);
		qs.setNeedingNotification(false);
	}
}
