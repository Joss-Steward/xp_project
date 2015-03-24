package edu.ship.shipsim.areaserver.datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DatabaseManager;
import data.Position;
import datasource.ClosingPreparedStatement;
import datasource.DatabaseException;

/**
 * An RDS implementation of the QuestRowDataGateway interface
 * 
 * @author merlin
 *
 */
public class QuestRowDataGatewayRDS implements QuestRowDataGateway
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
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"DROP TABLE IF EXISTS Quests");
			stmt.executeUpdate();
			stmt.close();

			stmt = new ClosingPreparedStatement(
					connection,
					"Create TABLE Quests (questID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, questDescription VARCHAR(80), triggerMapName VARCHAR(80),"
							+ " triggerRow INT, triggerColumn INT)");
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the Quest table", e);
		}
	}

	private int questID;
	private String questDescription;
	private String triggerMapName;
	private Position triggerPosition;
	private Connection connection;

	/**
	 * Finder constructor
	 * 
	 * @param questID
	 *            the id of the quest this gateway will manage
	 * @throws DatabaseException
	 *             if we can't talk to the RDS server
	 */
	public QuestRowDataGatewayRDS(int questID) throws DatabaseException
	{
		this.questID = questID;
		this.connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"SELECT * FROM Quests WHERE questID = ?");
			stmt.setInt(1, questID);
			ResultSet result = stmt.executeQuery();
			result.next();
			this.questDescription = result.getString("questDescription");
			this.triggerMapName = result.getString("triggerMapName");
			this.triggerPosition = new Position(result.getInt("triggerRow"),
					result.getInt("triggerColumn"));
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find a quest with ID " + questID, e);
		}
	}

	/**
	 * Create constructor
	 * 
	 * @param questID
	 *            the quest's unique ID
	 * @param questDescription
	 *            the description of the quest
	 * @param triggerMapName
	 *            the name of the map that contains the trigger location for
	 *            this quest
	 * @param triggerPosition
	 *            the coordinates of the trigger location for this quest
	 * @throws DatabaseException
	 *             if we can't talk to the RDS
	 */
	public QuestRowDataGatewayRDS(int questID, String questDescription,
			String triggerMapName, Position triggerPosition) throws DatabaseException
	{
		this.questID = questID;
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(
					connection,
					"Insert INTO Quests SET questID = ?, questDescription = ?, triggerMapname = ?, triggerRow = ?, triggerColumn = ?");
			stmt.setInt(1, questID);
			stmt.setString(2, questDescription);
			stmt.setString(3, triggerMapName);
			stmt.setInt(4, triggerPosition.getRow());
			stmt.setInt(5, triggerPosition.getColumn());
			stmt.executeUpdate();

			this.questDescription = questDescription;

		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't create a quest record for quest with ID " + questID, e);
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
	 * @see edu.ship.shipsim.areaserver.datasource.QuestRowDataGateway#getQuestDescription()
	 */
	@Override
	public String getQuestDescription()
	{
		return questDescription;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestRowDataGateway#getTriggerMapName()
	 */
	@Override
	public String getTriggerMapName()
	{
		return triggerMapName;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestRowDataGateway#getTriggerPosition()
	 */
	@Override
	public Position getTriggerPosition()
	{
		return triggerPosition;
	}

	/**
	 * Get the IDs of the quests that are supposed to trigger at a specified map
	 * location
	 * 
	 * @param mapName
	 *            the name of the map
	 * @param position
	 *            the position on the map
	 * @return the quest IDs
	 * @throws DatabaseException
	 *             if we can't talk to the RDS data source
	 */
	public static ArrayList<Integer> findQuestsForMapLocation(String mapName,
			Position position) throws DatabaseException
	{
		try
		{
			Connection connection = DatabaseManager.getSingleton().getConnection();

			ClosingPreparedStatement stmt = new ClosingPreparedStatement(
					connection,
					"SELECT questID FROM Quests WHERE triggerMapName = ? and triggerRow = ? and triggerColumn = ?");
			stmt.setString(1, mapName);
			stmt.setInt(2, position.getRow());
			stmt.setInt(3, position.getColumn());
			ResultSet result = stmt.executeQuery();

			ArrayList<Integer> results = new ArrayList<Integer>();
			while (result.next())
			{
				results.add(result.getInt("questID"));
			}
			return results;
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find a quests for map named " + mapName
					+ " and position " + position, e);
		}
	}

}
