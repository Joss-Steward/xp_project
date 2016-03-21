package model.reports;

import model.QualifiedObservableReport;
import datasource.LevelRecord;

/**
 *
 * @author Matthew Croft
 */
public final class KnowledgePointsChangeReport implements QualifiedObservableReport
{

	private final int knowledge;
	private final LevelRecord rec;

	/**
	 * Constructor
	 * @param knowledge experience points
	 * @param rec level record
	 */
	public KnowledgePointsChangeReport(int knowledge, LevelRecord rec) 
	{
		this.knowledge = knowledge;
		this.rec = rec;
	}

	/**
	 * Getter for level record
	 * @return level record
	 */
	public LevelRecord getLevelRecord() 
	{
		return rec;
	}

	/**
	 * Getter for current experience points
	 * @return experience points
	 */
	public int getKnowledgePoints() 
	{
		return knowledge;
	}

	/** 
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + knowledge;
		result = prime * result + ((rec == null) ? 0 : rec.hashCode());
		return result;
	}

	/**
	 * (non-Javadoc)
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
		if (!(obj instanceof KnowledgePointsChangeReport))
			return false;

		KnowledgePointsChangeReport other = (KnowledgePointsChangeReport) obj;
		if (knowledge != other.knowledge)
			return false;
		if (rec == null) {
			if (other.rec != null)
				return false;
		} else if (!rec.equals(other.rec))
			return false;
		return true;
	}
	
	
}
