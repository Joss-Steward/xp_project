package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;
import java.util.Hashtable;

import datasource.AdventureStateEnum;
import datasource.DatabaseException;

/**
 * Mock version of the gateway to the table of adventure states.
 * 
 * @author merlin
 *
 */
public class AdventureStateTableDataGatewayMock implements AdventureStateTableDataGateway
{
	private static AdventureStateTableDataGateway singleton;

	/**
	 * Retrieves the mock gateway singleton.
	 * 
	 * @return singleton
	 */
	public static synchronized AdventureStateTableDataGateway getSingleton()
	{
		if (singleton == null)
		{
			singleton = new AdventureStateTableDataGatewayMock();
		}
		return singleton;
	}

	private Hashtable<Key, ArrayList<AdventureStateRecord>> data;
	private int maxQuestIDSeen;

	/**
	 * build the mock data from AdventuresForTest
	 */
	private AdventureStateTableDataGatewayMock()
	{
		resetData();
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGateway#resetData()
	 */
	public void resetData()
	{
		data = new Hashtable<Key, ArrayList<AdventureStateRecord>>();
		for (AdventureStatesForTest a : AdventureStatesForTest.values())
		{
			AdventureStateRecord rec = new AdventureStateRecord(a.getPlayerID(),
					a.getQuestID(), a.getAdventureID(), a.getState(), a.isNeedingNotification());
			if (a.getQuestID() > maxQuestIDSeen)
			{
				maxQuestIDSeen = a.getQuestID();
			}
			Key key = new Key(a.getPlayerID(), a.getQuestID());
			if (data.containsKey(key))
			{
				ArrayList<AdventureStateRecord> x = data.get(key);
				x.add(rec);
			} else
			{
				ArrayList<AdventureStateRecord> x = new ArrayList<AdventureStateRecord>();
				x.add(rec);
				data.put(key, x);
			}
		}
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGateway#getAdventureStates(int,
	 *      int)
	 */
	@Override
	public ArrayList<AdventureStateRecord> getAdventureStates(int playerID, int questID)
	{
		if (data.containsKey(new Key(playerID, questID)))
		{
			return data.get(new Key(playerID, questID));
		} else
		{
			return new ArrayList<AdventureStateRecord>();
		}
	}

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
			;
			if (playerID != other.playerID)
				return false;
			if (questID != other.questID)
				return false;
			return true;
		}

		private AdventureStateTableDataGatewayMock getOuterType()
		{
			return AdventureStateTableDataGatewayMock.this;
		}
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGateway#updateState(int,
	 *      int, int, datasource.AdventureStateEnum, boolean)
	 */
	@Override
	public void updateState(int playerID, int questID, int adventureID,
			AdventureStateEnum newState, boolean needingNotification) throws DatabaseException
	{
		if (data.containsKey(new Key(playerID, questID)))
		{
			ArrayList<AdventureStateRecord> advStates = data.get(new Key(playerID,
					questID));
			for (AdventureStateRecord asRec : advStates)
			{
				if ((asRec.getQuestID() == questID)
						&& (asRec.getAdventureID() == adventureID))
				{
					asRec.setState(newState);
					asRec.setNeedingNotification(needingNotification);
				}
			}
		} else
		{
			ArrayList<AdventureStateRecord> list = new ArrayList<AdventureStateRecord>();
			list.add(new AdventureStateRecord(playerID, questID, adventureID, newState, needingNotification));
			data.put(new Key(playerID, questID), list);
		}
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGateway#createTable()
	 */
	@Override
	public void createTable() throws DatabaseException
	{
		resetData();
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGateway#createRow(int,
	 *      int, int, datasource.AdventureStateEnum, boolean)
	 */
	@Override
	public void createRow(int playerID, int questID, int adventureID,
			AdventureStateEnum state, boolean needingNotification) throws DatabaseException
	{
		updateState(playerID, questID, adventureID, state, needingNotification);
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGateway#getPendingAdventuresForPlayer(int)
	 */
	@Override
	public ArrayList<AdventureStateRecord> getPendingAdventuresForPlayer(int playerID)
			throws DatabaseException
	{
		ArrayList<AdventureStateRecord> results = new ArrayList<AdventureStateRecord>();
		for (int questID = 0; questID <= maxQuestIDSeen; questID++)
		{
			if (data.containsKey(new Key(playerID, questID)))
			{
				ArrayList<AdventureStateRecord> adventureList = data.get(new Key(playerID, questID));
				for (AdventureStateRecord a:adventureList)
				{
					if (a.getState() == AdventureStateEnum.TRIGGERED)
					{
						results.add(a);
					}
				}
			} 
		}
		return results;
	}

}
