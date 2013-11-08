package model;

import data.Position;

/**
 * @author Merlin
 *
 */
public class CommandMove extends Command
{

	

	/**
	 */
	public CommandMove()
	{
		
	}

	/**
	 * @see model.Command#execute()
	 */
	@Override
	protected
	boolean execute()
	{
//		ThisClientsPlayer p = ThisClientsPlayer.getSingleton();
//		p.move(new Position(4,4));
		return true;
	}

}
