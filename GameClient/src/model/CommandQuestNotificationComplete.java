package model;

import model.QualifiedObservableConnector;
import model.reports.QuestNotificationCompleteReport;

/**
 * @author Merlin
 *
 */
public class CommandQuestNotificationComplete extends Command
{
	private int playerID;
	private int questID;

	/**
	 * @param playerID
	 *            the player ID
	 * @param questID
	 *            the quest ID
	 */
	public CommandQuestNotificationComplete(int playerID, int questID)
	{
		this.playerID = playerID;
		this.questID = questID;
	}

	/**
	 * Command's execute method
	 */
	@Override
	public boolean execute()
	{
		QuestNotificationCompleteReport report = new QuestNotificationCompleteReport(
				playerID, questID);
		QualifiedObservableConnector.getSingleton().sendReport(report);
		return true;
	}

	/**
	 * @return playerID the players ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return questID the quest ID
	 */
	public int getQuestID()
	{
		return questID;
	}
}
