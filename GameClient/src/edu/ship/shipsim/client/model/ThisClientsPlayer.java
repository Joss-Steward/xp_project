package edu.ship.shipsim.client.model;

import java.util.ArrayList;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;
import model.QualifiedObservableConnector;
import data.Position;
import datasource.AdventureStateEnum;
import edu.ship.shipsim.client.model.reports.AdventuresNeedingNotificationReport;
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
	
	private int experiencePoints;
	private String levelDescription;
	private int numPointsLvlRequires;
	
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
		ArrayList<String> adventuresDescriptions = new ArrayList<String>();
		
		for(ClientPlayerQuest q : questList) 
		{
			for(ClientPlayerAdventure a : q.getAdventureList())
			{
				if(a.getAdventuretState().equals(AdventureStateEnum.NEED_NOTIFICATION))
				{
					adventuresDescriptions.add(a.getAdventureDescription());
				}
			}
		}
		
		AdventuresNeedingNotificationReport report = new AdventuresNeedingNotificationReport(adventuresDescriptions);
		QualifiedObservableConnector.getSingleton().sendReport(report);
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

	/**
	 * @return the number of experience points for ThisClientsPlayer
	 */
	public int getExperiencePoints()
	{
		return experiencePoints;
	}

	/**
	 * set the number of experience points for ThisClientsPlayer
	 * @param experiencePoints ThisClientsPlayer's exp points
	 */
	public void setExperiencePoints(int experiencePoints)
	{
		this.experiencePoints = experiencePoints;
	}

	/**
	 * @return the description of the level that ThisClientsPlayer is in
	 */
	public String getLevelDescription()
	{
		return levelDescription;
	}

	/**
	 * set the level description from the report given to ThisClientsPlayer
	 * @param levelDescription the level description
	 */
	public void setLevelDescription(String levelDescription)
	{
		this.levelDescription = levelDescription;
	}

	/**
	 * @return the number of points the level requires for ThisClientsPlayer
	 * to level up
	 */
	public int getNumPointsLvlRequires()
	{
		return numPointsLvlRequires;
	}

	/**
	 * sets the number of points required for this ThisClientsPlayer to 
	 * level up for this particular level that they're in
	 * @param numPointsLvlRequires the number of points the level requires
	 */
	public void setNumPointsLvlRequires(int numPointsLvlRequires)
	{
		this.numPointsLvlRequires = numPointsLvlRequires;
	}

}
