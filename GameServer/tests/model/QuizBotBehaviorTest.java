package model;

import static org.junit.Assert.*;

import java.sql.SQLException;

import model.reports.SendChatMessageReport;

import org.junit.Before;
import org.junit.Test;

import data.ChatType;
import data.Position;

/**
 * @author Frank & Dave & Nick
 *
 * Make sure that the QuizBotBehavior acts as expected
 */
public class QuizBotBehaviorTest {
	
	QuizBotBehavior behavior;
	NPCQuestion question;
	

	/**
	 * @throws SQLException
	 * Set up the behavior and a question for each test
	 */
	@Before
	public void setUp() throws SQLException
	{
		behavior = new QuizBotBehavior();
		question = behavior.getQuestion();
	}
	
	
	@Test
	public void testAskingQuestion()
	{
		
	}
	
	/**
	 * When a question is correctly answered, the player who got it right should have their score incremented.
	 */
	@Test
	public void testCorrectAnswer()
	{
		Player p = PlayerManager.getSingleton().addPlayer(1);
		
		String answer = question.getAnswer();
		String playerName = p.getPlayerName();
		
		SendChatMessageReport message = new SendChatMessageReport(answer, playerName, new Position(0,0), ChatType.Zone);
		
		int score = p.getQuizScore();
		
		behavior.update(p, message);
		
		assertEquals(score+1, p.getQuizScore());
		
		p.setQuizScore(score);
	}
	
	/**
	 * If a player answers incorrectly, their score should not change.
	 */
	@Test
	public void testIncorrectAnswer()
	{
		Player p = PlayerManager.getSingleton().addPlayer(1);
		SendChatMessageReport message = new SendChatMessageReport(question.getAnswer() + " ", p.getPlayerName(), new Position(0,0), ChatType.Zone);
		
		int score = p.getQuizScore();
		
		behavior.update(p, message);
		
		assertEquals(score, p.getQuizScore());
	}
}
