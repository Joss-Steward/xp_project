import java.sql.SQLException;

import model.DatabaseException;
import model.NPCQuestion;
import model.PlayerManager;
import model.QuestionsInDB;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Builds the Player portion of the database
 * 
 * @author Merlin
 * 
 */
public class BuildTestQuestions
{

	private static JdbcConnectionSource connectionSource;

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
		connectionSource = PlayerManager.getSingleton().getConnectionSource();
		Dao<NPCQuestion, Integer> questionDao = NPCQuestion.getDao();
		TableUtils.dropTable(connectionSource, NPCQuestion.class, true);
		TableUtils.createTableIfNotExists(connectionSource, NPCQuestion.class);

		for (QuestionsInDB question : QuestionsInDB.values())
		{
			NPCQuestion q = new NPCQuestion(question.getQ(),question.getA());
			questionDao.create(q);
		}
	}
}
