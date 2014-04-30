package model;

import java.sql.SQLException;
import java.util.Random;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.DatabaseTable;

/**
 * NPCQuestion class that utilizes ORMLite's DAO to store and retrieve NPCQuestions from the database.
 * 
 * Will return a random question from the database with getRandomQuestion()
 * 
 * @author Ga and Frank
 *
 */
@DatabaseTable(tableName = "NPCQuestion")
public class NPCQuestion
{

	public static JdbcConnectionSource getConnectionSource() throws SQLException
	{
		setUpDAOObject();
		return connectionSource;
	}

	@DatabaseField(generatedId = true)
	private int questionID;

	@DatabaseField
	private String question;

	@DatabaseField
	private String answer;

	private static Dao<NPCQuestion, Integer> questionDao;

	private static JdbcConnectionSource connectionSource;

	/**
	 * 
	 * @return
	 * 			the DAO for NPCQuestion
	 * @throws SQLException
	 */
	public static Dao<NPCQuestion, Integer> getQuestionDao() throws SQLException
	{
		setUpDAOObject();
		return questionDao;
	}

	/**
	 * Sets up DAO Object for this class.
	 * Must be called before using this class's DAO.
	 * @throws SQLException
	 */
	private static void setUpDAOObject() throws SQLException
	{
		if (connectionSource == null)
		{
			String databaseUrl = "jdbc:mysql://shipsim.cbzhjl6tpflt.us-east-1.rds.amazonaws.com:3306/Players";
			connectionSource = new JdbcConnectionSource(databaseUrl, "program",
					"ShipSim");
			questionDao = DaoManager.createDao(connectionSource,
					NPCQuestion.class);
		}
	}

	/**
	 * Empty Constructor
	 */
	public NPCQuestion()
	{
		
	}
	
	/**
	 * @param question
	 * 			question to be stored in an NPCQuestion Object
	 * @param answer
	 * 			the question's corresponding answer
	 * @throws SQLException
	 */
	public NPCQuestion(String question, String answer) throws SQLException
	{
		this.question = question;
		this.answer = answer;
		setUpDAOObject();
		questionDao.create(this);
	}

	/**
	 * 
	 * @return
	 * 			answer to question
	 */
	public String getAnswer()
	{
		return answer;
	}

	/**
	 * 
	 * @return
	 * 			question
	 */
	public String getQuestion()
	{
		return question;
	}

	/**
	 * 
	 * @param questionID
	 * 			ID of a question in the DB
	 * @return
	 * 			the corresponding question to the questionID
	 * @throws SQLException
	 */
	public static NPCQuestion getQuestionFromID(int questionID) throws SQLException
	{
		setUpDAOObject();
		return questionDao.queryForId(questionID);
	}

	/**
	 * 
	 * @return a random question from the DB
	 * @throws SQLException
	 * 				shouldn't
	 */
	public NPCQuestion getRandomQuestion() throws SQLException
	{
		Random rand = new Random();
		setUpDAOObject();
		//get a count of all questions
		long longCount = questionDao.countOf();
		int questionCount = (int) longCount;
		
		//generate random number
		int randomNumber = rand.nextInt(questionCount);
		randomNumber++;
		
		//return question from id
		return NPCQuestion.getQuestionFromID(randomNumber);
	}
}
