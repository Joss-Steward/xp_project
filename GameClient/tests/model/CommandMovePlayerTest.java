package model;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import model.CommandMovePlayer;
import model.MapManager;
import model.ClientPlayer;
import model.ClientPlayerManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Position;

/**
 * @author Josh
 * 
 */
public class CommandMovePlayerTest
{
	/**
	 * Create the passability map to simulate a map being loaded in.
	 */
	@Before
	public void setup()
	{
		boolean[][] passability =
		{
		{ true, true, true },
		{ true, false, true },
		{ true, true, true } };

		MapManager.getSingleton().setPassability(passability);
	}

	/**
	 * Reset the MapManager after we're done.
	 */
	@After
	public void cleanup()
	{
		MapManager.resetSingleton();
		ClientPlayerManager.resetSingleton();
	}

	/**
	 * Testing the command to move our player
	 * 
	 * @throws NotBoundException shouldn't
	 * @throws AlreadyBoundException shouldnt
	 */
	@Test
	public void testMovePlayer() throws AlreadyBoundException,
			NotBoundException
	{
		Position pos = new Position(1, 2);
		ClientPlayerManager.getSingleton().initiateLogin("1", "1");
		ClientPlayerManager.getSingleton().finishLogin(1);
		ClientPlayerManager.getSingleton().getThisClientsPlayer().setPosition(pos);
		assertEquals(new Position(1, 2), ClientPlayerManager.getSingleton().getThisClientsPlayer().getPosition());

		CommandMovePlayer cm = new CommandMovePlayer(1, new Position(1, 0));
		assertTrue(cm.execute());
		assertEquals(new Position(1, 0), ClientPlayerManager.getSingleton().getThisClientsPlayer().getPosition());
	}

	/**
	 * Let other people move anywhere they want
	 * @throws AlreadyBoundException shouldn't
	 * @throws NotBoundException shouldn't
	 */
	@Test
	public void testIllegalMoveNotThisClient() throws AlreadyBoundException,
			NotBoundException
	{
		Position pos = new Position(1, 2);
		ClientPlayer someGuy = ClientPlayerManager.getSingleton().initializePlayer(2, "1",
				"1", pos);
		ClientPlayerManager.getSingleton().initiateLogin("1", "1");
		ClientPlayerManager.getSingleton().finishLogin(1);
		assertEquals(new Position(1, 2), someGuy.getPosition());

		CommandMovePlayer cm = new CommandMovePlayer(someGuy.getID(),
				new Position(1, 1));
		assertTrue(cm.execute());
		assertEquals(new Position(1, 1), someGuy.getPosition());
	}

	/**
	 * Test attempting to move into an impassable position
	 * 
	 * @throws NotBoundException shouldn't
	 * @throws AlreadyBoundException shouldn't
	 */
	@Test
	public void testIllegalMove() throws AlreadyBoundException,
			NotBoundException
	{
		Position pos = new Position(1, 2);
		ClientPlayer me = ClientPlayerManager.getSingleton().initializePlayer(1, "1", "1",
				pos);
		ClientPlayerManager.getSingleton().initiateLogin("1", "1");
		ClientPlayerManager.getSingleton().finishLogin(1);
		assertEquals(new Position(1, 2), me.getPosition());

		CommandMovePlayer cm = new CommandMovePlayer(me.getID(), new Position(
				1, 1));
		assertFalse(cm.execute());
		assertEquals(new Position(1, 2), me.getPosition());
	}
}