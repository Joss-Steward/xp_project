package edu.ship.shipsim.areaserver.model.reports;

import model.QualifiedObservableReport;

/**
 * Sent when a quest moves to the needs fullfillment notification state
 * @author Merlin
 *
 */
public final class QuestStateChangeReport implements QualifiedObservableReport
{

	private final int questID;
	private final int playerID;
	private String questDescription;

	/**
	 * @param playerID the player's unique ID
	 * @param questID the quest's unique ID
	 * @param questDescription TODO
	 */
	public QuestStateChangeReport(int playerID, int questID, String questDescription)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.questDescription = questDescription;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestStateChangeReport other = (QuestStateChangeReport) obj;
		if (questID != other.questID)
			return false;
		return true;
	}

	/**
	 * @return the player whose quest state has changed
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return the questID that needs the report
	 */
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + questID;
		return result;
	}

	/**
	 * @return the description of the quest whose state has changed
	 */
	public String getQuestDescription()
	{
		return questDescription;
	}

}
