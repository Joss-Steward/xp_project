package model;

import java.util.ArrayList;

import model.QualifiedObservableReport;
import model.reports.SendChatMessageReport;
import datasource.DatabaseException;
import datatypes.ChatType;
import datatypes.Position;

/**
 * The Bot Behavior of the Quiznasium NPC.
 * 
 * @author Frank and Dave
 */
public class QuizBotBehavior extends NPCBehavior
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
		String questionString = question.getQuestionStatement();

		ChatManager.getSingleton().sendChatToClients(questionString, "QuizBot",
				new Position(0, 0), ChatType.Zone);
	}

	/**
	 * Gets a new random question from the database.
	 * @throws DatabaseException 
	 */
	private void pullNewQuestion()
	{
		try
		{
			question = NPCQuestion.getRandomQuestion();
		} catch (DatabaseException e)
		{
			System.out.println("Unable to retrieve a random question for the quiz bot");
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

	/**
	 * Watches SendChatMessageReports for a correct answer. On correct answer,
	 * announce the correct answer, pull a new random question, and ask that
	 * question.
	 * *
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport incomingReport)
	{
		if (incomingReport instanceof SendChatMessageReport)
		{
			String answer = question.getAnswer().toLowerCase().replaceAll(" ", "");
			SendChatMessageReport report = (SendChatMessageReport) incomingReport;
			String userAnswer = report.getMessage().toLowerCase().replaceAll(" ", "");
			System.out.println("[DEBUG] Answer is '" + answer + "' user answered '"
					+ userAnswer + "'");
			if (answer.equals(userAnswer))
			{
				try
				{
					int playerID = PlayerManager.getSingleton()
							.getPlayerIDFromPlayerName(report.getSenderName());
					Player player = PlayerManager.getSingleton()
							.getPlayerFromID(playerID);

					ChatManager.getSingleton().sendChatToClients(
							player.getPlayerName()
									+ " answered correctly.  The answer was "
									+ question.getAnswer(), "Quiz Bot",
							new Position(0, 0), ChatType.Zone);
					player.incrementQuizScore();
					ChatManager.getSingleton().sendChatToClients(
							player.getPlayerName() + " score is now "
									+ player.getQuizScore(), "Quiz Bot",
							new Position(0, 0), ChatType.Zone);
				} catch (PlayerNotFoundException e)
				{
					e.printStackTrace();
				}

				pullNewQuestion();
				askQuestion();
			}
		}
		
	}
}
