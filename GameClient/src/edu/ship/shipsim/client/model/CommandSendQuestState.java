package edu.ship.shipsim.client.model;

public class CommandSendQuestState extends Command
{

	@Override
	protected boolean execute()
	{
		PlayerManager.getSingleton().getThisClientsPlayer().sendCurrentQuestStateReport();
		return true;
	}

}
