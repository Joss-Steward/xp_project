package edu.ship.shipsim.areaserver.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DatabaseManager;
import datasource.DatabaseException;

/**
 * The RDS implementation
 * @author Merlin
 *
 */
public class MapAreaRowDataGatewayRDS extends MapAreaRowDataGateway
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
					.prepareStatement("DROP TABLE IF EXISTS MapAreas");
			stmt.executeUpdate();

			stmt = connection
					.prepareStatement("Create TABLE MapAreas (areaName VARCHAR(80) NOT NULL PRIMARY KEY, questid INT)");
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the  Map Areatable", e);
		}
	}

	private String areaName;
	private int questID;
	private Connection connection;

	/**
	 * Finder constructor
	 * 
	 * @param areaName
	 *            the name of the area we are managing
	 * @throws DatabaseException
	 *             if we can't talk to the data source or we can't find an area
	 *             with the given name
	 */
	public MapAreaRowDataGatewayRDS(String areaName) throws DatabaseException
	{
		this.connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM MapAreas WHERE areaName = ?");
			stmt.setString(1, areaName);
			ResultSet result = stmt.executeQuery();
			result.next();
			this.areaName = areaName;
			this.questID = result.getInt("questid");
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find a map area named " + areaName, e);
		}
	}

	/**
	 * Create Constructor
	 * 
	 * @param areaName
	 *            the name of the area
	 * @param questID
	 *            the ID of the quest associated with the area
	 * @throws DatabaseException
	 *             if we can't insert the new row
	 */
	public MapAreaRowDataGatewayRDS(String areaName, int questID)
			throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("Insert INTO MapAreas SET areaName = ?, questID = ?");
			stmt.setString(1, areaName);
			stmt.setInt(2, questID);
			stmt.executeUpdate();

			this.areaName = areaName;
			this.questID = questID;

		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't create a Map area row for the area named " + areaName, e);
		}
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.MapAreaRowDataGateway#getMapAreaName()
	 */
	@Override
	public String getMapAreaName()
	{
		return areaName;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.MapAreaRowDataGateway#getQuestID()
	 */
	@Override
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.MapAreaRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{

	}

}
