package communication.messages;

import java.io.Serializable;

import datasource.QuestStateEnum;

/**
 * A message from an area server to a client telling the client to notify the
 * player that he has fulfilled a quest
 * 
 * @author Merlin
 *
 */
public class QuestStateChangeMessage implements Message, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int questID;
	private int playerID;
	private String questDescription;
	private QuestStateEnum newState;

	/**
	 * 
	 * @param playerID 
	 * 			  the ID of the player
	 * @param questID
	 *            the ID of the quest
	 * @param questDescription
	 *            the description of the quest
	 * @param newState
	 *            the state the quest has moved to
	 */
	public QuestStateChangeMessage(int playerID, int questID, String questDescription,
			QuestStateEnum newState)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.questDescription = questDescription;
		this.newState = newState;
	}

	/**
	 * @return the quest's descrption
	 */
	public String getQuestDescription()
	{
		return questDescription;
	}

	/**
	 * @return the quest's ID
	 */
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @return the state the quest has moved to
	 */
	public QuestStateEnum getNewState()
	{
		return newState;
	}

	/**
	 * Get the player's ID
	 * @return playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}
}
