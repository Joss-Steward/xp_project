package model;

import static org.junit.Assert.*;

import java.util.Observer;

import model.reports.LoginFailedReport;
import model.reports.LoginSuccessfulReport;

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
	 * When a login is successful, the PlayerManager should send a LoginSuccessfulReport
	 */
	@Test
	public void notifiesOnSuccessfulLogin()
	{
		PlayerManager pm = PlayerManager.getSingleton();
		Observer obs = EasyMock.createMock(Observer.class);
		LoginSuccessfulReport expected = new LoginSuccessfulReport(2, "localhost",1872, 0);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, LoginSuccessfulReport.class);
		obs.update(EasyMock.eq(pm), EasyMock.eq(expected));
		EasyMock.replay(obs);
		
		pm.login(PlayerLoginTest.Players.MERLIN.getName(), PlayerLoginTest.Players.MERLIN.getPassword());
		EasyMock.verify(obs);
	}
	
	/**
	 * When a login fails, the PlayerManager should send a LoginFailedReport
	 */
	@Test
	public void notifiesOnFailedLogin()
	{
		PlayerManager pm = PlayerManager.getSingleton();
		Observer obs = EasyMock.createMock(Observer.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, LoginFailedReport.class);
		obs.update(EasyMock.eq(pm), EasyMock.isA(LoginFailedReport.class));
		EasyMock.replay(obs);
		
		pm.login(PlayerLoginTest.Players.MERLIN.getName(), PlayerLoginTest.Players.MERLIN.getPassword()+"Z");
		EasyMock.verify(obs);
	}
	
	
}
