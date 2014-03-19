package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import data.Position;

/**
 * 
 * @author Merlin
 * 
 */
public class MovePlayerCommandTest
{

	/**
	 * Reset PlayerManager
	 */
	@Before
	public void setup()
	{
		PlayerManager.resetSingleton();
	}

	/**
	 * Update a player's position from id
	 * 
	 * @throws PlayerNotFoundException
	 *             shouldn't
	 */
	@Test
	public void testValidPlayer() throws PlayerNotFoundException
	{
		Position startPosition = new Position(0, 0);
		Position newPosition = new Position(10, 10);

		PlayerManager.getSingleton().addPlayer(1);
		Player p = PlayerManager.getSingleton().getPlayerFromID(1);
		p.setPlayerPosition(startPosition);

		assertEquals(startPosition, p.getPlayerPosition());

		MovePlayerCommand cmd = new MovePlayerCommand(1, newPosition);
		cmd.execute();

		assertEquals(newPosition, p.getPlayerPosition());
	}

	/**
	 * Update a player's position from id
	 * 
	 * @throws PlayerNotFoundException
	 *             shouldn't
	 */
	@Test
	public void testNoPlayer() throws PlayerNotFoundException
	{
		Position newPosition = new Position(10, 10);

		MovePlayerCommand cmd = new MovePlayerCommand(-1, newPosition);
		assertFalse(cmd.execute());
	}

}
