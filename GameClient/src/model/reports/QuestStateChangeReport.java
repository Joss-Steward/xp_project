package model.reports;

import model.QualifiedObservableReport;
import data.QuestStateEnum;

/**
 * @author sl6469, Cody
 *
 */
/**
 * Sent when a quest moves to the needs fullfillment notification state
 * @author Merlin
 *
 */
public final class QuestStateChangeReport implements QualifiedObservableReport
{

	private final int questID;
	private String questDescription;
	private QuestStateEnum newState;

	/**
	 * @param questID the quest's unique ID
	 * @param questDescription the description of this quest
	 * @param newState the state the quest has transitioned to for this player
	 */
	public QuestStateChangeReport(int questID, String questDescription, QuestStateEnum newState)
	{
		this.questID = questID;
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
