package model;

import java.util.ArrayList;

/**
 * The class that hold the functionality for Quest
 * 
 * @author Scott Lantz, LaVonne Diller
 *
 */
public class Quest 
{
	private String q_description;
	ArrayList<Adventure> list_adventures;
	
	/**
	 * Creates a Quest Object
	 * @param desc the description
	 * @param adventures the list of adventures
	 */
	public Quest(String desc, ArrayList<Adventure> adventures) 
	{
		this.q_description = desc;
		this.list_adventures = adventures;
	}

	/**
	 * @return q_description the quest's description
	 */
	public String getDescription() 
	{
		return this.q_description;
	}
	
	/**
	 * @return list_adventures the quest's adventures
	 */
	public ArrayList<Adventure> getAdventures() 
	{
		return list_adventures;
	}

	/**
	 * Sets the quests description
	 * @param newDesc the new description
	 */
	public void setDescription(String newDesc) 
	{
		this.q_description = newDesc;
	}

	/**
	 * Sets the quests adventure list
	 * @param adventures the new adventure list
	 */
	public void setAdventures(ArrayList<Adventure> adventures) 
	{
		this.list_adventures = adventures;
	}
}