package edu.ship.shipsim.areaserver.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DatabaseManager;
import datasource.DatabaseException;
import datasource.QuestStateList;

/**
 * The RDS implementation of the gateway
 * @author Merlin
 *
 */
public class QuestStateTableDataGatewayRDS implements QuestStateTableDataGateway
{
	private static QuestStateTableDataGateway singleton;

	/**
	 * Retrieves the rds gateway singleton.
	 * 
	 * @return singleton
	 */
	public static synchronized QuestStateTableDataGateway getSingleton()
	{
		if (singleton == null)
		{
			singleton = new QuestStateTableDataGatewayRDS();
		}
		return singleton;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestStateTableDataGateway#getQuestStates(int)
	 */
	@Override
	public ArrayList<QuestStateRecord> getQuestStates(int playerID) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM QuestStates WHERE playerID = ?");
			stmt.setInt(1, playerID);
			ResultSet result = stmt.executeQuery();

			ArrayList<QuestStateRecord> results = new ArrayList<QuestStateRecord>();
			while (result.next())
			{
				QuestStateRecord rec = new QuestStateRecord(
						result.getInt("playerID"),
						result.getInt("questID"),
						convertToState(result.getInt("QuestState"))
						);
				results.add(rec);
			}
			return results;
		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't find quests for player ID " + playerID, e);
		}
	}

	private QuestStateList convertToState(int int1)
	{
		return QuestStateList.values()[int1];
	}
}
