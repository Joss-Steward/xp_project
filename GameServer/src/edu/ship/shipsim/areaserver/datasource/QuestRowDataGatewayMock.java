package edu.ship.shipsim.areaserver.datasource;

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

	/**
	 * Map quest ID to questDescription
	 */
	private static HashMap<Integer, QuestData> questInfo;
	private int questID;
	private String questDescription;
	private String triggerMapName;
	private Position triggerPosition;

	private class QuestData
	{
		private String questDescription;

		public String getQuestDescription()
		{
			return questDescription;
		}

		public String getMapName()
		{
			return mapName;
		}

		public Position getPosition()
		{
			return position;
		}

		private String mapName;
		private Position position;

		public QuestData(String questDescription, String mapName, Position position)
		{
			this.questDescription = questDescription;
			this.mapName = mapName;
			this.position = position;
		}
	}

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
		} else
		{
			throw new DatabaseException("Couldn't find quest with ID " + questID);
		}
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
					new QuestData(p.getQuestDescription(), p.getMapName(), p
							.getPosition()));
		}
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
	 * @see edu.ship.shipsim.areaserver.datasource.QuestRowDataGateway#getQuestDescription()
	 */
	@Override
	public String getQuestDescription()
	{
		return questDescription;
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

}
