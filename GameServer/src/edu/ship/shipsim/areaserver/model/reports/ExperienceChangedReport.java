package edu.ship.shipsim.areaserver.model.reports;

import datasource.LevelRecord;
import model.QualifiedObservableReport;

/**
 * The ExperienceChangedReport class
 * @author Olivia
 * @author LaVonne
 */
public final class ExperienceChangedReport  implements QualifiedObservableReport
{

	private final int experiencePoints;
	
	private final LevelRecord record;

	/**
	 * @param experiencePoints experience points of the player
	 * @param record level record of the player
	 */
	public ExperienceChangedReport(int experiencePoints, LevelRecord record) 
	{
		this.experiencePoints = experiencePoints;
		this.record = record;
	}

	/**
	 * Gets player's current experience points
	 * @return experiencePoints
	 */
	public int getExperiencePoints() 
	{
		return experiencePoints;
	}
	
	/**
	 * Returns the player's LevelRecord 
	 * @return the record
	 */
	public LevelRecord getRecord() {
		return record;
	}

	/**
	 *  (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + experiencePoints;
		result = prime * result + ((record == null) ? 0 : record.hashCode());
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
		if (!(obj instanceof ExperienceChangedReport))
			return false;
		ExperienceChangedReport other = (ExperienceChangedReport) obj;
		if (experiencePoints != other.experiencePoints)
			return false;
		if (record == null) {
			if (other.record != null)
				return false;
		} else if (!record.equals(other.record))
			return false;
		return true;
	}

	
}
