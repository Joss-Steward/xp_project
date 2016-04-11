package data;

/**
 * @author Emily Maust
 * @author Matthew Croft
 *
 */
public enum Major {

	/**
	 * 
	 */
	SOFTWARE_ENGINEERING,
	
	/**
	 * 
	 */
	COMPUTER_SCIENCE,
	
	/**
	 * 
	 */
	COMPUTER_ENGINEERING,
	
	/**
	 * 
	 */
	ELECTRICAL_ENGINEERING;
	
	/**
	 * @return a unique ID for this Major
	 */
	public int getID()
	{
		return ordinal();
	}

	/**
	 * @param id the MajorID we are interested in
	 * @return the Major with the given ID
	 */
	public static Major getMajorForID(int id)
	{
		return Major.values()[id];
	}
	
}
