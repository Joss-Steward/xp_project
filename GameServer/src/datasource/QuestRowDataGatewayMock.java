package datasource;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

import testData.QuestsForTest;
import data.QuestCompletionActionParameter;
import data.QuestCompletionActionType;
import datasource.DatabaseException;
import datatypes.Position;

/**
 * A Mock implementation of the data source layer for the quest table
 * 
 * @author merlin
 *
 */
public class QuestRowDataGatewayMock implements QuestRowDataGateway
{

	private class QuestData
	{
		private String questTitle;
		private String questDescription;
		private String mapName;
		private Position position;
		private int questID;
		private int experiencePointsGained;
		private int adventuresForFulfillment;
		private Date startDate;
		private Date endDate;
		private QuestCompletionActionType completionActionType;
		private QuestCompletionActionParameter completionActionParameter;

		public QuestData(int questID, String questTitle, String questDescription, String mapName, Position position,
				int experiencePointsGained, int adventuresForFulfillment,
				QuestCompletionActionType completionActionType, QuestCompletionActionParameter completionActionParam,
				Date startDate, Date endDate)
		{
			this.questTitle = questTitle;
			this.questDescription = questDescription;
			this.mapName = mapName;
			this.position = position;
			this.questID = questID;
			this.experiencePointsGained = experiencePointsGained;
			this.adventuresForFulfillment = adventuresForFulfillment;
			this.completionActionType = completionActionType;
			this.completionActionParameter = completionActionParam;
			this.startDate = startDate;
			this.endDate = endDate;
		}

		public int getAdventuresForFulfillment()
		{
			return adventuresForFulfillment;
		}

		public QuestCompletionActionParameter getCompletionActionParameter()
		{
			return completionActionParameter;
		}

		public QuestCompletionActionType getCompletionActionType()
		{
			return completionActionType;
		}

		public int getExperiencePointsGained()
		{
			return experiencePointsGained;
		}

		public String getMapName()
		{
			return mapName;
		}

		public Position getPosition()
		{
			return position;
		}

		public String getQuestDescription()
		{
			return questDescription;
		}

		public int getQuestID()
		{
			return questID;
		}

		public String getQuestTitle()
		{
			return questTitle;
		}

		public Date getStartDate()
		{
			return startDate;
		}

		public Date getEndDate()
		{
			return endDate;
		}

	}

	/**
	 * Map quest ID to questDescription
	 */
	private static HashMap<Integer, QuestData> questInfo;

	/**
	 * Get the IDs of the quests that are supposed to trigger at a specified map
	 * location
	 * 
	 * @param mapName the name of the map
	 * @param position the position on the map
	 * @return the quest IDs
	 * @throws DatabaseException shouldn't
	 */
	public static ArrayList<Integer> findQuestsForMapLocation(String mapName, Position position)
			throws DatabaseException
	{
		if (questInfo == null)
		{
			new QuestRowDataGatewayMock(1).resetData();
		}
		ArrayList<Integer> results = new ArrayList<Integer>();
		for (QuestData q : questInfo.values())
		{
			if ((q.getMapName().equals(mapName)) && (q.getPosition().equals(position)))
			{
				results.add(q.getQuestID());
			}
		}
		return results;
	}

	private int questID;
	private String questDescription;

	private String triggerMapName;

	private Position triggerPosition;
	private int experiencePointsGained;
	private int adventuresForFulfillment;
	private QuestCompletionActionType completionActionType;
	private QuestCompletionActionParameter completionActionParameter;
	private String questTitle;
	private Date startDate;
	private Date endDate;

	/**
	 * Get the row data gateway object for an existing quest
	 * 
	 * @param questID the quest's unique ID
	 * @throws DatabaseException shouldn't
	 */
	public QuestRowDataGatewayMock(int questID) throws DatabaseException
	{
		if (questInfo == null)
		{
			resetData();
		}
		if (questInfo.containsKey(questID))
		{
			QuestData questData = questInfo.get(questID);
			this.questTitle = questData.getQuestTitle();
			this.questDescription = questData.getQuestDescription();
			this.triggerMapName = questData.getMapName();
			this.triggerPosition = questData.getPosition();
			this.questID = questID;
			this.experiencePointsGained = questData.getExperiencePointsGained();
			this.adventuresForFulfillment = questData.getAdventuresForFulfillment();
			this.completionActionType = questData.getCompletionActionType();
			this.completionActionParameter = questData.getCompletionActionParameter();
			this.startDate = questData.getStartDate();
			this.endDate = questData.getEndDate();
		} else
		{
			throw new DatabaseException("Couldn't find quest with ID " + questID);
		}
	}

	/**
	 * @see datasource.QuestRowDataGateway#getAdventuresForFulfillment()
	 */
	@Override
	public int getAdventuresForFulfillment()
	{
		return adventuresForFulfillment;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getCompletionActionParameter()
	 */
	@Override
	public QuestCompletionActionParameter getCompletionActionParameter()
	{
		return completionActionParameter;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getCompletionActionType()
	 */
	@Override
	public QuestCompletionActionType getCompletionActionType()
	{
		return completionActionType;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getExperiencePointsGained()
	 */
	@Override
	public int getExperiencePointsGained()
	{
		return experiencePointsGained;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getQuestDescription()
	 */
	@Override
	public String getQuestDescription()
	{
		return questDescription;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getQuestID()
	 */
	@Override
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getQuestTitle()
	 */
	@Override
	public String getQuestTitle()
	{
		return questTitle;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getTriggerMapName()
	 */
	@Override
	public String getTriggerMapName()
	{
		return triggerMapName;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getTriggerPosition()
	 */
	@Override
	public Position getTriggerPosition()
	{
		return triggerPosition;
	}

	/**
	 * @see datasource.QuestRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		questInfo = new HashMap<Integer, QuestData>();
		for (QuestsForTest p : QuestsForTest.values())
		{
			questInfo.put(p.getQuestID(), new QuestData(p.getQuestID(), p.getQuestTitle(), p.getQuestDescription(),
					p.getMapName(), p.getPosition(), p.getExperienceGained(), p.getAdventuresForFulfillment(),
					p.getCompletionActionType(), p.getCompletionActionParameter(), p.getStartDate(), p.getEndDate()));
		}
	}

	/**
	 * @return the first day the quest is available
	 */
	@Override
	public Date getStartDate()
	{
		return startDate;
	}

	/**
	 * @return the last day the quest is available
	 */
	@Override
	public Date getEndDate()
	{
		return endDate;
	}

}
