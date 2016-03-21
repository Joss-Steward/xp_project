package model.reports;

import datasource.LevelRecord;
import model.QualifiedObservableReport;

/**
 * knowledgePointsChangeReport class
 * @author Matthew Croft
 *
 */
public class knowledgePointsChangeReport implements QualifiedObservableReport
{

	/**
	 * 
	 */
	public final int knowledgePoints;

	private final LevelRecord record;

	private final int playerID;
	
	/**
	 * @param playerID of the current player
	 * @param knowledgePoints of the player
	 * @param level record for this play
	 */
	public knowledgePointsChangeReport (int playerID, int knowledgePoints, LevelRecord level)
	{
		this.playerID = playerID;
		this.record =  level;
		this.knowledgePoints = knowledgePoints;
	}
	
	/**
	 * @return knowledgePoints
	 */
	public int getKnowledgePoints() {
		return knowledgePoints;
	}

	/**
	 * @return LevelRecord
	 */
	public LevelRecord getRecord() {
		return record;
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
		result = prime * result + ((record == null) ? 0 : record.hashCode());
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
		knowledgePointsChangeReport other = (knowledgePointsChangeReport) obj;
		if (knowledgePoints != other.knowledgePoints)
			return false;
		if (playerID != other.playerID)
			return false;
		if (record == null) {
			if (other.record != null)
				return false;
		} else if (!record.equals(other.record))
			return false;
		return true;
	}


}
