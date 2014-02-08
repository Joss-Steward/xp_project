package model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the CommandAddOtherPlayer class
 * @author merlin
 *
 */
public class CommandAddOtherPlayerTest
{

	/**
	 * Just make sure that new player is added to the player manager correctly
	 */
	@Test
	public void addsCorrectly()
	{
		CommandAddOtherPlayer cmd = new CommandAddOtherPlayer("Harry");
		PlayerManager pm = PlayerManager.getSingleton();
		assertNull (pm.getPlayerNamed("Harry"));
		assertTrue(cmd.execute());
		Player p = pm.getPlayerNamed("Harry");
		assertEquals("Harry", p.getUserName());
	}

}
