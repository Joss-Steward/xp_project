package model;

/**
 * Command used to add a new player to this area server's part of the game
 * @author Merlin
 *
 */
public class AddPlayerCommand extends Command
{

	private int playerID;
	private double pin;

	/**
	 * 
	 * @param playerID the user's player id
	 * @param pin the user's pin
	 */
	public AddPlayerCommand(int playerID, double pin)
	{
		this.playerID = playerID;
		this.pin = pin;
	}

	/**
	 * 
	 * @see model.Command#execute()
	 */
	@Override
	protected boolean execute()
	{
		PlayerManager.getSingleton().addPlayer(playerID, pin);
		return true;
	}

}
