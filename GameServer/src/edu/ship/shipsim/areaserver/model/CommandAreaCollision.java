package edu.ship.shipsim.areaserver.model;

public class CommandAreaCollision extends Command{
	private int playerID;
	private String areaName;

	public CommandAreaCollision(int playerID, String areaName){
		this.playerID = playerID;
		this.areaName = areaName;
	}
	@Override
	protected boolean execute() {
		//Need to get quest ID from the areaName. We activate that quest
		// on the player then check that 
		// the players quest was the correct quest.
		return false;
	}
	
	public int getPlayerID(){
		return playerID;
	}
	
	public String getAreaName(){
		return areaName;
	}

}
