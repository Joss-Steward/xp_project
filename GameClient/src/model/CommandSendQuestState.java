package model;

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
	 * @see model.Command#execute()
	 */
	@Override
	protected boolean execute()
	{
		PlayerManager.getSingleton().getThisClientsPlayer().sendCurrentQuestStateReport();
		
		System.out.println("Cmd execute");
		
		return true;
	}

}
