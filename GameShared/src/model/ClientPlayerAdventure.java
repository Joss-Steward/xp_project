package model;

import datasource.AdventureStateEnum;

/**
 * Stores the adventure for the GameClient which encapsulates the 
 * AdventureState and Adventure on the game server
 * @author Nathaniel
 *
 */
public class ClientPlayerAdventure 
{
	private int adventureID;
	private String adventureDescription;
	private AdventureStateEnum adventureState;
	
	/**
	 * Basic constructor for ClientPlayerAdventure
	 * @param adventureID ; unique identifier for this adventure
	 * @param adventureDescription ; description of the adventure
	 * @param adventureState ; current state of this adventure using the AdventureStateList enum
	 */
	public ClientPlayerAdventure(int adventureID, String adventureDescription, AdventureStateEnum adventureState) 
	{
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.adventureState = adventureState;
	}

	/**
	 * Retrieve the adventure's ID
	 * @return adventureID ; the adventure's unique identifier
	 */
	public int getAdventureID() 
	{
		return adventureID;
	}

	/**
	 * Retrieve the adventure's description
	 * @return adventureDescription ; The description of the adventure
	 */
	public String getAdventureDescription() 
	{
		return adventureDescription;
	}

	/**
	 * Retrieve the adventure's state 
	 * @return adventureState ; The current state of the adventure. Uses the enum AdventureStateList
	 */
	public AdventureStateEnum getAdventuretState() 
	{
		return adventureState;
	}

	/**
	 * @return the adventureState
	 */
	public AdventureStateEnum getAdventureState() {
		return adventureState;
	}

	/**
	 * @param adventureState the adventureState to set
	 */
	public void setAdventureState(AdventureStateEnum adventureState) {
		this.adventureState = adventureState;
	}

	/**
	 * @param adventureID the adventureID to set
	 */
	public void setAdventureID(int adventureID) {
		this.adventureID = adventureID;
	}

	/**
	 * @param adventureDescription the adventureDescription to set
	 */
	public void setAdventureDescription(String adventureDescription) {
		this.adventureDescription = adventureDescription;
	}
	
}
