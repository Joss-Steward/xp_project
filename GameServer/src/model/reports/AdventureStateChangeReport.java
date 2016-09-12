package model.reports;

import model.QualifiedObservableReport;
import datatypes.AdventureStateEnum;

/**
 * Report to the client that an adventure state change had occured.
 * 
 * @author nk3668
 *
 */
public final class AdventureStateChangeReport implements QualifiedObservableReport
{
	private final int playerID;
	private final int questID;
	private final int adventureID;
	private final String adventureDescription;
	private final AdventureStateEnum newState;
	private boolean realLifeAdventure;
	private String witnessTitle;

	/**
	 * @param id
	 *            players ID
	 * @param questID
	 *            id of the quest
	 * @param adventureID
	 *            adventures ID to change state
	 * @param adventureDescription
	 *            description of adventure
	 * @param newState
	 *            new state to be changed to
	 * @param realLifeAdventure
	 *            true if the player must complete this adventure outside of the
	 *            game
	 * @param witnessTitle
	 *            the title of the person who can witness completion if this is
	 *            a real life adventure
	 */
	public AdventureStateChangeReport(int id, int questID, int adventureID,
			String adventureDescription, AdventureStateEnum newState,
			boolean realLifeAdventure, String witnessTitle)
	{
		this.playerID = id;
		this.questID = questID;
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.newState = newState;
		this.realLifeAdventure = realLifeAdventure;
		this.witnessTitle = witnessTitle;
	}

	/**
	 * @return player ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return adventure ID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * @return adventure Description
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}

	/**
	 * @return new State
	 */
	public AdventureStateEnum getNewState()
	{
		return newState;
	}

	/**
	 * @return quest id
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
		result = prime * result
				+ ((adventureDescription == null) ? 0 : adventureDescription.hashCode());
		result = prime * result + adventureID;
		result = prime * result + ((newState == null) ? 0 : newState.hashCode());
		result = prime * result + playerID;
		result = prime * result + questID;
		return result;
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
		AdventureStateChangeReport other = (AdventureStateChangeReport) obj;
		if (adventureDescription == null)
		{
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
		if (questID != other.questID)
			return false;
		return true;
	}

	/**
	 * @return true if the player must complete this adventure in real life
	 */
	public boolean isRealLifeAdventure()
	{
		return realLifeAdventure;
	}

	/**
	 * @return if the player must complete this adventure in real life, the
	 *         title of the person who can witness completion
	 */
	public String getWitnessTitle()
	{
		return witnessTitle;
	}

}
