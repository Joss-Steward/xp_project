package model;

import java.util.ArrayList;

import data.AdventureRecord;
import data.Position;

/**
 * The class that hold the functionality for Quest
 * 
 * @author Scott Lantz, LaVonne Diller
 *
 */
public class Quest 
{
	private String description;
	private ArrayList<AdventureRecord> adventures;
	private int questID;
	private String mapName;
	private Position position;
	private int experiencePointsGained;
	private int adventuresForFulfillment;

	/**
	 * Creates a Quest Object
	 * @param id the id
	 * @param desc the description
	 * @param map the map that the quest is on
	 * @param pos position of the quest
	 * @param adventures the list of adventures
	 * @param experiencePointsGained the number of points we get when we fulfill this quest
	 * @param adventuresForFulfillment the number of adventures we have to complete to fulfill this quest
	 */
	public Quest(int id, String desc, String map, Position pos, ArrayList<AdventureRecord> adventures, int experiencePointsGained, int adventuresForFulfillment) 
	{
		this.questID = id;
		this.description = desc;
		this.mapName = map;
		this.position = pos;
		this.adventures = adventures;
		this.experiencePointsGained = experiencePointsGained;
		this.adventuresForFulfillment = adventuresForFulfillment;
	}

	/**
	 * @return q_description the quest's description
	 */
	public String getDescription() 
	{
		return this.description;
	}
	
	/**
	 * @return list_adventures the quest's adventures
	 */
	public ArrayList<AdventureRecord> getAdventures() 
	{
		return adventures;
	}

	/**
	 * Sets the quests description
	 * @param newDesc the new description
	 */
	public void setDescription(String newDesc) 
	{
		this.description = newDesc;
	}

	/**
	 * Sets the quests adventure list
	 * @param adventures the new adventure list
	 */
	public void setAdventures(ArrayList<AdventureRecord> adventures) 
	{
		this.adventures = adventures;
	}

	/**
	 * @return q_id the quest id
	 */
	public int getQuestID() 
	{
		return this.questID;
	}

	/**
	 * Sets the quests id
	 * @param newId the new id
	 */
	public void setQuestID(int newId) 
	{
		this.questID = newId;	
	}

	/**
	 * Return the map name the quest is on
	 * @return map name
	 */
	public String getMapName() {
		return mapName;
	}

	/**
	 * Set the quest's map name
	 * @param mapName the map that the quest is on
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	/**
	 * Return the position of the quest
	 * @return position of quest
	 */
	public Position getPos() {
		return position;
	}

	/**
	 * Set the position of the quest
	 * @param pos position of the quest
	 */
	public void setPos(Position pos) {
		this.position = pos;
	}
	
	/**
	 * Get adventure description by specific adventure id
	 * @param adventureID id of the adventure
	 * @return adventure description
	 */
	public String getAdventureDescription(int adventureID)
	{
		for(AdventureRecord a: adventures)
		{
			if(a.getAdventureID() == adventureID)
			{
				return a.getAdventureDescription();
			}
		}
		
		return null;
		
	}

	/**
	 * Get adventure description by specific adventure id
	 * @param adventureID id of the adventure
	 * @return adventure description
	 */
	public int getAdventureXP(int adventureID)
	{
		for(AdventureRecord a: adventures)
		{
			if(a.getAdventureID() == adventureID)
			{
				return a.getExperiencePointsGained();
			}
		}
		
		return 0;
		
	}
	
	/**
	 * @return the number of adventures necessary to fulfill this quest
	 */
	public int getAdventuresForFulfillment()
	{
		return adventuresForFulfillment;
	}

	/**
	 * @return the number of experience points gained when we fulfill this quest
	 */
	public int getExperiencePointsGained()
	{
		return experiencePointsGained;
	}
	
}