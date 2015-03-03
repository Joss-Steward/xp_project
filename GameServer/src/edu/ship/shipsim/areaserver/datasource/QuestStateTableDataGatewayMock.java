package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * A mock implementation of the gateway
 * @author Merlin
 *
 */
public class QuestStateTableDataGatewayMock implements QuestStateTableDataGateway
{
private static QuestStateTableDataGateway singleton;
	
	/**
	 * Retrieves the mock gateway singleton.
	 * @return singleton
	 */
	public static synchronized QuestStateTableDataGateway getSingleton()
	{
		if(singleton == null)
		{
			singleton = new QuestStateTableDataGatewayMock();
		}
		return singleton;
	}

	private Hashtable<Integer, ArrayList<QuestStateRecord>> data;
	
	/**
	 * build the mock data from AdventuresForTest
	 */
	private QuestStateTableDataGatewayMock()
	{
		data = new Hashtable<Integer, ArrayList<QuestStateRecord>>();
		for(QuestStatesForTest a:QuestStatesForTest.values())
		{
			QuestStateRecord rec = new QuestStateRecord(a.getPlayerID(), a.getQuestID(), a.getState());
			
			if (data.containsKey(a.getPlayerID()))
			{
				ArrayList<QuestStateRecord> x = data.get(a.getPlayerID());
				x.add(rec);
			} else
			{
				ArrayList<QuestStateRecord> x = new ArrayList<QuestStateRecord>();
				x.add(rec);
				data.put(a.getPlayerID(), x);
			}
		}
	}
	
	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestStateTableDataGateway#getQuestStates(int)
	 */
	@Override
	public ArrayList<QuestStateRecord> getQuestStates(int playerID)
	{
		return data.get(playerID);
	}
	

}
