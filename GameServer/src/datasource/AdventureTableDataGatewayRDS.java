package datasource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.AdventureCompletionCriteria;
import data.AdventureCompletionType;
import data.AdventureRecord;
import data.GameLocation;
import datasource.ClosingPreparedStatement;
import datasource.DatabaseException;
import datasource.DatabaseManager;
import datatypes.Position;

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
	 * @throws DatabaseException shouldn't
	 */
	public static void createTable() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection, "DROP TABLE IF EXISTS Adventures");
			stmt.executeUpdate();
			stmt.close();

			stmt = new ClosingPreparedStatement(connection,
					"Create TABLE Adventures (adventureID INT NOT NULL, adventureDescription VARCHAR(200), "
							+ "questID INT NOT NULL, experiencePointsGained INT, completionType INT, completionCriteria BLOB, PRIMARY KEY(questID, adventureID))");
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the Adventure table", e);
		}
	}

	/**
	 * @see datasource.AdventureTableDataGateway#getAdventuresForQuest(int)
	 */
	@Override
	public ArrayList<AdventureRecord> getAdventuresForQuest(int questID) throws DatabaseException
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
				AdventureRecord rec = buildAdventureRecord(queryResult);
				results.add(rec);
			}
			return results;
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find adventures for quest ID " + questID, e);
		}
	}

	/**
	 * Create a new row in the table
	 * 
	 * @param adventureID the unique ID of the adventure
	 * @param adventureDescription the description of the adventure
	 * @param questID the quest that contains the adventure
	 * @param experiencePointsEarned the number of points you get when you
	 *            complete this adventure
	 * @param completionType the type of action the player must do to complete
	 *            this adventure
	 * @param completionCriteria the criteria for completing this adventure
	 * @throws DatabaseException if we can't talk to the RDS
	 */
	public static void createRow(int adventureID, String adventureDescription, int questID, int experiencePointsEarned,
			AdventureCompletionType completionType, AdventureCompletionCriteria completionCriteria)
			throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"Insert INTO Adventures SET adventureID = ?, adventureDescription = ?, questID = ?,"
							+ "experiencePointsGained = ?, completionType = ?, completionCriteria = ?");
			stmt.setInt(1, adventureID);
			stmt.setString(2, adventureDescription);
			stmt.setInt(3, questID);
			stmt.setInt(4, experiencePointsEarned);
			stmt.setInt(5, completionType.getID());
			stmt.setObject(6, completionCriteria);
			stmt.executeUpdate();

		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't create a adventure record for adventure with ID " + adventureID, e);
		}
	}

	/**
	 * @see datasource.AdventureTableDataGateway#getAdventure(int, int)
	 */
	@Override
	public AdventureRecord getAdventure(int questID, int adventureID) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"SELECT * FROM Adventures WHERE questID = ? and AdventureID = ?");
			stmt.setInt(1, questID);
			stmt.setInt(2, adventureID);
			ResultSet queryResult = stmt.executeQuery();

			if (queryResult.next())
			{
				AdventureRecord rec = buildAdventureRecord(queryResult);
				return rec;
			}
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find adventure " + adventureID + " for quest ID " + questID, e);
		}
		return null;
	}

	private AdventureRecord buildAdventureRecord(ResultSet queryResult) throws DatabaseException
	{
		try
		{
			AdventureCompletionType completionType = AdventureCompletionType
					.findByID(queryResult.getInt("completionType"));
			AdventureCompletionCriteria completionCriteria = extractCompletionCriteria(queryResult, completionType);

			AdventureRecord rec = new AdventureRecord(queryResult.getInt("questID"), queryResult.getInt("adventureID"),
					queryResult.getString("adventureDescription"), queryResult.getInt("experiencePointsGained"),
					completionType, completionCriteria);
			return rec;
		} catch (SQLException e)
		{
			throw new DatabaseException("Exception trying to parse the results of reading an adventure", e);
		}
	}

	private AdventureCompletionCriteria extractCompletionCriteria(ResultSet queryResult,
			AdventureCompletionType completionType) throws SQLException, DatabaseException
	{
		Class<? extends AdventureCompletionCriteria> completionCriteriaClass = completionType
				.getCompletionCriteriaType();
		ByteArrayInputStream baip = new ByteArrayInputStream((byte[]) queryResult.getObject("completionCriteria"));
		AdventureCompletionCriteria completionCriteria = null;
		try
		{
			Object x = new ObjectInputStream(baip).readObject();
			completionCriteria = completionCriteriaClass.cast(x);
		} catch (ClassNotFoundException | IOException e)
		{
			throw new DatabaseException("Couldn't convert blob to completion criteria ", e);
		}
		return completionCriteria;
	}

	/**
	 * @see datasource.AdventureTableDataGateway#findAdventuresCompletedForMapLocation(String,
	 *      Position)
	 */
	@Override
	public ArrayList<AdventureRecord> findAdventuresCompletedForMapLocation(String mapName, Position pos)
			throws DatabaseException
	{
		ArrayList<AdventureRecord> results = new ArrayList<AdventureRecord>();

		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"SELECT * FROM Adventures WHERE completionType = ?");
			stmt.setInt(1, AdventureCompletionType.MOVEMENT.getID());
			ResultSet queryResult = stmt.executeQuery();

			while (queryResult.next())
			{
				AdventureRecord rec = buildAdventureRecord(queryResult);
				GameLocation thisLocation = (GameLocation) rec.getCompletionCriteria();
				if (thisLocation.getPosition().equals(pos) && thisLocation.getMapName().equals(mapName))
				{
					results.add(rec);
				}
			}
			return results;
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find adventures for location at " + mapName + " " + pos.toString(),
					e);
		}
	}

}
