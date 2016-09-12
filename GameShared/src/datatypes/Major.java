package datatypes;

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
	ELECTRICAL_ENGINEERING, 
	/**
	 * 
	 */
	CS_AND_E_GENERAL, 
	/**
	 * 
	 */
	OTHER;
	
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
