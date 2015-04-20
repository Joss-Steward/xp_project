package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;
import java.util.Hashtable;

import datasource.DatabaseException;
import datasource.QuestStateEnum;

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

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestStateTableDataGateway#resetData()
	 */
	public void resetData()
	{
		data = new Hashtable<Integer, ArrayList<QuestStateRecord>>();
		for(QuestStatesForTest a:QuestStatesForTest.values())
		{
			QuestStateRecord rec = new QuestStateRecord(a.getPlayerID(), a.getQuestID(), a.getState(), a.isNeedingNotification());
			
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
		if (data.containsKey(playerID))
		{
			return data.get(playerID);
		} else
		{
			return new ArrayList<QuestStateRecord>();
		}
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
	 * @see edu.ship.shipsim.areaserver.datasource.QuestStateTableDataGateway#createRow(int, int, datasource.QuestStateEnum, boolean)
	 */
	@Override
	public void createRow(int playerID, int questID, QuestStateEnum state, boolean needingNotification)
			throws DatabaseException
	{
		insertRow(playerID, new QuestStateRecord(playerID, questID, state, needingNotification));
		
	}

	/**
	 * @throws DatabaseException shouldn't
	 * @see edu.ship.shipsim.areaserver.datasource.QuestStateTableDataGateway#udpateState(int, int, datasource.QuestStateEnum, boolean)
	 */
	@Override
	public void udpateState(int playerID, int questID, QuestStateEnum newState, boolean needingNotification) throws DatabaseException
	{
		boolean updated = false;
		ArrayList<QuestStateRecord> quests = data.get(playerID);
		if (quests == null)
		{
			quests = new ArrayList<QuestStateRecord>();
		}
		for (QuestStateRecord qsRec: quests)
		{
			if (qsRec.getQuestID() == questID)
			{
				updated = true;
				qsRec.setState(newState);
			}
		}
		if (!updated)
		{
			createRow(playerID, questID, newState, needingNotification);
		}
		
	}
	

}
