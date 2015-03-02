package datasource;

/**
 * The legal states a quest can be in for a given player
 * @author Merlin
 *
 */
public enum QuestStateList
{

	/**
	 * The player cannot see any about this quest
	 */
	HIDDEN,
	/**
	 * The player can know that there is a quest, but knows nothing about it
	 */
	AVAILABLE,
	/**
	 * The player can see the description of the quest and the adventures within it
	 */
	TRIGGERED,
	/**
	 * The player has completed enough adventures to get the points for this quest
	 */
	FULFILLED,
	/**
	 * The player has completed all of the adventures within this quest
	 */
	FINISHED
}
