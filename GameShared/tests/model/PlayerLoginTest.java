package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the functionality required during a login
 * 
 * @author Merlin
 * 
 */
public class PlayerLoginTest extends DatabaseTest
{


	/**
	 * If the name and password are in the db, we should be able to determine
	 * the player's unique ID
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void simpleRead() throws DatabaseException
	{
		PlayerLogin pl = PlayerLogin.readAndVerifyPlayerLogin(PlayersInDB.JOHN.getPlayerName(),
				PlayersInDB.JOHN.getPlayerPassword());
		assertEquals(1, pl.getPlayerID());
	}

	/**
	 * If the player's name isn't in the db, we should throw an exception
	 * 
	 * @throws DatabaseException
	 *             should
	 */
	@Test(expected = DatabaseException.class)
	public void notThere() throws DatabaseException
	{
		PlayerLogin.readAndVerifyPlayerLogin(PlayersInDB.JOHN.getPlayerName() + "z", PlayersInDB.JOHN.getPlayerPassword());
	}

	/**
	 * If the password given doesn't match the db, we should throw an exception
	 * 
	 * @throws DatabaseException
	 *             should
	 */
	@Test(expected = DatabaseException.class)
	public void wrongPassword() throws DatabaseException
	{
		PlayerLogin.readAndVerifyPlayerLogin(PlayersInDB.JOHN.getPlayerName(), PlayersInDB.JOHN.getPlayerPassword() + 'Z');
	}

}
