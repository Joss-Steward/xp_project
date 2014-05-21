package model;


/**
 * 
 * @author Joshua Opends the quest screen
 */
public class CommandQuestScreenOpen extends Command

{

	/**
	 * @see Command#execute()
	 */
	@Override
	protected boolean execute()
	{
		ThisClientsPlayer p = PlayerManager.getSingleton().getThisClientsPlayer();
		p.showQuests(true);
		return true;
	}

}
