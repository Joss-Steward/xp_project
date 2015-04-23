package edu.ship.shipsim.areaserver.model.reports;

import model.QualifiedObservableReport;
import datasource.AdventureStateEnum;

/**
 * Report to the client that an adventure state change had occured.
 * @author nk3668
 *
 */
public final class AdventureStateChangeReport implements QualifiedObservableReport
{
	/** (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((adventureDescription == null) ? 0 : adventureDescription
						.hashCode());
		result = prime * result + adventureID;
		result = prime * result
				+ ((newState == null) ? 0 : newState.hashCode());
		result = prime * result + playerID;
		return result;
	}

	/** (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AdventureStateChangeReport))
			return false;
		AdventureStateChangeReport other = (AdventureStateChangeReport) obj;
		if (adventureDescription == null) {
			if (other.adventureDescription != null)
				return false;
		} else if (!adventureDescription.equals(other.adventureDescription))
			return false;
		if (adventureID != other.adventureID)
			return false;
		if (newState != other.newState)
			return false;
		if (playerID != other.playerID)
			return false;
		return true;
	}

	private final int playerID;
	private final int adventureID;
	private final String adventureDescription;
	private final AdventureStateEnum newState;

	/**
	 * @param i players ID
	 * @param adventureID adventures ID to change state
	 * @param adventureDescription desc of adventure
	 * @param pending new state to be changed to
	 */
	public AdventureStateChangeReport(int i, int adventureID,
			String adventureDescription, AdventureStateEnum pending) {
		this.playerID = i;
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.newState = pending;
	}

	/**
	 * @return player ID
	 */
	public int getPlayerID() {
		return playerID;
	}

	/**
	 * @return adventure ID
	 */
	public int getAdvetnureID() {
		return adventureID;
	}

	/**
	 * @return adventure Description
	 */
	public String getAdventureDescription() {
		return adventureDescription;
	}

	/**
	 * @return new State
	 */
	public AdventureStateEnum getNewState() {
		return newState;
	}

}
