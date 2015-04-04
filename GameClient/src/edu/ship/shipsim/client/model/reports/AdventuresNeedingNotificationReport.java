package edu.ship.shipsim.client.model.reports;

import java.util.ArrayList;

import model.QualifiedObservableReport;

/**
 * This class will send a report that contains the strings of adventures that are 
 * currently of state "needing notification" so that the client
 * can be informed of their completion.
 * 
 * @author nk3668 & ew4344
 */
public final class AdventuresNeedingNotificationReport implements QualifiedObservableReport
{
	private final ArrayList<String> adventuresDescriptions = new ArrayList<String>();

	/**
	 * Constructor
	 * @param adventuresDescriptions our list of adventures descriptions to overwrite
	 */
	public AdventuresNeedingNotificationReport(ArrayList<String> adventuresDescriptions) 
	{
		for(String a : adventuresDescriptions)
		{
			this.adventuresDescriptions.add(a);
		}
	}

	/**
	 * Return adventure list's descriptions
	 * @return adventure list
	 */
	public ArrayList<String> getAdventuresDescriptionList() 
	{
		return adventuresDescriptions;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((adventuresDescriptions == null) ? 0
						: adventuresDescriptions.hashCode());
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
		AdventuresNeedingNotificationReport other = (AdventuresNeedingNotificationReport) obj;
		if (adventuresDescriptions == null) {
			if (other.adventuresDescriptions != null)
				return false;
		} else if (!adventuresDescriptions.equals(other.adventuresDescriptions))
			return false;
		return true;
	}
}
