package communication.messages;

import java.io.Serializable;
import java.util.ArrayList;

import data.ClientPlayerQuestState;
import datasource.LevelRecord;

/**
 * @author Merlin
 * @author Olivia
 * @author LaVonne
 */
public class InitializeThisClientsPlayerMessage implements Message, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<ClientPlayerQuestState> clientPlayerQuestList;

	private int experiencePts;
	private LevelRecord level;
	private int knowledgePoints;

	/**
	 * Constructor for Quest State Message
	 * 
	 * @param clientPlayerQuestList players quest list
	 * @param experiencePts player's experience points
	 * @param level LevelRecord
	 */
	public InitializeThisClientsPlayerMessage(ArrayList<ClientPlayerQuestState> clientPlayerQuestList, int experiencePts,
			LevelRecord level)
	{
		this.clientPlayerQuestList = clientPlayerQuestList;
		this.experiencePts = experiencePts;
		this.level = level;
	}

	/**
	 * @param clientPlayerQuestList players quest list
	 * @param experiencePts player's experience points
	 * @param level LevelRecord
	 * @param knowledgePoints for this player
	 */
	public InitializeThisClientsPlayerMessage(ArrayList<ClientPlayerQuestState> clientPlayerQuestList, int experiencePts,
			int knowledgePoints, LevelRecord level)
	{
		this.clientPlayerQuestList = clientPlayerQuestList;
		this.experiencePts = experiencePts;
		this.knowledgePoints = knowledgePoints;
		this.level = level;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InitializeThisClientsPlayerMessage other = (InitializeThisClientsPlayerMessage) obj;
		if (clientPlayerQuestList == null)
		{
			if (other.clientPlayerQuestList != null)
				return false;
		} else if (!clientPlayerQuestList.equals(other.clientPlayerQuestList))
			return false;
		if (experiencePts != other.experiencePts)
			return false;
		if (level == null)
		{
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		return true;
	}

	/**
	 * Return current players quest List
	 * 
	 * @return quest list
	 */
	public ArrayList<ClientPlayerQuestState> getClientPlayerQuestList()
	{
		return clientPlayerQuestList;
	}

	/**
	 * Get experience points of this client's player
	 * 
	 * @return experience pts
	 */
	public int getExperiencePts()
	{
		return experiencePts;
	}

	/**
	 * Get level of this client's player
	 * 
	 * @return level
	 */
	public LevelRecord getLevel()
	{
		return level;
	}

	/**
	 * @return the knowledgePoints of this player
	 */
	public int getKnowledgePoints()
	{
		return knowledgePoints;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientPlayerQuestList == null) ? 0 : clientPlayerQuestList.hashCode());
		result = prime * result + experiencePts;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		return result;
	}

}
