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

	private final int userID;
	private final String userName;

	/**
	 * @param p
	 *            the player who connected to this server
	 */
	public PlayerConnectionReport(Player p)
	{
		this.userID = p.getUserID();
		this.userName = p.getUserName();
	}

	/**
	 * @return the users ID
	 */
	public int getUserID()
	{
		return userID;
	}

	/**
	 * @return the users name
	 */
	public String getUserName()
	{
		return userName;
	}

}
