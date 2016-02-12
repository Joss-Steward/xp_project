package model;

import java.io.Serializable;

import data.AdventureStateEnum;

/**
 * Stores the adventure for the GameClient which encapsulates the AdventureState
 * and Adventure on the game server
 * 
 * @author Nathaniel
 *
 */
public class ClientPlayerAdventure implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int adventureID, adventureXP;

	private String adventureDescription;
	private AdventureStateEnum adventureState;
	private boolean needingNotification;
	/**
	 * Basic constructor for ClientPlayerAdventure
	 * 
	 * @param adventureID
	 *            unique identifier for this adventure
	 * @param adventureDescription
	 *            description of the adventure
	 * @param adventureXP
	 *            xp reward for adventure
	 * @param adventureState
	 *            current state of this adventure using the AdventureStateList
	 *            enum
	 * @param needingNotification
	 *            true if the player needs to be told about the state of this
	 *            adventure
	 */
	public ClientPlayerAdventure(int adventureID, String adventureDescription, int adventureXP,
			AdventureStateEnum adventureState, boolean needingNotification)
	{
		this.adventureID = adventureID;
		this.adventureXP = adventureXP;
		this.adventureDescription = adventureDescription;
		this.adventureState = adventureState;
		this.needingNotification = needingNotification;
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
		ClientPlayerAdventure other = (ClientPlayerAdventure) obj;
		if (adventureDescription == null)
		{
			if (other.adventureDescription != null)
				return false;
		} else if (!adventureDescription.equals(other.adventureDescription))
			return false;
		if (adventureID != other.adventureID)
			return false;
		if (adventureState != other.adventureState)
			return false;
		if (adventureXP != other.adventureXP)
			return false;
		if (needingNotification != other.needingNotification)
			return false;
		return true;
	}

	/**
	 * Retrieve the adventure's description
	 * 
	 * @return adventureDescription ; The description of the adventure
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}

	/**
	 * Retrieve the adventure's ID
	 * 
	 * @return adventureID ; the adventure's unique identifier
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * Retrieve the adventure's state
	 * 
	 * @return adventureState ; The current state of the adventure. Uses the
	 *         enum AdventureStateList
	 */
	public AdventureStateEnum getAdventureState()
	{
		return adventureState;
	}

	/**
	 * @return the adventureXP
	 */
	public int getAdventureXP()
	{
		return adventureXP;
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
		result = prime * result
				+ ((adventureState == null) ? 0 : adventureState.hashCode());
		result = prime * result + adventureXP;
		result = prime * result + (needingNotification ? 1231 : 1237);
		return result;
	}

	/**
	 * @return true if we should notify the player about this adventure's state
	 */
	public boolean isNeedingNotification()
	{
		return needingNotification;
	}

	/**
	 * @param adventureDescription
	 *            the adventureDescription to set
	 */
	public void setAdventureDescription(String adventureDescription)
	{
		this.adventureDescription = adventureDescription;
	}

	/**
	 * @param adventureID
	 *            the adventureID to set
	 */
	public void setAdventureID(int adventureID)
	{
		this.adventureID = adventureID;
	}

	/**
	 * @param adventureState
	 *            the adventureState to set
	 */
	public void setAdventureState(AdventureStateEnum adventureState)
	{
		this.adventureState = adventureState;
	}

	/**
	 * @param adventureXP the adventureXP to set
	 */
	public void setAdventureXP(int adventureXP)
	{
		this.adventureXP = adventureXP;
	}

}
