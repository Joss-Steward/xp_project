package model;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import model.CommandInitializePlayer;
import model.ClientPlayer;
import model.ClientPlayerManager;

import org.junit.Before;
import org.junit.Test;

import data.Position;

/**
 * Tests the CommandAddOtherPlayer class
 * 
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
		ClientPlayerManager.resetSingleton();
	}

	/**
	 * Just make sure that new player is added to the player manager correctly
	 */
	@Test
	public void addsNewPlayerWhoIsNotThisClientsPlayer()
	{
		Position pos = new Position(1, 2);
		CommandInitializePlayer cmd = new CommandInitializePlayer(4, "Henry",
				"Appearance", pos);
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		assertNull(pm.getPlayerFromID(4));
		assertTrue(cmd.execute());
		ClientPlayer p = pm.getPlayerFromID(4);
		assertEquals(4, p.getID());
		assertEquals("Henry", p.getName());
		assertEquals(pos, p.getPosition());
	}

	/**
	 * Just make sure that existing player is updated to the player manager
	 * correctly
	 */
	@Test
	public void updatesExistingPlayerWhoIsNotThisClientsPlayer()
	{
		Position pos = new Position(1, 2);
		CommandInitializePlayer cmd = new CommandInitializePlayer(4, "Henry",
				"Appearance", pos);
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		ClientPlayer p = ClientPlayerManager.getSingleton().initializePlayer(4, "4", "4", pos);
		assertNotNull(pm.getPlayerFromID(4));
		assertTrue(cmd.execute());
		p = pm.getPlayerFromID(4);
		assertEquals(4, p.getID());
		assertEquals("Henry", p.getName());
		assertEquals(pos, p.getPosition());
	}

	/**
	 * Update the already existing thisClientsPlayer
	 * 
	 * @throws NotBoundException
	 *             shouldn't
	 * @throws AlreadyBoundException
	 *             shouldn't
	 */
	@Test
	public void updatesExistingThisClientsPlayer() throws AlreadyBoundException,
			NotBoundException
	{
		Position pos = new Position(1, 2);
		CommandInitializePlayer cmd = new CommandInitializePlayer(4, "Henry",
				"Apperance", pos);
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		pm.initiateLogin("not", "needed");
		ClientPlayer p = pm.finishLogin(4);
		assertNotNull(pm.getPlayerFromID(4));
		assertTrue(cmd.execute());
		p = pm.getPlayerFromID(4);
		assertEquals(4, p.getID());
		assertEquals("Henry", p.getName());
		assertEquals(pos, p.getPosition());
	}

}
