package data;

/**
 * The list of ways adventures can be completed
 * 
 * @author Merlin
 *
 */
public enum AdventureCompletionType
{
	/**
	 * 
	 */
	REAL_LIFE(CriteriaString.class),

	/**
	 * 
	 */
	MOVEMENT(GameLocation.class),

	/**
	 * 
	 */
	CHAT(CriteriaString.class),

	/**
	 * 
	 */
	KNOWLEDGE_POINTS(CriteriaInteger.class),

	/**
	 * 
	 */
	KEYSTROKE(CriteriaString.class);

	/**
	 * Get the completion type with a given ID
	 * 
	 * @param id the ID
	 * @return the appropriate completion type
	 */
	public static AdventureCompletionType findByID(int id)
	{
		return AdventureCompletionType.values()[id];
	}

	private Class<? extends AdventureCompletionCriteria> completionCriteriaType;

	AdventureCompletionType(Class<? extends AdventureCompletionCriteria> completionCriteriaType)
	{
		this.completionCriteriaType = completionCriteriaType;
	}

	/**
	 * @return the class of the adventure completion criteria
	 */
	public Class<? extends AdventureCompletionCriteria> getCompletionCriteriaType()
	{
		return completionCriteriaType;
	}

	/**
	 * @return the unique ID of the completion type
	 */
	public int getID()
	{
		return this.ordinal();
	}
}
