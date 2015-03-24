package edu.ship.shipsim.client.model;

/**
 * Command to send quest state report for This Clients Player
 * 
 * @author Merlin
 *
 */
public class CommandSendQuestState extends Command
{

	/**
	 * Sends CurrentQuestStateReport for ThisClientsPlayer
	 * @see edu.ship.shipsim.client.model.Command#execute()
	 */
	@Override
	protected boolean execute()
	{
		PlayerManager.getSingleton().getThisClientsPlayer().sendCurrentQuestStateReport();
		
		System.out.println("Got to cmd execute");
		return true;
	}

}
