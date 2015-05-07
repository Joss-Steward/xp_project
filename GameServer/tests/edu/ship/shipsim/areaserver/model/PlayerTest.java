package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import model.OptionsManager;
import model.PlayerConnection;
import model.QualifiedObservableConnector;
import model.QualifiedObserver;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Position;
import datasource.DatabaseException;
import datasource.DatabaseTest;
import datasource.PlayersForTest;
import edu.ship.shipsim.areaserver.datasource.QuestStateTableDataGatewayMock;
import edu.ship.shipsim.areaserver.model.Player;
import edu.ship.shipsim.areaserver.model.PlayerManager;
import edu.ship.shipsim.areaserver.model.reports.ExperienceChangedReport;

/**
 * Test the Player class
 * 
 * @author Merlin
 * 
 */
public class PlayerTest extends DatabaseTest
{

	private PlayerManager playerManager;

	/**
	 * make sure the necessary singletons are reset
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		super.setUp();
		OptionsManager.getSingleton().setTestMode(true);
		QualifiedObservableConnector.resetSingleton();
		PlayerManager.resetSingleton();
		playerManager = PlayerManager.getSingleton();
		QuestStateTableDataGatewayMock.getSingleton().resetData();
	}

	/**
	 * cleanup the singletons we played with
	 * @see datasource.DatabaseTest#tearDown()
	 */
	@After
	public void tearDown() throws DatabaseException, SQLException
	{
		super.tearDown();
		QualifiedObservableConnector.resetSingleton();
		PlayerManager.resetSingleton();
	}
	/**
	 * Make sure we can retrieve a player's appearanceType
	 */
	@Test
	public void canGetAppearanceType()
	{
		Player p = playerManager.addPlayer(1);
		assertEquals(PlayersForTest.JOHN.getAppearanceType(), p.getAppearanceType());
	}

	/**
	 * Make sure we can retrieve a player's unique name from the db
	 * 
	 * @throws DatabaseException
	 *             shouldn'ts
	 */
	@Test
	public void canGetPlayerName() throws DatabaseException
	{
		Player p = playerManager.addPlayer(1);
		assertEquals("John", p.getPlayerName());
	}

	/**
	 * Test to make sure we have a legitimate PIN
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void legitPin() throws DatabaseException, IllegalQuestChangeException
	{
		PlayerConnection playerPin = new PlayerConnection(1);
		playerPin.generateTestPin();
		playerManager.addPlayer(1, PlayerConnection.DEFAULT_PIN);
	}

	/**
	 * Make sure an expired PIN throws the appropriate exception
	 * 
	 * @throws DatabaseException
	 *             shouldn't (exception checked in the test)
	 */
	@Test
	public void oldPin() throws DatabaseException
	{
		PlayerConnection playerPin = new PlayerConnection(1);
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(PlayerConnection.EXPIRATION_TIME_UNITS, -1 * PlayerConnection.EXPIRATION_TIME_QUANTITY
				- 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		playerPin.setChangedOn(sdf.format(cal.getTime()));
		boolean gotTheException = false;
		try
		{
			playerManager.addPlayer(1, PlayerConnection.DEFAULT_PIN);
		} catch (DatabaseException | IllegalQuestChangeException e)
		{
			gotTheException = true;
		}
		assertTrue(gotTheException);
	}

	/**
	 * Sets the players position and checks it
	 * 
	 * @throws DatabaseException
	 *             shouldn'ts
	 */
	@Test
	public void testPlayerPosition() throws DatabaseException
	{
		Player p = playerManager.addPlayer(1);
		Position pos = new Position(3, 3);
		p.setPlayerPosition(pos);
		assertEquals(pos, p.getPlayerPosition());
	}

	/**
	 * Make sure it complains if we give it the wrong PIN
	 * 
	 * @throws DatabaseException
	 *             should
	 * @throws IllegalQuestChangeException the state changed illegally
	 * 
	 */
	@Test(expected = DatabaseException.class)
	public void wrongPin() throws DatabaseException, IllegalQuestChangeException
	{
		playerManager.addPlayer(1, -1);
	}

	/**
	 * Test that we can set Player's experience points and add to it
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testPlayerExpPoints() throws DatabaseException
	{
		Player p = playerManager.addPlayer(1);

		p.setExperiencePoints(34);
		assertEquals(34, p.getExperiencePoints());

		p.addExperiencePoints(3);
		assertEquals(37, p.getExperiencePoints());
	}
	
	/**
	 * Tests that adding experience points to a player object 
	 * generates ExperienceChangedReport
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testAddExpPointsCreatesReport() throws DatabaseException
	{
		Player p = playerManager.addPlayer(1);
		
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				ExperienceChangedReport.class);
		obs.receiveReport(new ExperienceChangedReport(p.getPlayerID(), 30, LevelManager.getSingleton().getLevelForPoints(30)));
		EasyMock.replay(obs);

		p.addExperiencePoints(15);
		EasyMock.verify(obs);
	}
}
