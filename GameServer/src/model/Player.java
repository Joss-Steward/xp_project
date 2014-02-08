package model;

import model.reports.PlayerMovedReport;
import model.reports.QuestScreenReport;

/**
 * Very simple for now . . .
 * 
 * @author Merlin
 * 
 */
public class Player extends QualifiedObservable
{

	private int userID;

	/**
	 * @param userID
	 *            the userid of this player
	 * @param pin
	 *            the pin we gave the player to connect to this area server
	 */
	public Player(int userID, double pin)
	{
		this.userID = userID;
		QualifiedObservableConnector.getSingleton()
				.registerQualifiedObservable(this, PlayerMovedReport.class);
		// this.setQuestManager(new QuestManager());

		QualifiedObservableConnector.getSingleton()
				.registerQualifiedObservable(this, QuestScreenReport.class);
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

	/**
	 * TODO need to fix so players know their names
	 * 
	 * @return fred
	 */
	public String getUserName()
	{
		return "fred";
	}

}
