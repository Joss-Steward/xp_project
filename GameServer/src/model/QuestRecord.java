package model;

import java.util.ArrayList;
import java.util.Date;

import data.QuestCompletionActionParameter;
import data.QuestCompletionActionType;
import datatypes.Position;

/**
 * The class that hold the functionality for Quest
 * 
 * @author Scott Lantz, LaVonne Diller
 *
 */
public class QuestRecord
{
	private String title;
	private String description;
	private ArrayList<AdventureRecord> adventures;
	private int questID;
	private String mapName;
	private Position position;
	private int experiencePointsGained;
	private int adventuresForFulfillment;
	private QuestCompletionActionType completionActionType;
	private QuestCompletionActionParameter completionActionParameter;
	private Date startDate;
	private Date endDate;

	/**
	 * Creates a Quest Object
	 * 
	 * @param id the id
	 * @param title The quest's title
	 * @param desc the description
	 * @param map the map that the quest is on
	 * @param pos position of the quest
	 * @param adventures the list of adventures
	 * @param experiencePointsGained the number of points we get when we fulfill
	 *            this quest
	 * @param adventuresForFulfillment the number of adventures we have to
	 *            complete to fulfill this quest
	 * @param completionActionType the type of action to do on completing a
	 *            quest
	 * @param completionActionParameter parameter for the action type
	 * @param startDate The first day the quest is available
	 * @param endDate The last day the quest is available
	 */

	public QuestRecord(int id, String title, String desc, String map, Position pos, ArrayList<AdventureRecord> adventures,
			int experiencePointsGained, int adventuresForFulfillment, QuestCompletionActionType completionActionType,
			QuestCompletionActionParameter completionActionParameter, Date startDate, Date endDate)
	{
		this.questID = id;
		this.title = title;
		this.description = desc;
		this.mapName = map;
		this.position = pos;
		this.adventures = adventures;
		this.experiencePointsGained = experiencePointsGained;
		this.adventuresForFulfillment = adventuresForFulfillment;
		this.completionActionType = completionActionType;
		this.completionActionParameter = completionActionParameter;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Get adventure by specific adventure id
	 * 
	 * @param adventureID id of the adventure
	 * @return adventure description
	 */
	public AdventureRecord getAdventureD(int adventureID)
	{
		for (AdventureRecord a : adventures)
		{
			if (a.getAdventureID() == adventureID)
			{
				return a;
			}
		}

		return null;

	}

	/**
	 * Get adventure description by specific adventure id
	 * 
	 * @param adventureID id of the adventure
	 * @return adventure description
	 */
	public String getAdventureDescription(int adventureID)
	{
		for (AdventureRecord a : adventures)
		{
			if (a.getAdventureID() == adventureID)
			{
				return a.getAdventureDescription();
			}
		}

		return null;

	}

	/**
	 * @return list_adventures the quest's adventures
	 */
	public ArrayList<AdventureRecord> getAdventures()
	{
		return adventures;
	}

	/**
	 * @return the number of adventures necessary to fulfill this quest
	 */
	public int getAdventuresForFulfillment()
	{
		return adventuresForFulfillment;
	}

	/**
	 * Get adventure description by specific adventure id
	 * 
	 * @param adventureID id of the adventure
	 * @return adventure description
	 */
	public int getAdventureXP(int adventureID)
	{
		for (AdventureRecord a : adventures)
		{
			if (a.getAdventureID() == adventureID)
			{
				return a.getExperiencePointsGained();
			}
		}

		return 0;

	}

	/**
	 * @return parameter for the action type
	 */
	public QuestCompletionActionParameter getCompletionActionParameter()
	{
		return completionActionParameter;
	}

	/**
	 * @return the type of action to do on completing a quest
	 */
	public QuestCompletionActionType getCompletionActionType()
	{
		return completionActionType;
	}

	/**
	 * @return q_description the quest's description
	 */
	public String getDescription()
	{
		return this.description;
	}

	/**
	 * @return the number of experience points gained when we fulfill this quest
	 */
	public int getExperiencePointsGained()
	{
		return experiencePointsGained;
	}

	/**
	 * Return the map name the quest is on
	 * 
	 * @return map name
	 */
	public String getMapName()
	{
		return mapName;
	}

	/**
	 * Return the position of the quest
	 * 
	 * @return position of quest
	 */
	public Position getPos()
	{
		return position;
	}

	/**
	 * @return q_id the quest id
	 */
	public int getQuestID()
	{
		return this.questID;
	}

	/**
	 * @return this quest's title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @return The first day the quest is available
	 */
	public Date getStartDate()
	{
		return startDate;
	}

	/**
	 * @return The end day the quest is available
	 */
	public Date getEndDate()
	{
		return endDate;
	}

	/**
	 * Sets the quests adventure list
	 * 
	 * @param adventures the new adventure list
	 */
	public void setAdventures(ArrayList<AdventureRecord> adventures)
	{
		this.adventures = adventures;
	}

	/**
	 * Sets the quests description
	 * 
	 * @param newDesc the new description
	 */
	public void setDescription(String newDesc)
	{
		this.description = newDesc;
	}

	/**
	 * Set the quest's map name
	 * 
	 * @param mapName the map that the quest is on
	 */
	public void setMapName(String mapName)
	{
		this.mapName = mapName;
	}

	/**
	 * Set the position of the quest
	 * 
	 * @param pos position of the quest
	 */
	public void setPos(Position pos)
	{
		this.position = pos;
	}

	/**
	 * Sets the quests id
	 * 
	 * @param newId the new id
	 */
	public void setQuestID(int newId)
	{
		this.questID = newId;
	}

	/**
	 * @param title the new title for this quest
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

}
