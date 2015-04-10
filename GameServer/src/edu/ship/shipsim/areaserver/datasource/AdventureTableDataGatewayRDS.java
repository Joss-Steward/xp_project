package edu.ship.shipsim.areaserver.datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DatabaseManager;
import datasource.ClosingPreparedStatement;
import datasource.DatabaseException;

/**
 * The RDS Implementation of this gateway
 * 
 * @author merlin
 *
 */
public class AdventureTableDataGatewayRDS implements AdventureTableDataGateway
{

	private static AdventureTableDataGateway singleton;

	/**
	 * Retrieves the rds gateway singleton.
	 * 
	 * @return singleton
	 */
	public static synchronized AdventureTableDataGateway getSingleton()
	{
		if (singleton == null)
		{
			singleton = new AdventureTableDataGatewayRDS();
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
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"DROP TABLE IF EXISTS Adventures");
			stmt.executeUpdate();
			stmt.close();

			stmt = new ClosingPreparedStatement(connection,
					"Create TABLE Adventures (adventureID INT NOT NULL, adventureDescription VARCHAR(80), "
							+ "questID INT NOT NULL, experiencePointsGained INT)");
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the Adventure table", e);
		}
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.AdventureTableDataGateway#getAdventuresForQuest(int)
	 */
	@Override
	public ArrayList<AdventureRecord> getAdventuresForQuest(int questID)
			throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"SELECT * FROM Adventures WHERE questID = ?");
			stmt.setInt(1, questID);
			ResultSet queryResult = stmt.executeQuery();

			ArrayList<AdventureRecord> results = new ArrayList<AdventureRecord>();
			while (queryResult.next())
			{
				AdventureRecord rec = new AdventureRecord(queryResult.getInt("questID"),
						queryResult.getInt("adventureID"),
						queryResult.getString("adventureDescription"),
						queryResult.getInt("experiencePointsGained"));
				results.add(rec);
			}
			return results;
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find adventures for quest ID "
					+ questID, e);
		}
	}

	/**
	 * Create a new row in the table
	 * 
	 * @param adventureID
	 *            the unique ID of the adventure
	 * @param adventureDescription
	 *            the description of the adventure
	 * @param questID
	 *            the quest that contains the adventure
	 * @param experiencePointsEarned
	 *            the number of points you get when you complete this adventure
	 * @throws DatabaseException
	 *             if we can't talk to the RDS
	 */
	public static void createRow(int adventureID, String adventureDescription,
			int questID, int experiencePointsEarned) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"Insert INTO Adventures SET adventureID = ?, adventureDescription = ?, questID = ?,"
							+ "experiencePointsGained = ?");
			stmt.setInt(1, adventureID);
			stmt.setString(2, adventureDescription);
			stmt.setInt(3, questID);
			stmt.setInt(4, experiencePointsEarned);
			stmt.executeUpdate();

		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't create a adventure record for adventure with ID "
							+ adventureID, e);
		}
	}

}
