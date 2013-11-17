package model;

/**
 * Closes the quest screen
 * @author Joshua
 *
 */
public class CommandQuestScreenClose extends Command
{


	/**
	 * executes the new quest screen close command
	 */
	@Override
	protected boolean execute()
	{
		ThisClientsPlayer p = ThisClientsPlayer.getSingleton();
		p.showQuests(false);
		return true;
	}
}
