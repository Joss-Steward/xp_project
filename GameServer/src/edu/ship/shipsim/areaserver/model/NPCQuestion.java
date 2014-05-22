package edu.ship.shipsim.areaserver.model;

import java.io.Serializable;
import java.sql.SQLException;

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
public class NPCQuestion implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2652068822205218746L;

	@DatabaseField(generatedId = true)
	private int questionID;

	@DatabaseField
	private String question;

	@DatabaseField
	private String answer;

	private static Dao<NPCQuestion, Integer> questionDao;

	private static JdbcConnectionSource connectionSource;

	/**
	 * Sets up DAO Object for this class.
	 * Must be called before using this class's DAO.
	 * @return The Dao for NPCQuestion
	 * @throws SQLException when the database messes up
	 */
	public static Dao<NPCQuestion, Integer> getDao() throws SQLException
	{
		String databaseUrl = "jdbc:mysql://shipsim.cbzhjl6tpflt.us-east-1.rds.amazonaws.com:3306/Players";
		connectionSource = new JdbcConnectionSource(databaseUrl, "program", "ShipSim");
		questionDao = DaoManager.createDao(connectionSource, NPCQuestion.class);
		return questionDao;
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
	 * @throws SQLException when database goes wrong
	 */
	public NPCQuestion(String question, String answer) throws SQLException
	{
		this.question = question;
		this.answer = answer;
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
	 * @param offset
	 * 			ID of a question in the DB
	 * @return
	 * 			the corresponding question to the questionID
	 * @throws SQLException when database goes wrong
	 */
	private static NPCQuestion getQuestionFromID(long offset) throws SQLException
	{
		return getDao().queryBuilder().offset(offset).limit(1L).queryForFirst();
	}

	/**
	 * 
	 * @return a random question from the DB
	 * @throws SQLException
	 * 				shouldn't
	 */
	public static NPCQuestion getRandomQuestion() throws SQLException
	{
		//get a count of all questions
		int questionCount = (int) getDao().countOf();
		
		int randomNumber = (int)(Math.random()*questionCount);
		
		//return question offset by our random amount
		return getQuestionFromID(randomNumber);
	}
}
