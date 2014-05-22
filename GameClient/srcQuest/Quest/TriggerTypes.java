package Quest;

/**
 * @author Joshua Enum of triggers that are found in the quest layer
 */
public enum TriggerTypes
{

	/**
	 * Activates a quest
	 */
	ACTIVATE_QUEST,

	/**
	 * Activates a task
	 */
	ACTIVATE_TASK,

	/**
	 * Task was completed by talking to character
	 */
	TASK_COMPLETED_TALKING,

	/**
	 * Task was completed by entering a new area
	 */
	TASK_COMPLETED_WALKED_ON_TILE,

	/**
	 * Task was completed by picking up an item
	 */
	TASK_COMPLETED_AQUIRED_ITEM,

}
