package datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.QuestStateEnum;
import data.QuestStateRecord;
import datasource.ClosingPreparedStatement;
import datasource.DatabaseException;
import datasource.DatabaseManager;

/**
 * The RDS implementation of the gateway
 * 
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

	private void checkForDuplicateEntry(int playerID, int questID)
			throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"SELECT * FROM QuestStates WHERE playerID = ? and questID = ?");
			stmt.setInt(1, playerID);
			stmt.setInt(2, questID);
			ResultSet result = stmt.executeQuery();

			if (result.next())
			{
				throw new DatabaseException("Duplicate quest state for player ID "
						+ playerID + " and quest id " + questID);
			}
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find quests for player ID " + playerID,
					e);
		}
	}

	private QuestStateEnum convertToState(int int1)
	{
		return QuestStateEnum.values()[int1];
	}

	/**
	 * Add a new row to the table
	 * 
	 * @param playerID
	 *            the player
	 * @param questID
	 *            the quest
	 * @param state
	 *            the player's state in that quest
	 * @param needingNotification
	 *            true if the player should be notified about this state
	 * @throws DatabaseException
	 *             if we can't talk to the RDS server
	 */
	public void createRow(int playerID, int questID, QuestStateEnum state,
			boolean needingNotification) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		checkForDuplicateEntry(playerID, questID);
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(
					connection,
					"Insert INTO QuestStates SET playerID = ?, questID = ?, questState = ?, needingNotification = ?");
			stmt.setInt(1, playerID);
			stmt.setInt(2, questID);
			stmt.setInt(3, state.getID());
			stmt.setBoolean(4, needingNotification);
			stmt.executeUpdate();

		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't create a quest state record for player with ID " + playerID
							+ " and quest with ID " + questID, e);
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
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"DROP TABLE IF EXISTS QuestStates");
			stmt.executeUpdate();
			stmt.close();

			stmt = new ClosingPreparedStatement(
					connection,
					"Create TABLE QuestStates (playerID INT NOT NULL, questID INT NOT NULL , questState INT NOT NULL, needingNotification BOOLEAN NOT NULL)");
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the Quest table", e);
		}
	}

	/**
	 * @see datasource.QuestStateTableDataGateway#getQuestStates(int)
	 */
	@Override
	public ArrayList<QuestStateRecord> getQuestStates(int playerID)
			throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"SELECT * FROM QuestStates WHERE playerID = ?");
			stmt.setInt(1, playerID);
			ResultSet result = stmt.executeQuery();

			ArrayList<QuestStateRecord> results = new ArrayList<QuestStateRecord>();
			while (result.next())
			{
				QuestStateRecord rec = new QuestStateRecord(result.getInt("playerID"),
						result.getInt("questID"),
						convertToState(result.getInt("QuestState")),
						result.getBoolean("needingNotification"));
				results.add(rec);
			}
			return results;
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find quests for player ID " + playerID,
					e);
		}
	}

	/**
	 * @see datasource.QuestStateTableDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		// Nothing required
	}

	/**
	 * @see datasource.QuestStateTableDataGateway#udpateState(int,
	 *      int, data.QuestStateEnum, boolean)
	 */
	@Override
	public void udpateState(int playerID, int questID, QuestStateEnum newState,
			boolean needingNotification) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(
					connection,
					"UPDATE QuestStates SET questState = ?, needingNotification = ? WHERE  playerID = ? and questID = ?");
			stmt.setInt(1, newState.getID());
			stmt.setBoolean(2, needingNotification);
			stmt.setInt(3, playerID);
			stmt.setInt(4, questID);
			int count = stmt.executeUpdate();
			if (count == 0)
			{
				this.createRow(playerID, questID, newState, needingNotification);
			}
		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't update a quest state record for player with ID " + playerID
							+ " and quest with ID " + questID, e);
		}
	}

}
