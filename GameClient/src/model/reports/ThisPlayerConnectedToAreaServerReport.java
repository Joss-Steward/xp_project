package model.reports;

import model.QualifiedObservableReport;

/**
 * Is sent when the player on this client has completed the connection to an area server (must be sent AFTER the map report has been sent)
 * @author Merlin
 *
 */
public class ThisPlayerConnectedToAreaServerReport implements QualifiedObservableReport
{

	private String playerName;
	private String playerType;

	/**
	 * 
	 * @param playerName the name of the player
	 * @param playerType the type of the player
	 */
	public ThisPlayerConnectedToAreaServerReport(String playerName, String playerType)
	{
		this.playerName = playerName;
		this.playerType = playerType;
	}

	public String getPlayerName()
	{
		return playerName;
	}

	public String getPlayerType()
	{
		return playerType;
	}
}
