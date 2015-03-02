package edu.ship.shipsim.areaserver.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DatabaseManager;
import datasource.DatabaseException;

/**
 * An RDS implementation of the QuestRowDataGateway interface
 * 
 * @author merlin
 *
 */
public class QuestStateRowDataGatewayRDS implements QuestStateRowDataGateway
{

	/**
	 * Drop the table if it exists and re-create it empty
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public static void createTable() throws DatabaseException
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

	private int questID;
	private QuestStateList questState;
	private Connection connection;
	private int playerID;

	/**
	 * Finder constructor
	 * 
	 * @param playerID
	 *            the player we are interested in
	 * @param questID
	 *            the id of the quest this gateway will manage
	 * @throws DatabaseException
	 *             if we can't talk to the RDS server
	 */
	public QuestStateRowDataGatewayRDS(int playerID, int questID)
			throws DatabaseException
	{
		this.playerID = playerID;
		this.questID = questID;
		this.connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM QuestStates WHERE playerID = ? and questID = ?");
			stmt.setInt(1, playerID);
			stmt.setInt(2, questID);
			ResultSet result = stmt.executeQuery();
			result.next();
			this.questState = convertToState(result.getInt("questState"));
		} catch (SQLException e)
		{
			this.questState = QuestStateList.HIDDEN;
		}
	}

	private QuestStateList convertToState(int int1)
	{
		return QuestStateList.values()[int1];
	}

	/**
	 * Create constructor
	 * 
	 * @param playerID
	 *            the player's unique ID
	 * @param questID
	 *            the quest's unique ID
	 * @param state
	 *            this player's progress on the given quest
	 * @throws DatabaseException
	 *             if we can't talk to the RDS
	 */
	public QuestStateRowDataGatewayRDS(int playerID, int questID, QuestStateList state)
			throws DatabaseException
	{
		this.questID = questID;
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("Insert INTO QuestStates SET playerID = ?, questID = ?, questState = ?");
			stmt.setInt(1, playerID);
			stmt.setInt(2, questID);
			stmt.setInt(3, state.ordinal());
			stmt.executeUpdate();

			this.playerID = playerID;
			this.questID = questID;
			this.questState = state;

		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't create a quest state record for player with ID " + playerID
							+ " and quest with ID " + questID, e);
		}
	}

	/**
	 * Nothing to do in this version
	 * 
	 * @see edu.ship.shipsim.areaserver.datasource.QuestRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestRowDataGateway#getQuestID()
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
	 * @see edu.ship.shipsim.areaserver.datasource.QuestStateRowDataGateway#getQuestState()
	 */
	@Override
	public QuestStateList getQuestState()
	{
		return questState;
	}

}
