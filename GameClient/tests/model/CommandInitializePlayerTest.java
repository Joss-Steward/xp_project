package model;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the CommandAddOtherPlayer class
 * @author merlin
 *
 */
public class CommandInitializePlayerTest
{

	/**
	 * Reset the PlayerManager
	 */
	@Before
	public void setup()
	{
		PlayerManager.resetSingleton();
	}
	
	/**
	 * Just make sure that new player is added to the player manager correctly
	 */
	@Test
	public void addsNewPlayerWhoIsNotThisClientsPlayer()
	{
		CommandInitializePlayer cmd = new CommandInitializePlayer(4, "Henry", "Appearance");
		PlayerManager pm = PlayerManager.getSingleton();
		assertNull (pm.getPlayerFromID(4));
		assertTrue(cmd.execute());
		Player p = pm.getPlayerFromID(4);
		assertEquals(4, p.getID());
		assertEquals("Henry", p.getName());
	}
	
	/**
	 * Just make sure that existing player is updated to the player manager correctly
	 */
	@Test
	public void updatesExistingPlayerWhoIsNotThisClientsPlayer()
	{
		CommandInitializePlayer cmd = new CommandInitializePlayer(4, "Henry", "Appearance");
		PlayerManager pm = PlayerManager.getSingleton();
		Player p = PlayerManager.getSingleton().initializePlayer(4, "4", "4");
		assertNotNull (pm.getPlayerFromID(4));
		assertTrue(cmd.execute());
		p = pm.getPlayerFromID(4);
		assertEquals(4, p.getID());
		assertEquals("Henry", p.getName());
	}
	
	/**
	 * Update the already existing thisClientsPlayer
	 * @throws NotBoundException shouldn't
	 * @throws AlreadyBoundException shouldn't
	 */
	@Test
	public void updatesExistingThisClientsPlayer() throws AlreadyBoundException, NotBoundException
	{
		CommandInitializePlayer cmd = new CommandInitializePlayer(4, "Henry", "Apperance");
		PlayerManager pm = PlayerManager.getSingleton();
		pm.initiateLogin("not", "needed");
		Player p = pm.setThisClientsPlayer(4);
		assertNotNull (pm.getPlayerFromID(4));
		assertTrue(cmd.execute());
		p = pm.getPlayerFromID(4);
		assertEquals(4, p.getID());
		assertEquals("Henry", p.getName());
	}

}
