package model;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.junit.Test;

import data.Position;

/**
 * Test the Player classs
 * 
 * @author Merlin
 * 
 */
public class PlayerTest extends DatabaseTest
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
		JOHN(1, "male_a"),
		/**
		 * 
		 */
		MERLIN(2, "male_b");

		private int playerID;
		
		/**
		 * Get the player's unique ID 
		 * @return the id
		 */
		public int getPlayerID()
		{
			return playerID;
		}

		/**
		 * Get the player's player type
		 * @return a string that matches the name of one of the members of the enum PlayerTypes
		 */
		public String getPlayerType()
		{
			return playerType;
		}

		private String playerType;

		Players(int id, String type)
		{
			this.playerID = id;
			this.playerType = type;
		}

		
	}	
	/**
	 * Make sure we can retrieve a player's unique name from the db
	 * 
	 * @throws DatabaseException
	 *             shouldn'ts
	 */
	@Test
	public void canGetPlayerName() throws DatabaseException
	{
		Player p = new Player(1);
		assertEquals("John", p.getPlayerName());
	}
	
	/**
	 * Sets the players position and checks it
	 * @throws DatabaseException shouldn'ts
	 */
	@Test
	public void testPlayerPosition() throws DatabaseException
	{
		Player p = new Player(1);
		Position pos = new Position(3, 3);
		p.setPlayerPosition(pos);
		assertEquals(pos, p.getPlayerPosition());
		
		//Test for null
		Player c = new Player(2);
		assertEquals(null, c.getPlayerPosition());
	}

	/**
	 * Test to make sure we have a legitimate PIN
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void legitPin() throws DatabaseException
	{
		new Player(1, PlayerPin.DEFAULT_PIN);
	}

	/**
	 * Make sure it complains if we give it the wrong PIN
	 * @throws DatabaseException shouldn't
	 */
	@Test(expected = DatabaseException.class)
	public void wrongPin() throws DatabaseException
	{
		new Player(1, 1);
	}

	/**
	 * Make sure an expired PIN throws the appropriate exception
	 * @throws DatabaseException shouldn't (exception checked in the test)
	 */
	@Test
	public void oldPin() throws DatabaseException
	{
		PlayerPin playerPin = new PlayerPin(1);
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(PlayerPin.EXPIRATION_TIME_UNITS, -1
				* PlayerPin.EXPIRATION_TIME_QUANTITY - 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		playerPin.setChangedOn(sdf.format(cal.getTime()));
		boolean gotTheException = false;
		try
		{
			new Player(1, PlayerPin.DEFAULT_PIN);
		} catch (DatabaseException e)
		{
			gotTheException = true;
		}
		assertTrue(gotTheException);
	}
}
