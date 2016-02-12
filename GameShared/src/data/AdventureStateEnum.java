package data;

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
	TRIGGERED,

	/**
	 * Player has been notified, nothing left to do.
	 */
	COMPLETED;

	/**
	 * @return the unique id of the enum
	 */
	public int getID()
	{
		return this.ordinal();
	}

}
