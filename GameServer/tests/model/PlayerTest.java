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

	@Test
	public void legitPin() throws DatabaseException
	{
		Player p = new Player(1);
		assertEquals("John", p.getPlayerName());
	}

	@Test(expected = DatabaseException.class)
	public void wrongPin() throws DatabaseException
	{
		new Player(1, 1);
	}

	@Test
	public void oldPin() throws DatabaseException
	{
		PlayerPin playerPin = new PlayerPin(1);
		double pin = playerPin.generatePin();
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(PlayerPin.EXPIRATION_TIME_UNITS, -1
				* PlayerPin.EXPIRATION_TIME_QUANTITY - 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		playerPin.setChangedOn(sdf.format(cal.getTime()));
		boolean gotTheException = false;
		try
		{
			Player p = new Player(1, pin);
		} catch (DatabaseException e)
		{
			gotTheException = true;
		}
		assertTrue(gotTheException);
	}
}
