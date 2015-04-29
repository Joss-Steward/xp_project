package edu.ship.shipsim.client.model;

import model.QualifiedObservableConnector;
import edu.ship.shipsim.client.model.reports.AdventureNotifcationCompleteReport;

/**
 * @author Ryan
 *
 */
public class CommandAdventureNotificationComplete extends Command 
{
	private int playerID;
	private int questID;
	private int adventureID;

	/**
	 * @param playerID the player ID
	 * @param questID the quest ID
	 * @param adventureID the adventure ID
	 */
	public CommandAdventureNotificationComplete(int playerID, int questID, int adventureID) 
	{
		this.playerID = playerID;
		this.questID = questID;
		this.adventureID = adventureID;
	}

	/**
	 * Command's execute method
	 */
	@Override
	public boolean execute() 
	{		
		AdventureNotifcationCompleteReport report = new AdventureNotifcationCompleteReport(playerID, questID, adventureID);
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

	/**
	 * @return adventureID the adventure ID
	 */
	public int getAdventureID() 
	{
		return adventureID;
	}
}
