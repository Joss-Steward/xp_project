package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

import model.reports.SendChatMessageReport;
import data.ChatType;
import data.Position;

/**
 * The Bot Behavior of the Quiznasium NPC.
 * 
 * @author Frank and Dave
 */
public class QuizBotBehavior extends Behavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1511084096181049717L;
	
	/*
	 * 30 Seconds.
	 */
	protected int pollingInterval = 30000;
	
	private NPCQuestion question;

	public QuizBotBehavior()
	{
		pullNewQuestion();
		setUpListening();
	}
	
	/**
	 * @param o
	 * @param message
	 *			the chat message that is broadcast to the clients
	 * 
	 * Watches SendChatMessageReports for a correct answer.
	 * On correct answer, announce the correct answer, pull a new random question, and ask that question.
	 */
	@Override
	public void update(Observable o, Object message) 
	{
		
		if(message instanceof SendChatMessageReport)
		{
			String answer = question.getAnswer();
			SendChatMessageReport report = (SendChatMessageReport)message;
			
			if (answer.equals(report.getMessage()))
			{
				Player player = null;
				
				
				try 
				{
					int playerID = PlayerManager.getSingleton().getPlayerIDFromPlayerName(report.getSenderName());
					player = PlayerManager.getSingleton().getPlayerFromID(playerID);
				} 
				catch (PlayerNotFoundException e) 
				{
					e.printStackTrace();
				}
				
				correctAnswer(player);
				//pull new question
				pullNewQuestion();
				//ask new question
				askQuestion();
			}
		}
	}
	
	/**
	 * @param player
	 * 			the player that correctly answered the question
	 * 
	 * Announce a correct answer and increment the player's quiz score.
	 */
	private void correctAnswer(Player player)
	{
		ChatManager.getSingleton().sendChatToClients(player.getPlayerName() +" answered correctly.  The answer was " +question.getAnswer(),
				"Quiz Bot", new Position(0,0), ChatType.Zone);
		player.incrementQuizScore();
	}
	
	/**
	 * Asks the question at 30 second intervals.
	 */
	@Override
	public void doTimedEvent() 
	{
		askQuestion();
	}

	/**
	 * Asks the question.
	 */
	private void askQuestion() 
	{
		String questionString = question.getQuestion();
		
		ChatManager.getSingleton().sendChatToClients(questionString, "Quiz Bot",
				new Position(0,0), ChatType.Zone);	
	}

	/**
	 * Gets a new random question from the database.
	 */
	private void pullNewQuestion() 
	{
		try 
		{
			question = NPCQuestion.getRandomQuestion();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return the question currently being asked by the quiz bot
	 */
	protected NPCQuestion getQuestion()
	{
		return this.question;
	}
	
	/**
	 * Get report types that this class watches for.
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes() {
		final ArrayList<Class<? extends QualifiedObservableReport>> listOfObservables = new ArrayList<>();
		listOfObservables.add(SendChatMessageReport.class);
		return listOfObservables;
	}


}
