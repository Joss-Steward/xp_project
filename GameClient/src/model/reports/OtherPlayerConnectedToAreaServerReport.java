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
	private int playerID;
	private String playerName;
	private String playerType;

	/**
	 * 
	 * @param playerID 
	 * 			  the id of the player
	 * @param playerName
	 *            the name of the player
	 * @param playerType
	 *            the type of the player
	 * @see view.PlayerType
	 */
	public OtherPlayerConnectedToAreaServerReport(int playerID, String playerName, String playerType)
	{
		this.playerID = playerID;
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
	
	/** 
	 * @return The id of this player who connected
	 */
	public int getPlayerID()
	{
		return this.playerID;
	}
}
