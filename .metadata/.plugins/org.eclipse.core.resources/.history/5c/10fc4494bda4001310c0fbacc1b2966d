package model.reports;

import model.QualifiedObservableReport;

/**
 * Is sent when a player not on this client has completed the connection to an
 * area server (must be sent AFTER the map report has been sent)
 * 
 * @author Merlin
 * 
 */
public class OtherPlayerConnectedToAreaServerReport implements QualifiedObservableReport
{

	private String playerName;
	private String playerType;

	/**
	 * 
	 * @param playerName
	 *            the name of the player
	 * @param playerType
	 *            the type of the player
	 * @see view.PlayerType
	 */
	public OtherPlayerConnectedToAreaServerReport(String playerName, String playerType)
	{
		this.playerName = playerName;
		this.playerType = playerType;
	}

	/**
	 * Report the name this player used to login to the system
	 * 
	 * @return the name
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * Report the player type which reflects how this player should be
	 * displayed. This must be a string that matches the name of one of the
	 * members of the PlayerType enum.
	 * 
	 * @return the player tpye
	 */
	public String getPlayerType()
	{
		return playerType;
	}
}
