package edu.ship.shipsim.client.model;

import java.util.ArrayList;

import model.Quest;
import data.Position;
import edu.ship.shipsim.client.model.reports.AreaCollisionReport;

/**
 * The player who is playing the game
 * 
 * @author merlin
 * 
 */
public class ThisClientsPlayer extends Player
{
	private ArrayList<Quest> questList = new ArrayList<Quest>();
	
	protected ThisClientsPlayer(int playerID)
	{
		super(playerID);

		reportTypes.add(AreaCollisionReport.class);
		this.registerReportTypesWeNotify();
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

	/**
	 * Add quest to questList
	 * @param q : quest that needs to be added to questList
	 */
	public void addQuest(Quest q) 
	{
		questList.add(q);
	}

	/**
	 * Return the player's quest list
	 * @return the questList
	 */
	public ArrayList<Quest> getQuests() 
	{
		return questList;
	}
}
