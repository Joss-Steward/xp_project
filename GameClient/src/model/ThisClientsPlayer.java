package model;

import Quest.QuestManager;
import model.reports.QuestScreenReport;

/**
 * The player who is playing the game
 * 
 * @author merlin
 * 
 */
public class ThisClientsPlayer extends Player
{
	private QuestManager questManager;

	protected ThisClientsPlayer(int playerID)
	{
		super(playerID);

		this.questManager = new QuestManager();
		reportTypes.add(QuestScreenReport.class);

		this.registerReportTypesWeNotify();
	}

	/**
	 * notifies that the player has updated quest information
	 * 
	 * @param b
	 *            Closing quest screen or opening
	 */
	public void showQuests(boolean b)
	{
		System.out.println("hello from the player. Showing quests is " + b);
		notifyObservers(new QuestScreenReport(b));
	}

	/**
	 * getter for quest manager
	 * 
	 * @return QuestManager this client's manager
	 */
	public QuestManager getQuestManager()
	{
		return questManager;
	}
}
