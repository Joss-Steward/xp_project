package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Mock version of the gateway to the table of adventures.
 * @author merlin
 *
 */
public class AdventureTableDataGatewayMock implements AdventureTableDataGateway
{
	private static AdventureTableDataGateway singleton;
	
	/**
	 * Retrieves the mock gateway singleton.
	 * @return singleton
	 */
	public static synchronized AdventureTableDataGateway getSingleton()
	{
		if(singleton == null)
		{
			singleton = new AdventureTableDataGatewayMock();
		}
		return singleton;
	}

	private Hashtable<Integer, ArrayList<AdventureRecord>> data;
	
	/**
	 * build the mock data from AdventuresForTest
	 */
	public AdventureTableDataGatewayMock()
	{
		data = new Hashtable<Integer, ArrayList<AdventureRecord>>();
		for(AdventuresForTest a:AdventuresForTest.values())
		{
			AdventureRecord rec = new AdventureRecord(a.getAdventureID(), a.getAdventureDescription(), a.getQuestID());
			
			if (data.containsKey(a.getQuestID()))
			{
				ArrayList<AdventureRecord> x = data.get(a.getQuestID());
				x.add(rec);
			} else
			{
				ArrayList<AdventureRecord> x = new ArrayList<AdventureRecord>();
				x.add(rec);
				data.put(a.getQuestID(), x);
			}
		}
	}
	
	/**
	 * @see edu.ship.shipsim.areaserver.datasource.AdventureTableDataGateway#getAdventuresForQuest(int)
	 */
	@Override
	public ArrayList<AdventureRecord> getAdventuresForQuest(int questID)
	{
		return data.get(questID);
	}
	
}
