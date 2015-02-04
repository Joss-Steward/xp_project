package edu.ship.shipsim.client.model;

import data.Position;
import edu.ship.shipsim.client.model.reports.AreaCollisionReport;
import edu.ship.shipsim.client.model.reports.QuestScreenReport;
import Quest.QuestManager;

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
		reportTypes.add(AreaCollisionReport.class);
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
	
	/**
	 * Moves this player and report if they have entered into any regions
	 * @param pos 
	 * 		the location to move to
	 */
	public void move(Position pos)
	{
		super.move(pos);
		
		//check if entering a region
		String region = MapManager.getSingleton().getIsInRegion(pos);
		if (region != null)
		{
			this.notifyObservers(new AreaCollisionReport(this.id, region));
		}
	}
}
