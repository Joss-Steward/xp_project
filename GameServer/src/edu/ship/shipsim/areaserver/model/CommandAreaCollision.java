package edu.ship.shipsim.areaserver.model;

import datasource.DatabaseException;
import model.OptionsManager;
import edu.ship.shipsim.areaserver.datasource.MapAreaRowDataGateway;
import edu.ship.shipsim.areaserver.datasource.MapAreaRowDataGatewayMock;
import edu.ship.shipsim.areaserver.datasource.MapAreaRowDataGatewayRDS;

/**
 * Command that will trigger a players quest.
 * 
 * @author Ethan
 *
 */
public class CommandAreaCollision extends Command{
	private int playerID;
	private String areaName;
	private MapAreaRowDataGateway gateway;
	/**
	 * Constructor for CommandAreaCollision
	 * 
	 * @param playerID the players ID
	 * @param areaName the areaName the player collided with.
	 */
	public CommandAreaCollision(int playerID, String areaName){
		this.playerID = playerID;
		this.areaName = areaName;
	}
	
	/**
	 * Will trigger the quest for a player.
	 * @throws DatabaseException 
	 */
	@Override
	protected boolean execute() {
		try
		{ 
			if (OptionsManager.getSingleton().isTestMode())
			{
				this.gateway = new MapAreaRowDataGatewayMock(areaName);
			} else
			{
				this.gateway = new MapAreaRowDataGatewayRDS(areaName);
			}
		} 
		catch (DatabaseException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		int questID = gateway.getQuestID();
		Player p = PlayerManager.getSingleton().getPlayerFromID(playerID);
		p.getQuestStateByID(questID).trigger();
		return true;
	}
	
	/**
	 * Gets the ID of the player that collided with the area.
	 * 
	 * @return the players ID
	 */
	public int getPlayerID(){
		return playerID;
	}
	
	/**
	 * Gets the name of the area the player collided with.
	 * 
	 * @return the name of the area
	 */
	public String getAreaName(){
		return areaName;
	}

}
