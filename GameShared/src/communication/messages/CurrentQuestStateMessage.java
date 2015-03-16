package communication.messages;

import java.util.ArrayList;

import model.ClientPlayerQuest;

/**
 * @author Merlin
 *
 */
public class CurrentQuestStateMessage implements Message
{

	
	private ArrayList<ClientPlayerQuest> clientPlayerQuestList;

	/**
	 * Constructor for Quest State Message
	 * @param clientPlayerQuestList players quest list
	 */
	public CurrentQuestStateMessage(ArrayList<ClientPlayerQuest> clientPlayerQuestList)
	{
		this.clientPlayerQuestList = clientPlayerQuestList;
	}

	/**
	 * Return current players quest List
	 * @return quest list
	 */
	public ArrayList<ClientPlayerQuest> getClientPlayerQuestList()
	{
		return clientPlayerQuestList;
	}

}
