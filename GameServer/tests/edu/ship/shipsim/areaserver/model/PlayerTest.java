package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import model.OptionsManager;
import model.PlayerConnection;
import model.QualifiedObservableConnector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Position;
import datasource.DatabaseException;
import datasource.DatabaseTest;
import datasource.PlayersForTest;
import datasource.QuestStateList;
import edu.ship.shipsim.areaserver.datasource.QuestStatesForTest;
import edu.ship.shipsim.areaserver.model.Player;
import edu.ship.shipsim.areaserver.model.PlayerManager;
import edu.ship.shipsim.areaserver.model.reports.PlayerConnectionReport;

/**
 * Test the Player classs
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
		OptionsManager.getSingleton(true);
		QualifiedObservableConnector.resetSingleton();
		PlayerManager.resetSingleton();
		playerManager = PlayerManager.getSingleton();
		assertEquals(0, playerManager.countObservers());
		assertEquals(0, playerManager.countObservers(PlayerConnectionReport.class));
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
	 */
	@Test
	public void legitPin() throws DatabaseException
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
		} catch (DatabaseException e)
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
	 * 
	 */
	@Test(expected = DatabaseException.class)
	public void wrongPin() throws DatabaseException
	{
		playerManager.addPlayer(1, -1);
	}
	
	/**
	 * Test simple functionality of setting quests to a player. 
	 */
	@Test
	public void testPlayerAddQuests()
	{
		Player p = playerManager.addPlayer(1);

		QuestState quest = new QuestState(QuestStatesForTest.PLAYER1_QUEST1.getQuestID(), QuestStatesForTest.PLAYER1_QUEST1.getQuestState());
		p.addQuest(quest);
		
		assertEquals(QuestStateList.HIDDEN, p.getQuestStateByID(1));
		assertEquals(1, p.getSizeOfQuestList());
	}
}