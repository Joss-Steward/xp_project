package model;

/**
 * The list of qualifers that can be used to narrow the information a QualifiedObserver gets from a QualifiedObservable
 * @author Merlin
 *
 */
public enum ObserverQualifier
{
	/**
	 * movement of the current player
	 */
	THIS_PLAYER_MOVEMENT_QUALIFIER, 
	/**
	 * movement of another player
	 */
	OTHER_PLAYER_MOVEMENT_QUALIFIER,
	/**
	 * just for testing purposes
	 */
	TEST_QUALIFIER_1, 
	/**
	 * just for testing purposes
	 */
	TEST_QUALIFIER_2

}
