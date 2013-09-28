package model;

import model.reports.PlayerMovedReport;

/**
 * Very simple for now . . .
 * @author Merlin
 *
 */
public class Player extends QualifiedObservable
{

	private int userID;
	/**
	 * @param userID the userid of this player
	 * @param pin the pin we gave the player to connect to this area server
	 */
	public Player(int userID, int pin)
	{
		this.userID = userID;
		QualifiedObservableConnector.getSingleton().registerQualifiedObservable(this, PlayerMovedReport.class);
	}

	/**
	 * @return the userID of this player
	 */
	public int getUserID()
	{
		return userID;
	}

	/**
	 * @see model.QualifiedObservable#notifiesOn(java.lang.Class)
	 */
	@Override
	public boolean notifiesOn(Class<?> reportType)
	{
		if (reportType.equals(PlayerMovedReport.class))
		{
			return true;
		} 
		return false;
	}

}