package communication.messages;

import java.io.Serializable;

import datasource.LevelRecord;

/**
 * @author Matthew Croft
 * @author Evan Stevenson
 *
 */
public class KnowledgeChangedMessage implements Message, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int knowledgePoints;

	private LevelRecord level;
	
	private int playerID;

	/**
	 * @param knowledgePoints the player has
	 * @param level the player is
	 * @param playerID of the current player
	 */
	public KnowledgeChangedMessage(int knowledgePoints, LevelRecord level, int playerID)
	{
		this.knowledgePoints = knowledgePoints;
		this.level = level;
		this.playerID = playerID;
	}


	/**
	 * @return knowledgePoints
	 */
	public int getKnowledgePoints() 
	{
		return knowledgePoints;
	}

	/**
	 * @return the players level
	 */
	public LevelRecord getLevel() 
	{
		return level;
	}

	/**
	 * @return the playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}
}
