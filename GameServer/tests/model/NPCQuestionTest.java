package model;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

/**
 * Tests retrieving questions from the database
 * 
 * @author gl9859
 *
 */
public class NPCQuestionTest extends DatabaseTest
{

	/**
	 * The actual test.
	 */
	@Test
	public void test() throws SQLException {
		NPCQuestion question = NPCQuestion.getQuestionFromID(1);
		assertEquals(QuestionsInDB.ONE.getQ(),question.getQuestion());
		assertEquals(QuestionsInDB.ONE.getA(),question.getAnswer());
	}
}
