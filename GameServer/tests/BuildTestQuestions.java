import java.sql.SQLException;

import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.datasource.NPCQuestionRowDataGatewayRDS;
import edu.ship.shipsim.areaserver.datasource.NPCQuestionsForTest;

/**
 * Builds the Player portion of the database
 * 
 * @author Merlin
 * 
 */
public class BuildTestQuestions
{

	/**
	 * 
	 * @param args
	 *            unused
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws SQLException
	 *             shouldn't
	 */
	public static void main(String[] args) throws DatabaseException, SQLException
	{
		createQuestionTable();
	}

	/**
	 * Create a table of test questions
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	private static void createQuestionTable() throws SQLException, DatabaseException
	{
		NPCQuestionRowDataGatewayRDS.createTable();
		for (NPCQuestionsForTest question : NPCQuestionsForTest.values())
		{
			new NPCQuestionRowDataGatewayRDS(question.getQuestionID(),question.getQ(),question.getA());
		}
	}
}
