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
	/**
	 * Add a new row to the table
	 * @param playerID the player
	 * @param questID the quest
	 * @param state the player's state in that quest
	 * @throws DatabaseException if we can't talk to the RDS server
	 */
	public void createRow(int playerID, int questID, QuestStateList state) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		checkForDuplicateEntry(playerID, questID);
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("Insert INTO QuestStates SET playerID = ?, questID = ?, questState = ?");
			stmt.setInt(1, playerID);
			stmt.setInt(2, questID);
			stmt.setInt(3, state.ordinal());
			stmt.executeUpdate();

		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't create a quest state record for player with ID " + playerID
							+ " and quest with ID " + questID, e);
		}
	}

	private void checkForDuplicateEntry(int playerID, int questID) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM QuestStates WHERE playerID = ? and questID = ?");
			stmt.setInt(1, playerID);
			stmt.setInt(2, questID);
			ResultSet result = stmt.executeQuery();

			if (result.next())
			{
				throw new DatabaseException(
						"Duplicate quest state for player ID " + playerID + " and quest id " + questID);
			}
		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't find quests for player ID " + playerID, e);
		}
	}

	/**
	 * Drop the table if it exists and re-create it empty
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public void createTable() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("DROP TABLE IF EXISTS QuestStates");
			stmt.executeUpdate();

			stmt = connection
					.prepareStatement("Create TABLE QuestStates (playerID INT NOT NULL, questID INT NOT NULL , questState INT NOT NULL)");
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the Quest table", e);
		}
	}

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

	private static QuestStateTableDataGateway singleton;

	private QuestStateList convertToState(int int1)
	{
		return QuestStateList.values()[int1];
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

}
