package model.reports;

import model.Player;
import model.QualifiedObservableReport;

/**
 * This report is sent when a player successfully connects to this area server
 * @author Merlin
 *
 */
public class PlayerConnectionReport implements QualifiedObservableReport
{

	private Player player;

	/**
	 * @param p the player who connected to this server
	 */
	public PlayerConnectionReport(Player p)
	{
		this.player = p;
	}

	/**
	 * @return the users ID
	 */
	public int getUserID()
	{
		return player.getUserID();
	}

	/**
	 * @return the users name
	 * TODO should get the name from the db . . .
	 */
	public String getUserName()
	{
		return "fred";
	}

}
