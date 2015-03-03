package edu.ship.shipsim.areaserver.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DatabaseManager;
import datasource.AdventureStateList;
import datasource.DatabaseException;

/**
 * The RDS Implementation of this gateway
 * @author merlin
 *
 */
public class AdventureStateTableDataGatewayRDS implements AdventureStateTableDataGateway
{

	private static AdventureStateTableDataGateway singleton;

	/**
	 * Retrieves the rds gateway singleton.
	 * 
	 * @return singleton
	 */
	public static synchronized AdventureStateTableDataGateway getSingleton()
	{
		if (singleton == null)
		{
			singleton = new AdventureStateTableDataGatewayRDS();
		}
		return singleton;
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
					.prepareStatement("DROP TABLE IF EXISTS AdventureStates");
			stmt.executeUpdate();

			stmt = connection
					.prepareStatement("Create TABLE AdventureStates (adventureID INT NOT NULL, questID INT NOT NULL, playerID INT NOT NULL, adventureState INT)");
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the Adventure State table",
					e);
		}
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGateway#getAdventureStates(int, int)
	 */
	@Override
	public ArrayList<AdventureStateRecord> getAdventureStates(int playerID, int questID) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM AdventureStates WHERE questID = ? and playerID = ?");
			stmt.setInt(1, questID);
			stmt.setInt(2, playerID);
			ResultSet result = stmt.executeQuery();

			ArrayList<AdventureStateRecord> results = new ArrayList<AdventureStateRecord>();
			while (result.next())
			{
				AdventureStateRecord rec = new AdventureStateRecord(
						result.getInt("playerID"),
						result.getInt("questID"),
						result.getInt("adventureID"),
						convertToState(result.getInt("adventureState"))
						);
				results.add(rec);
			}
			return results;
		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't find adventures for quest ID " + questID, e);
		}
	}

	private AdventureStateList convertToState(int int1)
	{
		return AdventureStateList.values()[int1];
	}
	/**
	 * Create a new row in the table
	 * @param questID the quest that contains the adventure
	 * @param adventureID the unique ID of the adventure
	 * @param playerID the player ID
	 * @param adventureState the state of this adventure for this player
	 * @throws DatabaseException if we can't talk to the RDS 
	 */
	public static void createRow(int questID, int adventureID, int playerID, AdventureStateList adventureState) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("Insert INTO AdventureStates SET questID = ?, adventureID = ?, playerID = ?, adventureState = ?");
			stmt.setInt(1, questID);
			stmt.setInt(2, adventureID);
			stmt.setInt(3, playerID);
			stmt.setInt(4, adventureState.ordinal());
			stmt.executeUpdate();

		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't create a adventure state record for adventure with ID "
							+ adventureID, e);
		}
	}
}
