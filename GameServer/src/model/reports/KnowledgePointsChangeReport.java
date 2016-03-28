package model.reports;

import model.QualifiedObservableReport;

/**
 * @author Matthew Croft
 *
 */
public final class KnowledgePointsChangeReport implements QualifiedObservableReport
{

	/**
	 * 
	 */
	public final int knowledgePoints;

	private final int playerID;
	
	/**
	 * @param playerID of the current player
	 * @param knowledgePoints of the player
	 */
	public KnowledgePointsChangeReport (int playerID, int knowledgePoints)
	{
		this.playerID = playerID;
		this.knowledgePoints = knowledgePoints;
	}
	
	/**
	 * @return knowledgePoints
	 */
	public int getKnowledgePoints() {
		return knowledgePoints;
	}

	/**
	 * @return playerID
	 */
	public int getPlayerID() {
		return playerID;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + knowledgePoints;
		result = prime * result + playerID;
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KnowledgePointsChangeReport other = (KnowledgePointsChangeReport) obj;
		if (knowledgePoints != other.knowledgePoints)
			return false;
		if (playerID != other.playerID)
			return false;
		return true;
	}

}
