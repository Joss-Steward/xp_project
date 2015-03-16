package edu.ship.shipsim.client.model;

import java.util.ArrayList;

import model.ClientPlayerQuest;
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
	ArrayList<ClientPlayerQuest> questList = new ArrayList<ClientPlayerQuest>();
	
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
	 * Returns the list of quests contained by the local player.
	 * @return the quest list
	 */
	public ArrayList<ClientPlayerQuest> getQuests() {
		return questList;
	}

	/**
	 * Adds a quest to the local players quest list
	 * @param q the quest being added
	 */
	public void addQuest(ClientPlayerQuest q) {
		questList.add(q);
	}

	/**
	 * Overwrite ThisClientPlayer's quest list
	 * @param qList current quest list
	 */
	public void overwriteQuestList(ArrayList<ClientPlayerQuest> qList)
	{
		questList = qList;
		
	}

}
