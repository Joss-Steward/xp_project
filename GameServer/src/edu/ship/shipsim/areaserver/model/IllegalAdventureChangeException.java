package edu.ship.shipsim.areaserver.model;

import datasource.AdventureStateEnum;

/**
 * Throw an exception if you try to change to a non-legal state.
 * @author nk3668
 *
 */
public class IllegalAdventureChangeException extends Exception {
	/**
	 * ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The message thrown if you try to change to a state with 
	 * the wrong parameters
	 * @param from TODO
	 * @param to TODO
	 */
	public IllegalAdventureChangeException(AdventureStateEnum from, AdventureStateEnum to)
	{
		super("You can't change from " + from.toString() + " to " + to.toString());
	}
}
