package edu.ship.shipsim.areaserver.datasource;

import java.util.HashMap;

import datasource.DatabaseException;


/**
 * A Mock implementation of the data source layer for the quest table
 * @author merlin
 *
 */
public class QuestRowDataGatewayMock implements QuestRowDataGateway
{
	

	/**
	 * Map quest ID to questDescription
	 */
	private static HashMap<Integer, String> questInfo;
	private int questID;
	private String questDescription;

	
	/**
	 * Get the row data gateway object for an existing quest
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
			questDescription = questInfo.get(questID);
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
		questInfo = new HashMap<Integer, String>();
		for (QuestsForTest p : QuestsForTest.values())
		{
			questInfo.put(p.getQuestID(), p.getQuestDescription());
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

}
