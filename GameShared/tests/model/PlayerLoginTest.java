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
	 * The players that are in the test database
	 * 
	 * @author Merlin
	 * 
	 */
	public enum Players
	{
		/**
		 * 
		 */
		JOHN("John", "pw"),
		/**
		 * 
		 */
		MERLIN("Merlin", "mypwd");

		private String name;
		private String password;

		Players(String n, String pwd)
		{
			this.name = n;
			this.password = pwd;
		}

		/**
		 * Get the name of the player
		 * 
		 * @return the player's name
		 */
		public String getName()
		{
			return name;
		}

		/**
		 * Get the player's password
		 * 
		 * @return the password
		 */
		public String getPassword()
		{
			return password;
		}
	}

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
		PlayerLogin pl = new PlayerLogin(Players.JOHN.getName(),
				Players.JOHN.getPassword());
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
		new PlayerLogin(Players.JOHN.getName() + "z", Players.JOHN.getPassword());
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
		new PlayerLogin(Players.JOHN.getName(), Players.JOHN.getPassword() + 'Z');
	}

}
