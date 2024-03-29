package model.reports;

import model.QualifiedObservableReport;

/**
 * This report is sent by the communication layer when a player breaks its
 * socket connection.
 * 
 * @author merlin
 *
 */
public class PlayerDisconnectedReport implements QualifiedObservableReport
{

	private int playerID;

	/**
	 * @param playerID the unique ID of the player whose socket was disconnected
	 */
	public PlayerDisconnectedReport(int playerID)
	{
		this.playerID = playerID;
	}

	/**
	 * @return the player ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

}
