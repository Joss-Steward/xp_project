package datasource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.Position;
import data.QuestCompletionActionParameter;
import data.QuestCompletionActionType;

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
							+ " triggerRow INT, triggerColumn INT, experiencePointsGained INT, adventuresForFulfillment INT, "
							+ " completionActionType INT, completionActionParameter BLOB)");
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
	private int experiencePointsGained;
	private int adventuresForFulfillment;
	private QuestCompletionActionParameter completionActionParameter;
	private QuestCompletionActionType completionActionType;

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
			this.experiencePointsGained = result.getInt("experiencePointsGained");
			this.adventuresForFulfillment = result.getInt("adventuresForFulfillment");
			this.completionActionType = QuestCompletionActionType.findByID(result
					.getInt("completionActionType"));
			completionActionParameter = extractCompletionActionParameter(result,
					completionActionType);
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find a quest with ID " + questID, e);
		}
	}

	private QuestCompletionActionParameter extractCompletionActionParameter(
			ResultSet result, QuestCompletionActionType completionActionType)
			throws SQLException, DatabaseException
	{
		Class<? extends QuestCompletionActionParameter> completionActionParameterType = completionActionType
				.getCompletionActionParameterType();
		ByteArrayInputStream baip = new ByteArrayInputStream(
				(byte[]) result.getObject("completionActionParameter"));
		QuestCompletionActionParameter completionActionParameter = null;
		try
		{
			Object x = new ObjectInputStream(baip).readObject();
			completionActionParameter = completionActionParameterType.cast(x);
		} catch (ClassNotFoundException | IOException e)
		{
			throw new DatabaseException(
					"Couldn't convert blob to completion action parameter ", e);
		}
		return completionActionParameter;
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
	 * @param experiencePointsGained
	 *            the number of experience points gained for completing this
	 *            quest
	 * @param adventuresForFulfillment
	 *            the number of adventures this quest requires for fulfillment
	 * @param completionActionType
	 *            the type of action that should be taken when this quest is
	 *            completed
	 * @param completionActionParameter
	 *            data describing the details of the action taken when this
	 *            quest is completed
	 * @throws DatabaseException
	 *             if we can't talk to the RDS
	 */
	public QuestRowDataGatewayRDS(int questID, String questDescription,
			String triggerMapName, Position triggerPosition, int experiencePointsGained,
			int adventuresForFulfillment, QuestCompletionActionType completionActionType,
			QuestCompletionActionParameter completionActionParameter)
			throws DatabaseException
	{
		this.questID = questID;
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(
					connection,
					"Insert INTO Quests SET questID = ?, questDescription = ?, triggerMapname = ?, triggerRow = ?, triggerColumn = ?, "
							+ "experiencePointsGained = ?, adventuresForFulfillment = ?,"
							+ " completionActionType = ?, completionActionParameter = ?");
			stmt.setInt(1, questID);
			stmt.setString(2, questDescription);
			stmt.setString(3, triggerMapName);
			stmt.setInt(4, triggerPosition.getRow());
			stmt.setInt(5, triggerPosition.getColumn());
			stmt.setInt(6, experiencePointsGained);
			stmt.setInt(7, adventuresForFulfillment);
			stmt.setInt(8, completionActionType.getID());
			stmt.setObject(9, completionActionParameter);
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
	 * @see datasource.QuestRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
	}

	/**
	 * @see datasource.QuestRowDataGateway#getQuestID()
	 */
	@Override
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getQuestDescription()
	 */
	@Override
	public String getQuestDescription()
	{
		return questDescription;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getTriggerMapName()
	 */
	@Override
	public String getTriggerMapName()
	{
		return triggerMapName;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getTriggerPosition()
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

	/**
	 * @see datasource.QuestRowDataGateway#getAdventuresForFulfillment()
	 */
	@Override
	public int getAdventuresForFulfillment()
	{
		return adventuresForFulfillment;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getExperiencePointsGained()
	 */
	@Override
	public int getExperiencePointsGained()
	{
		return experiencePointsGained;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getCompletionActionType()
	 */
	@Override
	public QuestCompletionActionType getCompletionActionType()
	{
		return completionActionType;
	}

	/**
	 * @see datasource.QuestRowDataGateway#getCompletionActionParameter()
	 */
	@Override
	public QuestCompletionActionParameter getCompletionActionParameter()
	{
		return completionActionParameter;
	}

}
