package edu.ship.shipsim.areaserver.model;

import java.util.ArrayList;

import data.Position;

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
	private String q_mapName;
	private Position q_position;

	/**
	 * Creates a Quest Object
	 * @param id the id
	 * @param desc the description
	 * @param pos2 
	 * @param string 
	 * @param adventures the list of adventures
	 */
	public Quest(int id, String desc, String map, Position pos, ArrayList<Adventure> adventures) 
	{
		this.q_id = id;
		this.q_description = desc;
		this.q_mapName = map;
		this.q_position = pos;
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

	/**
	 * Return the map name the quest is on
	 * @return map name
	 */
	public String getMapName() {
		return q_mapName;
	}

	/**
	 * Set the quest's map name
	 * @param mapName
	 */
	public void setMapName(String mapName) {
		this.q_mapName = mapName;
	}

	/**
	 * Return the position of the quest
	 * @return position of quest
	 */
	public Position getPos() {
		return q_position;
	}

	/**
	 * Set the position of the quest
	 * @param pos
	 */
	public void setPos(Position pos) {
		this.q_position = pos;
	}
	
	
}