package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;
import java.util.HashMap;

import data.Position;
import datasource.DatabaseException;

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
		private String questDescription;

		private String mapName;

		private Position position;

		private int questID;

		private int experiencePointsGained;

		private int adventuresForFulfillment;

		public QuestData(int questID, String questDescription, String mapName,
				Position position, int experiencePointsGained,
				int adventuresForFulfillment)
		{
			this.questDescription = questDescription;
			this.mapName = mapName;
			this.position = position;
			this.questID = questID;
			this.experiencePointsGained = experiencePointsGained;
			this.adventuresForFulfillment = adventuresForFulfillment;
		}

		public int getExperiencePointsGained()
		{
			return experiencePointsGained;
		}

		public int getAdventuresForFulfillment()
		{
			return adventuresForFulfillment;
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

	}

	/**
	 * Get the IDs of the quests that are supposed to trigger at a specified map
	 * location
	 * 
	 * @param mapName
	 *            the name of the map
	 * @param position
	 *            the position on the map
	 * @return the quest IDs
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public static ArrayList<Integer> findQuestsForMapLocation(String mapName,
			Position position) throws DatabaseException
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

	/**
	 * Map quest ID to questDescription
	 */
	private static HashMap<Integer, QuestData> questInfo;
	private int questID;
	private String questDescription;

	private String triggerMapName;

	private Position triggerPosition;
	private int experiencePointsGained;
	private int adventuresForFulfillment;

	/**
	 * Get the row data gateway object for an existing quest
	 * 
	 * @param questID
	 *            the quest's unique ID
	 * @throws DatabaseException
	 *             shouldn't
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
			this.questDescription = questData.getQuestDescription();
			this.triggerMapName = questData.getMapName();
			this.triggerPosition = questData.getPosition();
			this.questID = questID;
			this.experiencePointsGained = questData.getExperiencePointsGained();
			this.adventuresForFulfillment = questData.getAdventuresForFulfillment();
		} else
		{
			throw new DatabaseException("Couldn't find quest with ID " + questID);
		}
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestRowDataGateway#getQuestDescription()
	 */
	@Override
	public String getQuestDescription()
	{
		return questDescription;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestRowDataGateway#getQuestID()
	 */
	@Override
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestRowDataGateway#getTriggerMapName()
	 */
	@Override
	public String getTriggerMapName()
	{
		return triggerMapName;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestRowDataGateway#getTriggerPosition()
	 */
	@Override
	public Position getTriggerPosition()
	{
		return triggerPosition;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		questInfo = new HashMap<Integer, QuestData>();
		for (QuestsForTest p : QuestsForTest.values())
		{
			questInfo.put(
					p.getQuestID(),
					new QuestData(p.getQuestID(), p.getQuestDescription(),
							p.getMapName(), p.getPosition(), p.getExperienceGained(), p
									.getAdventuersForFulfillment()));
		}
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestRowDataGateway#getAdventuresForFulfillment()
	 */
	@Override
	public int getAdventuresForFulfillment()
	{
		return adventuresForFulfillment;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestRowDataGateway#getExperiencePointsGained()
	 */
	@Override
	public int getExperiencePointsGained()
	{
		return experiencePointsGained;
	}

}
