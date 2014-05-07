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
public class QuizBotBehavior extends Behavior 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1511084096181049717L;
	
	private NPCQuestion question;

	/**
	 * Initialize the QuizBotBehavior
	 */
	public QuizBotBehavior()
	{
		pollingInterval = 30000;
		pullNewQuestion();
		setUpListening();
	}
	
	/**
	 * Watches SendChatMessageReports for a correct answer.
	 * On correct answer, announce the correct answer, pull a new random question, and ask that question.
	 */
	@Override
	public void update(Observable o, Object message) 
	{
		
		if(message instanceof SendChatMessageReport)
		{
			String answer = question.getAnswer().toLowerCase().replaceAll(" ", "");
			SendChatMessageReport report = (SendChatMessageReport)message;
			String userAnswer = report.getMessage().toLowerCase().replaceAll(" ", "");
			System.out.println("[DEBUG] Answer is '" + answer + "' user answered '" + userAnswer + "'");
			if (answer.equals(userAnswer))
			{				
				try 
				{
					int playerID = PlayerManager.getSingleton().getPlayerIDFromPlayerName(report.getSenderName());
					Player player = PlayerManager.getSingleton().getPlayerFromID(playerID);
					
					ChatManager.getSingleton().sendChatToClients(player.getPlayerName() +" answered correctly.  The answer was " +question.getAnswer(),
							"Quiz Bot", new Position(0,0), ChatType.Zone);
					player.incrementQuizScore();
				} 
				catch (PlayerNotFoundException e) 
				{
					e.printStackTrace();
				}
				
				pullNewQuestion();
				askQuestion();
			}
		}
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
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes() 
	{
		ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = new ArrayList<Class<? extends QualifiedObservableReport>>();
		reportTypes.add(SendChatMessageReport.class);
		return reportTypes;
	}
}
