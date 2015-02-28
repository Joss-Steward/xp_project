package edu.ship.shipsim.areaserver.datasource;

import java.util.HashMap;

import datasource.DatabaseException;

/**
 * A Mock implementation of this data source that builds itself from
 * QuestStatesForTest
 * 
 * @author Merlin
 *
 */
public class QuestStateRowDataGatewayMock implements QuestStateRowDataGateway
{

	private static HashMap<Key, QuestState> questStateInfo;
	private int playerID;
	private int questID;
	private QuestState state;

	private class Key
	{
		private int playerID;
		private int questID;

		public Key(int playerID, int questID)
		{
			this.playerID = playerID;
			this.questID = questID;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + playerID;
			result = prime * result + questID;
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (playerID != other.playerID)
				return false;
			if (questID != other.questID)
				return false;
			return true;
		}

		private QuestStateRowDataGatewayMock getOuterType()
		{
			return QuestStateRowDataGatewayMock.this;
		}
	}

	/**
	 * Create one for a given player and questID. Note: for pair which are not
	 * in QuestStatesForTest, the quest will be hidden for that player
	 * 
	 * @param playerID
	 *            the player we are interested in
	 * @param questID
	 *            the ID of the quest we are interested in
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public QuestStateRowDataGatewayMock(int playerID, int questID)
			throws DatabaseException
	{
		this.questID = questID;
		this.playerID = playerID;
		if (questStateInfo == null)
		{
			resetData();
		}
		Key key = new Key(playerID, questID);
		if (questStateInfo.containsKey(key))
		{
			state = questStateInfo.get(key);

		} else
		{
			state = QuestState.HIDDEN;
		}
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestStateRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		questStateInfo = new HashMap<Key, QuestState>();
		for (QuestStatesForTest p : QuestStatesForTest.values())
		{
			questStateInfo.put(new Key(p.getPlayerID(), p.getQuestID()),
					p.getQuestState());
		}
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestStateRowDataGateway#getQuestID()
	 */
	@Override
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestStateRowDataGateway#getPlayerID()
	 */
	@Override
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * 
	 * @see edu.ship.shipsim.areaserver.datasource.QuestStateRowDataGateway#getQuestState()
	 */
	@Override
	public QuestState getQuestState()
	{
		return state;
	}

}
