import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.model.NPCQuestion;
import edu.ship.shipsim.areaserver.model.PlayerManager;
import edu.ship.shipsim.areaserver.model.QuestionsInDB;

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
		//TODO still have to remove ORM Lite from questions
//		connectionSource = PlayerManager.getSingleton().getConnectionSource();
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
