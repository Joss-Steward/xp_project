package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datasource.DatabaseTest;

/**
 * Tests the functionality associated with the PINs that the login server gives
 * a client when they are connecting to an area server
 * 
 * @author Merlin
 * 
 */
public class PlayerConnectionTest extends DatabaseTest
{

	private static PlayerConnection pc;

	/**
	 * make sure we are testing
	 */
	@Before
	public void setup2()
	{
		OptionsManager.getSingleton().setTestMode(true);
	}

	/**
	 * set the data back
	 */
	@After
	public void cleanup()
	{
		pc.resetData();
	}

	/**
	 * Generate default pins for all users
	 * 
	 * @throws DatabaseException shouldn't
	 */
	public static void defaultAllPins() throws DatabaseException
	{
		OptionsManager.getSingleton().setTestMode(true);
		for (int userID = 1; userID < 3; userID++)
		{
			pc = new PlayerConnection(userID);
			pc.generateTestPin();
		}
	}

	/**
	 * Can store a new map file name
	 * 
	 * @throws DatabaseException shouldn't
	 * @throws SQLException shouldn't
	 */
	@Test
	public void storesMapName() throws DatabaseException, SQLException
	{
		pc = new PlayerConnection(2);
		pc.setMapName("thisMap.tmx");
		PlayerConnection after = new PlayerConnection(2);
		assertEquals("thisMap.tmx", after.getMapName());
	}

	/**
	 * When we generate a PIN for a player, it should be stored into the db
	 * 
	 * @throws DatabaseException shouldn't
	 * @throws SQLException shouldn't
	 */
	@Test
	public void generatesAndStoresPin() throws DatabaseException, SQLException
	{
		pc = new PlayerConnection(2);
		double pin = pc.generatePin();
		PlayerConnection after = new PlayerConnection(2);
		assertEquals(pin, after.getPin(), 0.00001);
	}

	/**
	 * Make sure we convert a time string from mySQL to a GregorianCalendar
	 * Correctly
	 * 
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void canParseTime() throws DatabaseException
	{
		pc = new PlayerConnection(2);
		GregorianCalendar cal = pc.parseTimeString("2013-10-07 13:24:23");
		assertEquals(2013, cal.get(Calendar.YEAR));
		assertEquals(9, cal.get(Calendar.MONTH));
		assertEquals(7, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(13, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(24, cal.get(Calendar.MINUTE));
		assertEquals(23, cal.get(Calendar.SECOND));

	}

	/**
	 * Make sure that when we delete someone's PIN, they can't get an expiration
	 * time
	 * 
	 * @throws DatabaseException shouldn't
	 * @throws SQLException shouldn't
	 */
	@Test
	public void testIsExpiredWithoutAPin() throws DatabaseException, SQLException
	{
		pc = new PlayerConnection(7);
		pc.generatePin();
		pc.deletePlayerPin();
		assertTrue(pc.isExpired());
	}

	/**
	 * Make sure the expiration time for the pin is in the future (rounding
	 * errors make it difficult to make this test more precise
	 * 
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testNewPinIsNotExpired() throws DatabaseException
	{
		pc = new PlayerConnection(7);
		pc.generatePin();
		assertFalse(pc.isExpired());
	}

	/**
	 * Make sure that if we set the pin, we can retrieve it
	 * 
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void isPinValid() throws DatabaseException
	{
		pc = new PlayerConnection(7);
		double pin = pc.generatePin();
		assertTrue(pc.isPinValid(pin));
		assertFalse(pc.isPinValid(pin + 1));
	}
}
