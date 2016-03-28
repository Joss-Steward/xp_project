package communication.messages;

import java.io.Serializable;

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

	private int playerID;

	/**
	 * @param knowledgePoints the player has
	 * @param level the player is
	 * @param playerID of the current player
	 */
	public KnowledgeChangedMessage(int playerID, int knowledgePoints)
	{
		this.knowledgePoints = knowledgePoints;
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
	 * @return the playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}
}
