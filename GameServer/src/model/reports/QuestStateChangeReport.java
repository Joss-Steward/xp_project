package model.reports;

import datatypes.QuestStateEnum;
import model.QualifiedObservableReport;

/**
 * Sent when a quest changes state
 * 
 * @author Merlin
 *
 */
public final class QuestStateChangeReport implements QualifiedObservableReport
{

	private final int questID;
	private final int playerID;
	private final String questDescription;
	private final String questTitle;
	private final QuestStateEnum newState;

	/**
	 * @param playerID the player's unique ID
	 * @param questID the quest's unique ID
	 * @param questTitle this quest's title
	 * @param questDescription the description of this quest
	 * @param newState the state the quest has transitioned to for this player
	 */
	public QuestStateChangeReport(int playerID, int questID, String questTitle, String questDescription,
			QuestStateEnum newState)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.questTitle = questTitle;
		this.questDescription = questDescription;
		this.newState = newState;
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
	 * @return the state the quest has moved to
	 */
	public QuestStateEnum getNewState()
	{
		return newState;
	}

	/**
	 * @return the player whose quest state has changed
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return the description of the quest whose state has changed
	 */
	public String getQuestDescription()
	{
		return questDescription;
	}

	/**
	 * @return the questID that needs the report
	 */
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @return this quest's title
	 */
	public String getQuestTitle()
	{
		return questTitle;
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

}
