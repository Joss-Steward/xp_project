package edu.ship.shipsim.client.model;

import java.util.ArrayList;

import communication.messages.CurrentQuestStateMessage;

import model.ClientPlayerQuest;

public class OverwriteQuestStateCommand extends Command
{

	private ArrayList<ClientPlayerQuest> clientPlayerQuestList;

	public OverwriteQuestStateCommand(CurrentQuestStateMessage msg)
	{
		this.clientPlayerQuestList = msg.getClientPlayerQuestList();
	}

	@Override
	protected boolean execute()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<ClientPlayerQuest> getClientPlayerQuestList()
	{
		return clientPlayerQuestList;
	}

}
