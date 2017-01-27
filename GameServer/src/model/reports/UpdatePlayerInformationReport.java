package model.reports;

import java.util.ArrayList;

import model.AdventureRecord;
import model.AdventureState;
import model.IllegalQuestChangeException;
import model.LevelManager;
import model.Player;
import model.QualifiedObservableReport;
import model.QuestRecord;
import model.QuestManager;
import model.QuestState;
import data.ClientPlayerAdventureState;
import data.ClientPlayerQuestState;
import datasource.DatabaseException;
import datasource.LevelRecord;

/**
 * Report that combines Quest descriptions and Quest states
 * 
 * @author Ryan, LaVonne, Olivia
 *
 */
public class UpdatePlayerInformationReport implements QualifiedObservableReport
{

	private ArrayList<ClientPlayerQuestState> clientPlayerQuestList = new ArrayList<ClientPlayerQuestState>();
	private int experiencePoints;
	private LevelRecord level;
	private int playerID;

	/**
	 * Combine the player's quest state and quest descriptions Sets local
	 * experience points equal to player's experience points
	 * 
	 * @param player the player
	 * @throws DatabaseException shouldn't
	 * @throws IllegalQuestChangeException When the quest's state is changing to
	 *             an illegal state
	 */
	public UpdatePlayerInformationReport(Player player) throws DatabaseException, IllegalQuestChangeException
	{
		combineQuest(QuestManager.getSingleton().getQuestList(player.getPlayerID()));
		this.experiencePoints = player.getExperiencePoints();
		this.level = LevelManager.getSingleton().getLevelForPoints(experiencePoints);
		this.playerID = player.getPlayerID();
	}

	/**
	 * Combines a quest description and state and adds them to
	 * clientPlayerQuestList
	 * 
	 * @throws DatabaseException
	 * @throws IllegalQuestChangeException shouldn't
	 */
	private void combineQuest(ArrayList<QuestState> questStateList)
			throws DatabaseException, IllegalQuestChangeException
	{
		if (questStateList != null)
		{
			for (QuestState qs : questStateList)
			{
				// if (qs.getStateValue() != QuestStateEnum.AVAILABLE
				// && qs.getStateValue() != QuestStateEnum.HIDDEN)
				// {
				int questID = qs.getID();
				QuestRecord quest = QuestManager.getSingleton().getQuest(questID);

				ClientPlayerQuestState clientQuest = new ClientPlayerQuestState(quest.getQuestID(), quest.getTitle(),
						quest.getDescription(), qs.getStateValue(), quest.getExperiencePointsGained(),
						quest.getAdventuresForFulfillment(), qs.isNeedingNotification(), quest.getEndDate());
				clientQuest.setAdventures(combineAdventure(quest, qs));

				clientPlayerQuestList.add(clientQuest);
			}
			// }
		}
	}

	private ArrayList<ClientPlayerAdventureState> combineAdventure(QuestRecord quest, QuestState qs)
	{
		ArrayList<ClientPlayerAdventureState> ca = new ArrayList<ClientPlayerAdventureState>();
		for (AdventureState a : qs.getAdventureList())
		{
			int adventureID = a.getID();
			AdventureRecord adventure = quest.getAdventureD(adventureID);
			ca.add(new ClientPlayerAdventureState(a.getID(), adventure.getAdventureDescription(),
					adventure.getExperiencePointsGained(), a.getState(), a.isNeedingNotification(),
					adventure.isRealLifeAdventure(), adventure.getCompletionCriteria().toString(), qs.getStateValue()));
		}

		return ca;
	}

	/**
	 * Return ArrayList of Client Player Quests
	 * 
	 * @return clientPlayerQuestList
	 */
	public ArrayList<ClientPlayerQuestState> getClientPlayerQuestList()
	{
		return clientPlayerQuestList;
	}

	/**
	 * Return int of Player's experience points
	 * 
	 * @return experiencePoints
	 */
	public int getExperiencePts()
	{
		return experiencePoints;
	}

	/**
	 * Returns the Player's level
	 * 
	 * @return level
	 */
	public LevelRecord getLevel()
	{
		return level;
	}

	/**
	 * @return this player's playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}
}
