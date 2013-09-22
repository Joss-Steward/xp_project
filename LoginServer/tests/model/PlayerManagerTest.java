package model;

import static org.junit.Assert.*;

import java.util.Observer;

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
	
	@Before
	public void setUp()
	{
		PlayerManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}

	@Test
	public void isSingleton()
	{ 
		PlayerManager pm1 = PlayerManager.getSingleton();
		PlayerManager pm2 = PlayerManager.getSingleton();
		assertSame(pm1, pm2);
		PlayerManager.resetSingleton();
		assertNotSame(pm1, PlayerManager.getSingleton());
	}

	
	
	@Test
	public void notifiesOnSuccessfulLogin()
	{
		PlayerManager pm = PlayerManager.getSingleton();
		Observer obs = EasyMock.createMock(Observer.class);
		LoginSuccessfulReport expected = new LoginSuccessfulReport(42, "localhost",1872, 12345);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, LoginSuccessfulReport.class);
		obs.update(EasyMock.eq(pm), EasyMock.eq(expected));
		EasyMock.replay(obs);
		
		pm.login("fred", "pw");
		EasyMock.verify(obs);
	}
}
