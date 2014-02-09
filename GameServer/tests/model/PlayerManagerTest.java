package model;

import static org.junit.Assert.*;

import java.util.Observer;

import model.reports.PlayerConnectionReport;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

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
		PlayerManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
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
		PlayerManager.getSingleton().addPlayer(1, 1234);
		assertEquals(1,PlayerManager.getSingleton().numberOfPlayers());
		Player p = PlayerManager.getSingleton().getPlayerFromID(1);
		assertEquals(1, p.getPlayerID());
	}
	
	/**
	 * Make sure that the PlayerManager notifies with the correct object type
	 */
	@Test
	public void notifiesOnAddPlayer()
	{
		Observer obs = EasyMock.createMock(Observer.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, PlayerConnectionReport.class);
		obs.update(EasyMock.eq(PlayerManager.getSingleton()), EasyMock.isA(PlayerConnectionReport.class));
		EasyMock.replay(obs);
		
		PlayerManager.getSingleton().addPlayer(2, 1234);
		EasyMock.verify(obs);
	}
	
	/**
	 * Make sure that we can get a players id from the user name
	 * @throws PlayerNotFoundException shouldn't
	 */
	@Test
	public void canGetPlayerIDFromPlayerName() throws PlayerNotFoundException
	{
		PlayerManager.getSingleton().addPlayer(1,1234);
		assertEquals(1, PlayerManager.getSingleton().getPlayerIDFromPlayerName("John"));
	}
}
