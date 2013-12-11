package model;

/**
 * 
 * @author Joshua Opends the quest screen
 */
public class CommandQuestScreenOpen extends Command

{

	/**
	 * executes the new quest screen command
	 */
	@Override
	protected boolean execute()
	{
		ThisClientsPlayer p = ThisClientsPlayer.getSingleton();
		p.showQuests(true);
		return true;
	}

}
