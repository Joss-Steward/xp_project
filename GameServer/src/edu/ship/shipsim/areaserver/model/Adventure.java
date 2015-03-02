package edu.ship.shipsim.areaserver.model;

/**
 * The class that hold the functionality for Adventure
 * 
 * @author Scott Lantz, LaVonne Diller
 *
 */
public class Adventure 
{
	private String description;
	private int adv_id;
	
	/**
	 * The constructor for the Adventure
	 * @param id   the id of the Adventure
	 * @param desc the Adventures description
	 */
	public Adventure(int id, String desc) 
	{
		this.adv_id = id;
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
	
	/**
	 * @return ID of the Adventure
	 */
	public int getID() 
	{
		return this.adv_id;
	}

	/**
	 * Sets the Adventures ID
	 * @param newID the new ID
	 */
	public void setID(int newID) 
	{
		this.adv_id = newID;
	}

}
