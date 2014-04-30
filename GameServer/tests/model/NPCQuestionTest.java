package model;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class NPCQuestionTest extends DatabaseTest
{

	@Test
	public void test() throws SQLException {
		NPCQuestion question = NPCQuestion.getQuestionFromID(1);
		assertEquals(QuestionsInDB.ONE.getQ(),question.getQuestion());
		assertEquals(QuestionsInDB.ONE.getA(),question.getAnswer());
	}
}
