package model;

import static org.junit.Assert.*;

import java.util.Observer;

import model.reports.PlayerConnectionReport;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.Position;

/**
 * @author Merlin
 * 
 */
public class PlayerManagerTest
{

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		QualifiedObservableConnector.resetSingleton();
		PlayerManager.resetSingleton();
		PlayerManager playerManager = PlayerManager.getSingleton();
		assertEquals(0, playerManager.countObservers());
		assertEquals(0, playerManager.countObservers(PlayerConnectionReport.class));

	}

	/**
	 * Make sure PlayerManager is a resetable singleton
	 */
	@Test
	public void isSingleton()
	{
		PlayerManager pm1 = PlayerManager.getSingleton();
		PlayerManager pm2 = PlayerManager.getSingleton();
		assertSame(pm1, pm2);
		PlayerManager.resetSingleton();
		assertNotSame(pm1, PlayerManager.getSingleton());
	}

	/**
	 * Make sure we can add a player to the listS
	 */
	@Test
	public void canAddPlayer()
	{
		PlayerManager.getSingleton().addPlayer(1);
		assertEquals(1, PlayerManager.getSingleton().numberOfPlayers());
		Player p = PlayerManager.getSingleton().getPlayerFromID(1);
		assertEquals(1, p.getPlayerID());
		assertTrue(PlayerManager.getSingleton().getConnectedPlayers().contains(p));
	}

	/**
	 * Make sure that the PlayerManager notifies with the correct object type
	 */
	@Test
	public void notifiesOnAddPlayer()
	{
		System.out.println("starting . . . . .");
		Observer obs = EasyMock.createMock(Observer.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				PlayerConnectionReport.class);
		obs.update(EasyMock.eq(PlayerManager.getSingleton()),
				EasyMock.isA(PlayerConnectionReport.class));
		EasyMock.replay(obs);

		assertEquals(1,
				PlayerManager.getSingleton().countObservers(PlayerConnectionReport.class));
		PlayerManager.getSingleton().addPlayer(2);
		EasyMock.verify(obs);
	}

	/**
	 * Make sure that we can get a players id from the player name
	 * 
	 * @throws PlayerNotFoundException
	 *             shouldn't
	 */
	@Test
	public void canGetPlayerIDFromPlayerNameOnlyOne() throws PlayerNotFoundException
	{
		PlayerManager.getSingleton().addPlayer(1);
		assertEquals(1, PlayerManager.getSingleton().getPlayerIDFromPlayerName("John"));
	}

	/**
	 * Make sure that we can get a players id from the player name
	 * 
	 * @throws PlayerNotFoundException
	 *             shouldn't
	 */
	@Test
	public void canGetPlayerIDFromPlayerName() throws PlayerNotFoundException
	{
		PlayerManager.getSingleton().addPlayer(1);
		PlayerManager.getSingleton().addPlayer(2);
		assertEquals(2, PlayerManager.getSingleton().getPlayerIDFromPlayerName("Merlin"));
	}

	/**
	 * Make sure the correct exception is thrown if we search for a player whose
	 * name we don't know
	 * 
	 * @throws PlayerNotFoundException
	 *             should
	 */
	@Test(expected = PlayerNotFoundException.class)
	public void playerNameNotFound() throws PlayerNotFoundException
	{
		PlayerManager.getSingleton().getPlayerIDFromPlayerName("henry");
	}

	/**
	 * Test that a player can be persisted by saving an attribute and fetching the player again
	 */
	@Test
	public void playerIsSaved()
	{
		Player player = PlayerManager.getSingleton().addPlayer(PlayersInDB.MERLIN.getPlayerID());
		player.setPlayerPosition(new Position(100, 100));
		assertTrue(PlayerManager.getSingleton().persistPlayer(player.getPlayerID()));
		
		PlayerManager.resetSingleton();
		
		Player fetched = PlayerManager.getSingleton().addPlayer(PlayersInDB.MERLIN.getPlayerID());
		assertEquals(new Position(100, 100), fetched.getPlayerPosition());
	}
}