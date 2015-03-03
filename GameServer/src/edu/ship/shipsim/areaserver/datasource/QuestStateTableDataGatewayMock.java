package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;
import java.util.Hashtable;

import datasource.DatabaseException;
import datasource.QuestStateList;

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
		resetData();
	}

	private void resetData()
	{
		data = new Hashtable<Integer, ArrayList<QuestStateRecord>>();
		for(QuestStatesForTest a:QuestStatesForTest.values())
		{
			QuestStateRecord rec = new QuestStateRecord(a.getPlayerID(), a.getQuestID(), a.getState());
			
			try
			{
				insertRow(a.getPlayerID(), rec);
			} catch (DatabaseException e)
			{
				// QuestStatesForTest should not contain duplicate entries and
				// that is the only way we can get here
				e.printStackTrace();
			}
		}
	}

	private void insertRow(int playerID, QuestStateRecord rec) throws DatabaseException
	{
		if (data.containsKey(playerID))
		{
			ArrayList<QuestStateRecord> x = data.get(playerID);
			for (QuestStateRecord r:x)
			{
				if (r.getQuestID() == rec.getQuestID())
				{
					throw new DatabaseException("Duplicate quest state: player ID " +
							playerID + " and quest id " + rec.getQuestID());
				}
			}
			x.add(rec);
		} else
		{
			ArrayList<QuestStateRecord> x = new ArrayList<QuestStateRecord>();
			x.add(rec);
			data.put(playerID, x);
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

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestStateTableDataGateway#createTable()
	 */
	@Override
	public void createTable() throws DatabaseException
	{
		resetData();
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestStateTableDataGateway#createRow(int, int, datasource.QuestStateList)
	 */
	@Override
	public void createRow(int playerID, int questID, QuestStateList state)
			throws DatabaseException
	{
		insertRow(playerID, new QuestStateRecord(playerID, questID, state));
		
	}
	

}
