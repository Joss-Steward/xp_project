package communication.messages;

import java.util.ArrayList;

import model.ClientPlayerQuest;

public class CurrentQuestStateMessage implements Message
{

	
	private ArrayList<ClientPlayerQuest> clientPlayerQuestList;

	public CurrentQuestStateMessage(ArrayList<ClientPlayerQuest> clientPlayerQuestList)
	{
		this.clientPlayerQuestList = clientPlayerQuestList;
	}

	public ArrayList<ClientPlayerQuest> getClientPlayerQuestList()
	{
		return clientPlayerQuestList;
	}

}
