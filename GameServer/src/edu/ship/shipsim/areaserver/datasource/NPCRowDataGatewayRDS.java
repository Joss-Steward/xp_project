package edu.ship.shipsim.areaserver.datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import datasource.ClosingPreparedStatement;
import datasource.DatabaseException;
import datasource.DatabaseManager;

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
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"SELECT * FROM NPCs WHERE playerID = ?");
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
	 * 
	 * @param playerID
	 *            the NPC's playerID
	 * @param behaviorClass
	 *            the name of the class encoding the behavior for the NPC
	 * @throws DatabaseException
	 *             if we can't add the info to the database
	 */
	public NPCRowDataGatewayRDS(int playerID, String behaviorClass)
			throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"Insert INTO NPCs SET playerID = ?, behaviorClass = ?");
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
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"DROP TABLE IF EXISTS NPCs");
			stmt.executeUpdate();
			stmt.close();
			
			stmt = new ClosingPreparedStatement(connection,
					"Create TABLE NPCs (playerID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, behaviorClass VARCHAR(80))");
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

	/**
	 * Get gateways for all of the NPCs that are managed by a server managing a given map
	 * @param mapName the map we are interested in
	 * @return the NPCs
	 * @throws DatabaseException if we have trouble talking to the database
	 */
	public static ArrayList<NPCRowDataGateway> getNPCsForMap(String mapName)
			throws DatabaseException
	{
		ArrayList<NPCRowDataGateway> gateways = new ArrayList<NPCRowDataGateway>();

		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"SELECT * FROM PlayerConnection INNER JOIN NPCs ON NPCs.playerID = PlayerConnection.playerID");
			ResultSet result = stmt.executeQuery();
			while (result.next())
			{
				if (result.getString("mapName").equals(mapName))
				{
					gateways.add(new NPCRowDataGatewayRDS(result.getInt("NPCs.playerID")));
				}
			}

		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to retrieve NPCs for map named "
					+ mapName, e);
		}
		return gateways;
	}

}
