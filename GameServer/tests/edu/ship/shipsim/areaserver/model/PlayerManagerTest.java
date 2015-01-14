package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Observer;

import model.DatabaseException;
import model.OptionsManager;
import model.QualifiedObservableConnector;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.Position;
import datasource.PlayersForTest;
import edu.ship.shipsim.areaserver.model.Player;
import edu.ship.shipsim.areaserver.model.PlayerManager;
import edu.ship.shipsim.areaserver.model.PlayerNotFoundException;
import edu.ship.shipsim.areaserver.model.reports.PlayerConnectionReport;

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
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton(true);
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
		assertEquals(1, p.getID());
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
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void playerIsSaved() throws DatabaseException
	{
		Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
		player.setPlayerPosition(new Position(100, 100));
		assertTrue(PlayerManager.getSingleton().persistPlayer(player.getID()));
		
		PlayerManager.resetSingleton();
		
		Player fetched = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
		assertEquals(new Position(100, 100), fetched.getPlayerPosition());
	}
	
	/**
	 * Test that the known npcs will be in the database
	 * @throws DatabaseException shouldn't
	 * @throws SQLException shouldn't
	 */
	@Test
	public void testNpcsLoaded() throws DatabaseException, SQLException
	{
		OptionsManager.getSingleton(true).updateMapInformation("quiznasium.tmx", "local", 1337);
		PlayerManager.getSingleton().loadNpcs();
		for(NpcsInDB npc: NpcsInDB.values())
		{
			assertNotNull(PlayerManager.getSingleton().getPlayerFromID(npc.getPlayerID()));
		}
	}
}