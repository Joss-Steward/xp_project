package model;

import java.util.ArrayList;

import communication.messages.InitializeThisClientsPlayerMessage;
import datasource.LevelRecord;
import model.ClientPlayerQuest;

/**
 * Command to overwrite ThisClientsPlayers quest list
 * 
 * @author Merlin
 *
 */
public class CommandOverwriteQuestState extends Command
{

	private ArrayList<ClientPlayerQuest> clientPlayerQuestList;
	private LevelRecord record;
	private int expPoints;

	/**
	 * Initializes clientPlayerQuestList
	 * @param msg message that contains new clientPlayerQuestList
	 */
	public CommandOverwriteQuestState(InitializeThisClientsPlayerMessage msg)
	{
		this.clientPlayerQuestList = msg.getClientPlayerQuestList();
		
		this.record = msg.getLevel();
		this.expPoints = msg.getExperiencePts();
	}

	@Override
	protected boolean execute()
	{
		PlayerManager.getSingleton().getThisClientsPlayer().overwriteQuestList(clientPlayerQuestList);
		PlayerManager.getSingleton().getThisClientsPlayer().setLevelInfo(record, expPoints);
		return true;
	}

	/**
	 * @return the clientPlayerQuestList
	 */
	public ArrayList<ClientPlayerQuest> getClientPlayerQuestList()
	{
		return clientPlayerQuestList;
	}

}
