import java.sql.SQLException;

import testData.NPCQuestionsForTest;
import model.OptionsManager;
import datasource.DatabaseException;
import datasource.NPCQuestionRowDataGatewayRDS;

/**
 * Builds the Question portion of the database for the quizbot
 * 
 * @author Merlin
 * 
 */
public class BuildTestQuestions
{

	/**
	 * 
	 * @param args unused
	 * @throws DatabaseException shouldn't
	 * @throws SQLException shouldn't
	 */
	public static void main(String[] args) throws DatabaseException, SQLException
	{
		OptionsManager.getSingleton().setTestMode(false);
		OptionsManager.getSingleton().setUsingTestDB(true);
		createQuestionTable();
	}

	/**
	 * Create a table of test questions
	 * 
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	private static void createQuestionTable() throws SQLException, DatabaseException
	{
		NPCQuestionRowDataGatewayRDS.createTable();
		for (NPCQuestionsForTest question : NPCQuestionsForTest.values())
		{
			new NPCQuestionRowDataGatewayRDS(question.getQuestionID(), question.getQ(), question.getA(),
					question.getStartDate(), question.getEndDate());
		}
	}
}
