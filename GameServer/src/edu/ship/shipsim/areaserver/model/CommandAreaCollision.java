package edu.ship.shipsim.areaserver.model;

/**
 * Command that will trigger a players quest.
 * 
 * @author Ethan
 *
 */
public class CommandAreaCollision extends Command{
	private int playerID;
	private String areaName;

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
	 */
	@Override
	protected boolean execute() {
		//Need to get quest ID from the areaName. We activate that quest
		// on the player then check that 
		// the players quest was the correct quest.
		return false;
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
