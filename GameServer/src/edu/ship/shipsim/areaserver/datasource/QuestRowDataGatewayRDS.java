package edu.ship.shipsim.areaserver.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DatabaseManager;
import datasource.DatabaseException;

/**
 * An RDS implementation of the QuestRowDataGateway interface
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
			PreparedStatement stmt = connection
					.prepareStatement("DROP TABLE IF EXISTS Quests");
			stmt.executeUpdate();

			stmt = connection
					.prepareStatement("Create TABLE Quests (questID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, questDescription VARCHAR(80))");
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the Quest table", e);
		}
	}
	private int questID;
	private String questDescription;
	private Connection connection;
	
	/**
	 * Finder constructor
	 * 
	 * @param questID the id of the quest this gateway will manage
	 * @throws DatabaseException if we can't talk to the RDS server
	 */
	public QuestRowDataGatewayRDS(int questID) throws DatabaseException
	{
		this.questID = questID;
		this.connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Quests WHERE questID = ?");
			stmt.setInt(1, questID);
			ResultSet result = stmt.executeQuery();
			result.next();
			this.questDescription = result.getString("questDescription");
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find a quest with ID "
					+ questID, e);
		}
	}

	/**
	 * Create constructor
	 * @param questID the quest's unique ID
	 * @param questDescription the description of the quest
	 * @throws DatabaseException if we can't talk to the RDS
	 */
	public QuestRowDataGatewayRDS(int questID, String questDescription) throws DatabaseException
	{
		this.questID = questID;
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("Insert INTO Quests SET questID = ?, questDescription = ?");
			stmt.setInt(1, questID);
			stmt.setString(2, questDescription);
			stmt.executeUpdate();

			this.questDescription = questDescription;

		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't create a quest record for quest with ID "
							+ questID, e);
		}
	}

	/**
	 * Nothing to do in this version
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

}
