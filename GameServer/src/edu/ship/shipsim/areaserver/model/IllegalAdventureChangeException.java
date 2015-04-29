package edu.ship.shipsim.areaserver.model;

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
	 */
	public IllegalAdventureChangeException()
	{
		super("You can't do that Dave.");
	}
}
