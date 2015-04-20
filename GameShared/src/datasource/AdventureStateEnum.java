package datasource;

/**
 * A list of the states an adventure can be in
 * 
 * @author Merlin
 *
 */
public enum AdventureStateEnum
{
	/**
	 * This adventure isn't yet available to the players.
	 * When this adventure's quest has yet to be triggered.
	 */
	HIDDEN,
	/**
	 * Adventure is ready to be completed.
	 */
	PENDING,

	/**
	 * Player has been notified, nothing left to do.
	 */
	COMPLETED

}
