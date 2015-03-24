package edu.ship.shipsim.client.model;

import java.util.ArrayList;

import model.ClientPlayerQuest;
import model.QualifiedObservableConnector;
import data.Position;
import edu.ship.shipsim.client.model.reports.AreaCollisionReport;
import edu.ship.shipsim.client.model.reports.QuestStateReport;

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
			QualifiedObservableConnector.getSingleton().sendReport(new AreaCollisionReport(this.id, region));
		}
	}

	/**
	 * Returns the list of quests contained by the local player.
	 * @return the quest list
	 */
	public ArrayList<ClientPlayerQuest> getQuests() 
	{
		return questList;
	}

	/**
	 * Adds a quest to the local players quest list
	 * @param q the quest being added
	 */
	public void addQuest(ClientPlayerQuest q) 
	{
		questList.add(q);
	}

	/**
	 * Overwrite ThisClientPlayer's quest list
	 * @param qList current quest list
	 */
	public void overwriteQuestList(ArrayList<ClientPlayerQuest> qList)
	{
		questList.clear();
		questList = qList;
		
	}

	/**
	 * Sends questList to QuestStateReport
	 * and notifies the Observers
	 */
	public void sendCurrentQuestStateReport()
	{
		QuestStateReport r = new QuestStateReport(questList);
		QualifiedObservableConnector.getSingleton().sendReport(r);
	}

}
