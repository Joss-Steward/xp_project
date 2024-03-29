package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import model.reports.LoginSuccessfulReport;

import org.junit.Before;
import org.junit.Test;

import testData.PlayersForTest;

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
		OptionsManager.getSingleton().setTestMode(true);
		LoginPlayerManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}

	/**
	 * Make sure PlayerManager is a resetable singleton
	 */
	@Test
	public void isSingleton()
	{
		LoginPlayerManager pm1 = LoginPlayerManager.getSingleton();
		LoginPlayerManager pm2 = LoginPlayerManager.getSingleton();
		assertSame(pm1, pm2);
		LoginPlayerManager.resetSingleton();
		assertNotSame(pm1, LoginPlayerManager.getSingleton());
	}

	/**
	 * When a login is successful, the PlayerManager should send a
	 * LoginSuccessfulReport
	 * 
	 * @throws LoginFailedException shouldn't
	 */
	@Test
	public void respondsOnSuccessfulLogin() throws LoginFailedException
	{
		LoginPlayerManager pm = LoginPlayerManager.getSingleton();
		LoginSuccessfulReport expected = new LoginSuccessfulReport(PlayersForTest.MERLIN.getPlayerID(), "localhost",
				1872, 0);

		LoginSuccessfulReport actual = pm.login(PlayersForTest.MERLIN.getPlayerName(),
				PlayersForTest.MERLIN.getPlayerPassword());
		assertEquals(expected, actual);
	}

	/**
	 * When a login fails, the PlayerManager should throw an exception
	 * 
	 * @throws LoginFailedException should
	 */
	@Test(expected = LoginFailedException.class)
	public void notifiesOnFailedLogin() throws LoginFailedException
	{
		LoginPlayerManager pm = LoginPlayerManager.getSingleton();
		pm.login(PlayersForTest.MERLIN.getPlayerName(), PlayersForTest.MERLIN.getPlayerPassword() + "Z");
	}

}
