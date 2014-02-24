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
		CommandAddOtherPlayer cmd = new CommandAddOtherPlayer(4);
		PlayerManager pm = PlayerManager.getSingleton();
		assertNull (pm.getPlayerFromID(4));
		assertTrue(cmd.execute());
		Player p = pm.getPlayerFromID(4);
		assertEquals(4, p.getPlayerID());
	}

}
