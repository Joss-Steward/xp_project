package edu.ship.shipsim.client.model;

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

}
