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
	private String q_state;
	ArrayList<Adventure> list_adventures;
	
	/**
	 * Creates a Quest Object
	 * @param desc the description
	 * @param new_state the new state
	 * @param adventures the list of adventures
	 */
	public Quest(String desc, String new_state, ArrayList<Adventure> adventures) 
	{
		this.q_description = desc;
		this.q_state= new_state;
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
	 * @return q_state the quest's state
	 */
	public String getState() 
	{
		return this.q_state;
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
	 * Sets the quests state
	 * @param newState the new state
	 */
	public void setState(String newState) 
	{
		this.q_state = newState;
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