package model;

/**
 * The class that hold the functionality for Adventure
 * 
 * @author Scott Lantz, LaVonne Diller
 *
 */
public class Adventure 
{
	private String description;
	
	/**
	 * The constructor for the Adventure
	 * @param desc the Adventures description
	 * @param state the Adventures state
	 */
	public Adventure(String desc) 
	{
		this.description = desc;
	}

	/**
	 * @return description of Adventure
	 */
	public String getDescription() 
	{
		return this.description;
	}

	/**
	 * Sets the Adventures description
	 * @param newDesc the new description
	 */
	public void setDescription(String newDesc) 
	{
		this.description = newDesc;
	}

}
