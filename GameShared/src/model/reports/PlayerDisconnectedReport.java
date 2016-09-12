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

	public PlayerDisconnectedReport(int playerID)
	{
		this.playerID = playerID;
	}

	public int getPlayerID()
	{
		return playerID;
	}

}
