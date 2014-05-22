package edu.ship.shipsim.client.model;

/**
 * Closes the quest screen
 * 
 * @author Joshua
 * 
 */
public class CommandQuestScreenClose extends Command
{

	/**
	 * @see Command#execute()
	 */
	@Override
	protected boolean execute()
	{
		ThisClientsPlayer p = PlayerManager.getSingleton().getThisClientsPlayer();
		p.showQuests(false);
		return true;
	}
}
