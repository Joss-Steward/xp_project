package edu.ship.shipsim.areaserver.model;

/**
 * Stores the states of all the adventures for an individual player on the server
 * @author Ryan
 *	
 */
public class AdventureState {
	
	private int adventureID;
	private String adventureState;
	
	/**
	 * Constructor for the instance variables.
	 * @param id : id of adventure
	 * @param state : state of adventure
	 */
	public AdventureState(int id, String state) 
	{
		this.adventureID = id;
		this.adventureState = state;
	}

	/**
	 * returns the id of the current adventure
	 * @return the id
	 */
	public int getID() 
	{
		return adventureID;
	}

	/**
	 * returns the state of the current adventure.
	 * @return the state
	 */
	public String getState()
	{
		return adventureState;
	}

	/**
	 * Changes the state of an adventure from hidden to pending.
	 */
	public void trigger()
	{
		if(this.adventureState.equals("hidden")) 
		{
			this.adventureState = "pending";
		}
	}
	
}
