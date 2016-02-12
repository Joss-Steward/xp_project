package data;

/**
 * The list of ways adventures can be completed
 * @author Merlin
 *
 */
public enum AdventureCompletionType
{
	/**
	 * 
	 */
	EXTERNAL(CriteriaString.class),
	
	/**
	 * 
	 */
	MOVEMENT(MapLocation.class),

	/**
	 * 
	 */
	CHAT(CriteriaString.class),

	/**
	 * 
	 */
	KEYSTROKE(CriteriaString.class);

	/**
	 * Get the completion type with a given ID
	 * @param id the ID
	 * @return the appropriate completion type
	 */
	public static AdventureCompletionType findByID(int id)
	{
		return AdventureCompletionType.values()[id];
	}

	private Class<? extends AdventureCompletionCriteria> completionCriteriaType;

	
	AdventureCompletionType(Class<? extends AdventureCompletionCriteria>  completionCriteriaType)
	{
		this.completionCriteriaType = completionCriteriaType;
	}
	
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
