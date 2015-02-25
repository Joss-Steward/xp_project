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
	private ArrayList<Adventure> list_adventures;
	private int q_id;

	/**
	 * Creates a Quest Object
	 * @param id the id
	 * @param desc the description
	 * @param adventures the list of adventures
	 */
	public Quest(int id, String desc, ArrayList<Adventure> adventures) 
	{
		this.q_id = id;
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

	/**
	 * @return q_id the quest id
	 */
	public int getQuestID() 
	{
		return this.q_id;
	}

	/**
	 * Sets the quests id
	 * @param newId the new id
	 */
	public void setQuestID(int newId) 
	{
		this.q_id = newId;	
	}
}