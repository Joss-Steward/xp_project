package model.reports;

import java.util.Date;
import model.QualifiedObservableReport;

/**
 * Time to level up deadline report to be sent
 * 
 * @author Evan, Chris, Marty
 */
public final class TimeToLevelUpDeadlineReport implements QualifiedObservableReport
{
	private int playerID;
	private Date timeToDeadline;
	private String nextLevel;

	/**
	 * @param playerID id of the player
	 * @param timeToDeadline time the user has to level up before being
	 *            penalized
	 * @param nextLevel next level the player needs to get to
	 */
	public TimeToLevelUpDeadlineReport(int playerID, Date timeToDeadline, String nextLevel)
	{
		this.playerID = playerID;
		this.timeToDeadline = timeToDeadline;
		this.nextLevel = nextLevel;

	}

	/**
	 * @return The player id
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return The time the user has to level up before being penalized
	 */
	public Date getTimeToDeadline()
	{
		return timeToDeadline;
	}

	/**
	 * @return The next level the player needs to get to
	 */
	public String getNextLevel()
	{
		return nextLevel;
	}

}
