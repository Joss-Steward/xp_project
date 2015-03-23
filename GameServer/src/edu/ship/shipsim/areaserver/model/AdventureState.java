package edu.ship.shipsim.areaserver.model;

import datasource.AdventureStateEnum;

/**
 * Stores the states of all the adventures for an individual player on the
 * server
 * 
 * @author Ryan
 *
 */
public class AdventureState
{

	private int adventureID;

	private AdventureStateEnum adventureState;

	/**
	 * Constructor for the instance variables.
	 * 
	 * @param id
	 *            : id of adventure
	 * @param state
	 *            : state of adventure
	 */
	public AdventureState(int id, AdventureStateEnum state)
	{
		this.adventureID = id;
		this.adventureState = state;
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
		AdventureState other = (AdventureState) obj;
		if (adventureID != other.adventureID)
			return false;
		if (adventureState != other.adventureState)
			return false;
		return true;
	}

	/**
	 * returns the id of the current adventure
	 * 
	 * @return the id
	 */
	public int getID()
	{
		return adventureID;
	}

	/**
	 * returns the state of the current adventure.
	 * 
	 * @return the state
	 */
	public AdventureStateEnum getState()
	{
		return adventureState;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + adventureID;
		result = prime * result
				+ ((adventureState == null) ? 0 : adventureState.hashCode());
		return result;
	}

	/**
	 * Changes the state of an adventure from hidden to pending.
	 */
	public void trigger()
	{
		if (this.adventureState.equals(AdventureStateEnum.HIDDEN))
		{
			this.adventureState = AdventureStateEnum.PENDING;
		}
	}

}
