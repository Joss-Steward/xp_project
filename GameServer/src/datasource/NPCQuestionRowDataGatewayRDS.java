package datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import datasource.ClosingPreparedStatement;
import datasource.DatabaseException;
import datasource.DatabaseManager;

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
					"Create TABLE NPCQuestions (questionID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, questionStatement VARCHAR(80), answer VARCHAR(80), startDate DATE, endDate DATE)");
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
					"SELECT * FROM NPCQuestions WHERE startDate < CURDATE() AND endDate > CURDATE() ORDER BY RAND() LIMIT 1");
			ResultSet result = stmt.executeQuery();
			result.next();
			NPCQuestionRowDataGatewayRDS gateway = new NPCQuestionRowDataGatewayRDS();
			gateway.questionStatement = result.getString("questionStatement");
			gateway.answer = result.getString("answer");
			gateway.startDate = result.getDate("startDate");
			gateway.endDate = result.getDate("endDate");
			return gateway;
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find a a rancom NPC Question", e);
		}
	}
	private String questionStatement;

	private String answer;
	
	private Date startDate;
	
	private Date endDate;

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
	 * @param startDate
	 *            the first day the question is available
	 * @param endDate
	 *             the last day the question is available
	 * @throws DatabaseException
	 *             if we fail when talking to the database (question ID already
	 *             exists would be common)
	 */
	public NPCQuestionRowDataGatewayRDS(int questionID, String questionStatement,
			String answer, Date startDate, Date endDate) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"Insert INTO NPCQuestions SET questionID = ?, questionStatement = ?, answer = ?, startDate = ?, endDate = ?");
			stmt.setInt(1, questionID);
			stmt.setString(2, questionStatement);
			stmt.setString(3, answer);
			stmt.setDate(4, new java.sql.Date(startDate.getTime()));
			stmt.setDate(5, new java.sql.Date(endDate.getTime()));
			stmt.executeUpdate();
			this.questionStatement = questionStatement;
			this.answer = answer;
			this.startDate = startDate;
			this.endDate = endDate;


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
	 * @see datasource.NPCQuestionRowDataGateway#getAnswer()
	 */
	@Override
	public String getAnswer()
	{
		return answer;
	}

	/**
	 * @see datasource.NPCQuestionRowDataGateway#getQuestionStatement()
	 */
	@Override
	public String getQuestionStatement()
	{
		return questionStatement;
	}

	/**
	 * @see datasource.NPCQuestionRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
	}

    /**
     * @see datasource.NPCQuestionRowDataGateway#getStartDate()
     */
    @Override
    public Date getStartDate()
    {
        return startDate;
    }
    
    /** 
     * @see datasource.NPCQuestionRowDataGateway#getEndDate()
     */
    public Date getEndDate()
    {
        return endDate;
    }

}
