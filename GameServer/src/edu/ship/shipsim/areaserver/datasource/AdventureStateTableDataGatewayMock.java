package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Mock version of the gateway to the table of adventure states.
 * @author merlin
 *
 */
public class AdventureStateTableDataGatewayMock implements AdventureStateTableDataGateway
{
	private static AdventureStateTableDataGateway singleton;
	
	/**
	 * Retrieves the mock gateway singleton.
	 * @return singleton
	 */
	public static synchronized AdventureStateTableDataGateway getSingleton()
	{
		if(singleton == null)
		{
			singleton = new AdventureStateTableDataGatewayMock();
		}
		return singleton;
	}

	private Hashtable<Key, ArrayList<AdventureStateRecord>> data;
	
	/**
	 * build the mock data from AdventuresForTest
	 */
	private AdventureStateTableDataGatewayMock()
	{
		data = new Hashtable<Key, ArrayList<AdventureStateRecord>>();
		for(AdventureStatesForTest a:AdventureStatesForTest.values())
		{
			AdventureStateRecord rec = new AdventureStateRecord(a.getPlayerID(), a.getQuestID(), a.getAdventureID(), a.getState());
			
			Key key = new Key (a.getPlayerID(), a.getQuestID());
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
	 * @see edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGateway#getAdventureStates(int, int)
	 */
	@Override
	public ArrayList<AdventureStateRecord> getAdventureStates(int playerID, int questID)
	{
		return data.get(new Key(playerID, questID));
	}
	
	private class Key
	{
		private int playerID;
		private int questID;
		
		public Key (int playerID, int questID)
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
				return false;;
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
	
}
