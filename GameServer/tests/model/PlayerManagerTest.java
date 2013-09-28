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
		PlayerManager.getSingleton().addPlayer(42, 1234);
		assertEquals(1,PlayerManager.getSingleton().numberOfPlayers());
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
		
		PlayerManager.getSingleton().addPlayer(42, 1234);
		EasyMock.verify(obs);
	}
}
