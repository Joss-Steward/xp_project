package edu.ship.shipsim.areaserver.model;

/**
 * Thrown if you try to change quest states to an illegal state.
 * @author nk3668
 *
 */
public class IllegalQuestChangeException extends Exception {
	/**
	 * ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The message thrown if you try to change to a state with 
	 * the wrong parameters
	 */
	public IllegalQuestChangeException()
	{
		super("You cannot change to that state from the state you are in.");
	}
}
