package model;

/**
 * Holds the information about one player in the system
 * @author merlin
 *
 */
public class Player
{

	private String userName;

	/**
	 * Create a player 
	 * @param userName the unique userName of this player
	 */
	public Player(String userName)
	{
		this.userName = userName;
	}

	/**
	 * Get the unique user name for this player
	 * @return the user name
	 */
	public String getUserName()
	{
		return userName;
	}

}
