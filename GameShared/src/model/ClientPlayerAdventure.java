package model;

import java.io.Serializable;

import datasource.AdventureStateEnum;

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
	private int adventureID;
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
	 * @param adventureState
	 *            current state of this adventure using the AdventureStateList
	 *            enum
	 * @param needingNotification
	 *            true if the player needs to be told about the state of this
	 *            adventure
	 */
	public ClientPlayerAdventure(int adventureID, String adventureDescription,
			AdventureStateEnum adventureState, boolean needingNotification)
	{
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.adventureState = adventureState;
		this.needingNotification = needingNotification;
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
	 * Retrieve the adventure's description
	 * 
	 * @return adventureDescription ; The description of the adventure
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
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
	 * @param adventureState
	 *            the adventureState to set
	 */
	public void setAdventureState(AdventureStateEnum adventureState)
	{
		this.adventureState = adventureState;
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
	 * @param adventureDescription
	 *            the adventureDescription to set
	 */
	public void setAdventureDescription(String adventureDescription)
	{
		this.adventureDescription = adventureDescription;
	}

	/**
	 * @return true if we should notify the player about this adventure's state
	 */
	public boolean isNeedingNotification()
	{
		return needingNotification;
	}

}
