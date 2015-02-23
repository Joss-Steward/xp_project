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
	private String state;
	
	/**
	 * The constructor for the Adventure
	 * @param desc the Adventures description
	 * @param state the Adventures state
	 */
	public Adventure(String desc, String state) 
	{
		this.description = desc;
		this.state = state;
	}

	/**
	 * @return description of Adventure
	 */
	public String getDescription() 
	{
		return this.description;
	}

	/**
	 * @return state of Adventure
	 */
	public String getState() 
	{
		return this.state;
	}

	/**
	 * Sets the Adventures description
	 * @param newDesc the new description
	 */
	public void setDescription(String newDesc) 
	{
		this.description = newDesc;
	}

	/**
	 * Sets the Adventures state
	 * @param newState the new state
	 */
	public void setState(String newState) 
	{
		this.state = newState;
	}
}
