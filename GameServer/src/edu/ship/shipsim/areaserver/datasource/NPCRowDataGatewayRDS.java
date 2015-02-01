package edu.ship.shipsim.areaserver.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DatabaseException;
import model.DatabaseManager;

/**
 * The RDS version of the gateway
 * 
 * @author Merlin
 *
 */
public class NPCRowDataGatewayRDS implements NPCRowDataGateway
{

	private int playerID;
	private String behaviorClass;
	private Connection connection;

	/**
	 * finder constructor
	 * 
	 * @param playerID
	 *            the unique ID of the player we are working with
	 * @throws DatabaseException
	 *             if we cannot find that player in the database
	 */
	public NPCRowDataGatewayRDS(int playerID) throws DatabaseException
	{
		this.playerID = playerID;
		this.connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM NPCs WHERE playerID = ?");
			stmt.setInt(1, playerID);
			ResultSet result = stmt.executeQuery();
			result.next();
			this.behaviorClass = result.getString("behaviorClass");

		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find an NPC with ID " + playerID, e);
		}
	}

	/**
	 * Create Constructor
	 * @param playerID the NPC's playerID
	 * @param behaviorClass the name of the class encoding the behavior for the NPC
	 * @throws DatabaseException if we can't add the info to the database
	 */
	public NPCRowDataGatewayRDS(int playerID, String behaviorClass)
			throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("Insert INTO NPCs SET playerID = ?, behaviorClass = ?");
			stmt.setInt(1, playerID);
			stmt.setString(2, behaviorClass);
			stmt.executeUpdate();

			this.playerID = playerID;

		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't create a player record for player with ID " + playerID, e);
		}
	}

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
					.prepareStatement("DROP TABLE IF EXISTS NPCs");
			stmt.executeUpdate();

			stmt = connection
					.prepareStatement("Create TABLE NPCs (playerID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, behaviorClass VARCHAR(80))");
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the NPC table", e);
		}
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.NPCRowDataGateway#getBehaviorClass()
	 */
	@Override
	public String getBehaviorClass()
	{
		return behaviorClass;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.NPCRowDataGateway#getPlayerID()
	 */
	@Override
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.NPCRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
	}

}
