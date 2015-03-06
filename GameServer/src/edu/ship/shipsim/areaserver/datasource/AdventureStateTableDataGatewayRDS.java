package edu.ship.shipsim.areaserver.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DatabaseManager;
import datasource.AdventureStateEnum;
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
	public void createTable() throws DatabaseException
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

	private AdventureStateEnum convertToState(int int1)
	{
		return AdventureStateEnum.values()[int1];
	}
	/**
	 * Create a new row in the table
	 * @param playerID the player ID
	 * @param questID the quest that contains the adventure
	 * @param adventureID the unique ID of the adventure
	 * @param adventureState the state of this adventure for this player
	 * @throws DatabaseException if we can't talk to the RDS 
	 */
	public void createRow(int playerID, int questID, int adventureID, AdventureStateEnum adventureState) throws DatabaseException
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

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGateway#updateState(int, int, int, datasource.AdventureStateEnum)
	 */
	@Override
	public void updateState(int playerID, int questID, int adventureID,
			AdventureStateEnum newState) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("UPDATE AdventureStates SET adventureState = ? WHERE  playerID = ? and questID = ? and adventureID = ?");
			stmt.setInt(1, newState.ordinal());
			stmt.setInt(2, playerID);
			stmt.setInt(3, questID);
			stmt.setInt(4, adventureID);
			int count = stmt.executeUpdate();
			if ( count == 0)
			{
				this.createRow(playerID, questID, adventureID, newState);
			}
		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't update an adventure state record for player with ID " + playerID
							+ " and quest with ID " + questID, e);
		}
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		// nothing necessary
		
	}
}
