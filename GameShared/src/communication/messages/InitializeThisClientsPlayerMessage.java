package communication.messages;

import java.io.Serializable;
import java.util.ArrayList;

import datasource.LevelRecord;
import model.ClientPlayerQuest;

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
	private ArrayList<ClientPlayerQuest> clientPlayerQuestList;
	private int experiencePts;
	private LevelRecord level;

	/**
	 * Constructor for Quest State Message
	 * @param clientPlayerQuestList players quest list
	 */
	public InitializeThisClientsPlayerMessage(ArrayList<ClientPlayerQuest> clientPlayerQuestList, int experiencePts, LevelRecord level)
	{
		this.clientPlayerQuestList = clientPlayerQuestList;
		this.experiencePts = experiencePts;
		this.level = level;
	}

	/**
	 * Return current players quest List
	 * @return quest list
	 */
	public ArrayList<ClientPlayerQuest> getClientPlayerQuestList()
	{
		return clientPlayerQuestList;
	}
	
	/**
	 * Get experience points of this client's player
	 * @return experience pts
	 */
	public int getExperiencePts() 
	{
		return experiencePts;
	}

	/**
	 * Get level of this client's player
	 * @return level
	 */
	public LevelRecord getLevel() 
	{
		return level;
	}

}
