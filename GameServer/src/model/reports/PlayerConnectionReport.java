package model.reports;

import model.Player;
import model.QualifiedObservableReport;

/**
 * This report is sent when a player successfully connects to this area server
 * 
 * @author Merlin
 * 
 */
public final class PlayerConnectionReport implements QualifiedObservableReport
{

	private final int playerID;
	private final String playerName;

	/**
	 * @param p
	 *            the player who connected to this server
	 */
	public PlayerConnectionReport(Player p)
	{
		this.playerID = p.getPlayerID();
		this.playerName = p.getPlayerName();
	}

	/**
	 * @return the player's unique ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return the player's name
	 */
	public String getPlayerName()
	{
		return playerName;
	}

}
