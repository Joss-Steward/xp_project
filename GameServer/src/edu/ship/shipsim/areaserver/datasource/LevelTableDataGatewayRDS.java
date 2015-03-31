package edu.ship.shipsim.areaserver.datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DatabaseManager;
import datasource.ClosingPreparedStatement;
import datasource.DatabaseException;

/**
 * The RDS implementation
 * @author Merlin
 *
 */
public class LevelTableDataGatewayRDS implements LevelTableDataGateway
{
	
	private static LevelTableDataGatewayRDS singleton;

	/**
	 * @return the one and only
	 */
	public static synchronized LevelTableDataGatewayRDS getSingleton()
	{
		if (singleton == null)
		{
			singleton = new LevelTableDataGatewayRDS();
		}
		return singleton;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.LevelTableDataGateway#getAllLevels()
	 */
	@Override
	public ArrayList<LevelRecord> getAllLevels() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"SELECT * FROM Levels");
			ResultSet result = stmt.executeQuery();

			ArrayList<LevelRecord> results = new ArrayList<LevelRecord>();
			while (result.next())
			{
				LevelRecord rec = new LevelRecord(
						result.getString("levelDescription"), result.getInt("levelUpPoints"));
				results.add(rec);
			}
			return results;
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find levels", e);
		}
	}

	/**
	 * Dump any existing table and create a new one
	 * @throws DatabaseException if we can't create it
	 */
	public static void createTable() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"DROP TABLE IF EXISTS Levels");
			stmt.executeUpdate();
			stmt.close();
			stmt = new ClosingPreparedStatement(
					connection,
					"Create TABLE Levels (levelDescription VARCHAR(30) NOT NULL, levelUpPoints INT NOT NULL)");
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the Level table", e);
		}
	}

	/**
	 * Add a new row to the table
	 * @param description the description of the level
	 * @param levelUpPoints the number of points you need to get to the next level
	 * @throws DatabaseException if we can't talk to the db
	 */
	public void createRow(String description, int levelUpPoints) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"Insert INTO Levels SET levelDescription = ?, levelUpPoints = ?");
			stmt.setString(1, description);
			stmt.setInt(2, levelUpPoints);
			stmt.executeUpdate();

		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't create a level record for level with description "
							+ description, e);
		}
	}

}
