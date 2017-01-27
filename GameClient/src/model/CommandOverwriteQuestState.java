package model;

import java.util.ArrayList;

import communication.messages.InitializeThisClientsPlayerMessage;
import data.ClientPlayerQuestState;
import datasource.LevelRecord;

/**
 * Command to overwrite ThisClientsPlayers quest list
 * 
 * @author Merlin
 *
 */
public class CommandOverwriteQuestState extends Command
{

	private ArrayList<ClientPlayerQuestState> clientPlayerQuestList;
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
		ClientPlayerManager.getSingleton().getThisClientsPlayer().overwriteQuestList(clientPlayerQuestList);
		ClientPlayerManager.getSingleton().getThisClientsPlayer().setLevelInfo(record, expPoints);
		return true;
	}

	/**
	 * @return the clientPlayerQuestList
	 */
	public ArrayList<ClientPlayerQuestState> getClientPlayerQuestList()
	{
		return clientPlayerQuestList;
	}

}
