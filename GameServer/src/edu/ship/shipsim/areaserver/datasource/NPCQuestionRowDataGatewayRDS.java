package edu.ship.shipsim.areaserver.datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DatabaseManager;
import datasource.ClosingPreparedStatement;
import datasource.DatabaseException;

/**
 * The RDS implementation of the gateway
 * 
 * @author Merlin
 *
 */
public class NPCQuestionRowDataGatewayRDS implements NPCQuestionRowDataGateway
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
					"DROP TABLE IF EXISTS NPCQuestions");
			stmt.executeUpdate();
			stmt.close();

			stmt = new ClosingPreparedStatement(connection,
					"Create TABLE NPCQuestions (questionID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, questionStatement VARCHAR(80), answer VARCHAR(80))");
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the NPC Question table", e);
		}
	}

	/**
	 * @return a random question from the data source
	 * @throws DatabaseException
	 *             if we can't talk to the database
	 */
	public static NPCQuestionRowDataGateway findRandomGateway() throws DatabaseException
	{

		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"SELECT * FROM NPCQuestions ORDER BY RAND() LIMIT 1");
			ResultSet result = stmt.executeQuery();
			result.next();
			NPCQuestionRowDataGatewayRDS gateway = new NPCQuestionRowDataGatewayRDS();
			gateway.questionStatement = result.getString("questionStatement");
			gateway.answer = result.getString("answer");
			return gateway;
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find a a rancom NPC Question", e);
		}
	}
	private String questionStatement;

	private String answer;

	private Connection connection;

	/**
	 * Finder constructor
	 * 
	 * @param questionID
	 *            the questions Unique ID
	 * @throws DatabaseException
	 *             if we fail when talking to the database
	 */
	public NPCQuestionRowDataGatewayRDS(int questionID) throws DatabaseException
	{
		this.connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"SELECT * FROM NPCQuestions WHERE questionID = ?");
			stmt.setInt(1, questionID);
			ResultSet result = stmt.executeQuery();
			result.next();
			this.questionStatement = result.getString("questionStatement");
			this.answer = result.getString("answer");
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find an NPC Question with ID "
					+ questionID, e);
		}
	}

	/**
	 * Create constructor
	 * 
	 * @param questionID
	 *            the question's unique ID
	 * @param questionStatement
	 *            the wording of the question
	 * @param answer
	 *            the answer to the question
	 * @throws DatabaseException
	 *             if we fail when talking to the database (question ID already
	 *             exists would be common)
	 */
	public NPCQuestionRowDataGatewayRDS(int questionID, String questionStatement,
			String answer) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"Insert INTO NPCQuestions SET questionID = ?, questionStatement = ?, answer = ?");
			stmt.setInt(1, questionID);
			stmt.setString(2, questionStatement);
			stmt.setString(3, answer);
			stmt.executeUpdate();

			this.questionStatement = questionStatement;
			this.answer = answer;

		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't create a NCP question record for question with ID "
							+ questionID, e);
		}
	}

	private NPCQuestionRowDataGatewayRDS()
	{
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.NPCQuestionRowDataGateway#getAnswer()
	 */
	@Override
	public String getAnswer()
	{
		return answer;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.NPCQuestionRowDataGateway#getQuestionStatement()
	 */
	@Override
	public String getQuestionStatement()
	{
		return questionStatement;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.NPCQuestionRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
	}

}
