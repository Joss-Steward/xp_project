package edu.ship.shipsim.client.model;

import java.util.ArrayList;

import communication.messages.CurrentQuestStateMessage;

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

	/**
	 * Initializes clientPlayerQuestList
	 * @param msg 
	 */
	public CommandOverwriteQuestState(CurrentQuestStateMessage msg)
	{
		this.clientPlayerQuestList = msg.getClientPlayerQuestList();
	}

	@Override
	protected boolean execute()
	{
		PlayerManager.getSingleton().getThisClientsPlayer().overwriteQuestList(clientPlayerQuestList);
		return true;
	}

	public ArrayList<ClientPlayerQuest> getClientPlayerQuestList()
	{
		return clientPlayerQuestList;
	}

}
