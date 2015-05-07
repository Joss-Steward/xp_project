package edu.ship.shipsim.areaserver.model;

import datasource.QuestStateEnum;

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
	 * @param from TODO
	 * @param to TODO
	 */
	public IllegalQuestChangeException(QuestStateEnum from, QuestStateEnum to)
	{
		super("You cannot change quest states from " + from + " to " + to);
	}
}
