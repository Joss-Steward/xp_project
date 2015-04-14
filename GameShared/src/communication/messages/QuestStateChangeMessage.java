package communication.messages;

import datasource.QuestStateEnum;

/**
 * A message from an area server to a client telling the client to notify the
 * player that he has fulfilled a quest
 * 
 * @author Merlin
 *
 */
public class QuestStateChangeMessage implements Message
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int questID;
	private String questDescription;

	/**
	 * 
	 * @param questID
	 *            the ID of the quest
	 * @param questDescription
	 *            the description of the quest
	 * @param newState TODO
	 */
	public QuestStateChangeMessage(int questID, String questDescription, QuestStateEnum newState)
	{
		this.questID = questID;
		this.questDescription = questDescription;
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

}
