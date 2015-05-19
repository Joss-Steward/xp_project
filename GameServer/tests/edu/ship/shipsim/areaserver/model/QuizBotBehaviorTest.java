package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;
import model.OptionsManager;
import model.QualifiedObservableConnector;

import org.junit.Before;
import org.junit.Test;

import data.ChatType;
import data.Position;
import datasource.DatabaseException;
import datasource.DatabaseTest;
import edu.ship.shipsim.areaserver.model.NPCQuestion;
import edu.ship.shipsim.areaserver.model.Player;
import edu.ship.shipsim.areaserver.model.PlayerManager;
import edu.ship.shipsim.areaserver.model.QuizBotBehavior;
import edu.ship.shipsim.areaserver.model.reports.SendChatMessageReport;

/**
 * @author Frank & Dave & Nick
 *
 * Make sure that the QuizBotBehavior acts as expected
 */
public class QuizBotBehaviorTest  extends DatabaseTest
{
	
	private QuizBotBehavior behavior;
	private NPCQuestion question;
	

	/**
	 * @throws DatabaseException  shouldn't
	 * Set up the behavior and a question for each test
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		super.setUp();
		OptionsManager.getSingleton().setTestMode(true);
		behavior = new QuizBotBehavior();
		question = behavior.getQuestion();
		QualifiedObservableConnector.resetSingleton();
		ChatManager.resetSingleton();
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
		
		// check that spaces don't matter
		SendChatMessageReport report = new SendChatMessageReport("    " + answer + " ", playerName, new Position(0,0), ChatType.Zone);
		int score = p.getQuizScore();
		
		behavior.receiveReport(report);
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
		SendChatMessageReport report = new SendChatMessageReport("incorrect", p.getPlayerName(), new Position(0,0), ChatType.Zone);
		
		int score = p.getQuizScore();
		
		behavior.receiveReport(report);
		
		assertEquals(score, p.getQuizScore());
	}
}
