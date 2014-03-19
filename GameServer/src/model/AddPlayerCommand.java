package model;

/**
 * Command used to add a new player to this area server's part of the game
 * 
 * @author Merlin
 * 
 */
public class AddPlayerCommand extends Command
{

	private int playerID;
	private double pin;

	/**
	 * 
	 * @param playerID
	 *            the player's player id
	 * @param pin
	 *            the player's pin
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
		try
		{
			PlayerManager.getSingleton().addPlayer(playerID, pin);
		} catch (DatabaseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
