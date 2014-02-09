package model;

import data.Position;

/**
 * @author Merlin
 * 
 */
public class CommandMoveThisPlayer extends Command
{

	private Position newPosition;

	/**
	 * @param p the position the player should be moved to
	 */
	public CommandMoveThisPlayer(Position p)
	{
		this.newPosition = p;
	}

	/**
	 * @see model.Command#execute()
	 */
	@Override
	protected boolean execute()
	{
		ThisClientsPlayer p = PlayerManager.getSingleton().getThisClientsPlayer();
		p.move(newPosition);
		return true;
	}

}
