package data;

/**
 * The list of crews. Each player must belong to one crew
 * @author Merlin
 *
 */
public enum Crew
{
	/**
	 * 
	 */
	OFF_BY_ONE,
	
	/**
	 * 
	 */
	NULL_POINTER,
	
	/**
	 * 
	 */
	OUT_OF_BOUNDS;

	/**
	 * @return a unique ID for this crew
	 */
	public int getID()
	{
		return ordinal();
	}

	/**
	 * @param id the crewID we are interested in
	 * @return the crew with the given ID
	 */
	public static Crew getCrewForID(int id)
	{
		return Crew.values()[id];
	}
}
