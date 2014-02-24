package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the player manager to make sure it maintains the list of players correctly
 * @author merlin
 *
 */
public class PlayerManagerTest
{

	/**
	 * Always start with a new singleton
	 */
	@Before
	public void reset()
	{
		PlayerManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}
	/**
	 * There should be only one player
	 */
	@Test
	public void testSingleton()
	{
		PlayerManager player1 = PlayerManager.getSingleton();
		assertSame(player1,PlayerManager.getSingleton());
		PlayerManager.resetSingleton();
		assertNotSame(player1,PlayerManager.getSingleton());
	}
	
	/**
	 * The player manager should initialize this client's player on initialization
	 */
	@Test
	public void shouldAlwaysHaveThisClientsPlayer()
	{
		PlayerManager pm = PlayerManager.getSingleton();
		assertNotNull(pm.getThisClientsPlayer());
	}
	
	/**
	 * Make sure we can add players and retrieve them by their player names
	 */
	@Test
	public void canAddAndRetrievePlayers()
	{
		PlayerManager pm = PlayerManager.getSingleton();
		Player p1 = new Player(1);
		Player p2 = new Player(2);
		Player p3 = new Player(3);
		pm.addPlayer(1);
		assertEquals(p1, pm.getPlayerFromID(1));
		pm.addPlayer(2);
		assertEquals(p1, pm.getPlayerFromID(1));
		assertEquals(p2, pm.getPlayerFromID(2));
		pm.addPlayer(3);
		assertEquals(p1, pm.getPlayerFromID(1));
		assertEquals(p2, pm.getPlayerFromID(2));
		assertEquals(p3, pm.getPlayerFromID(3));
	}
}

