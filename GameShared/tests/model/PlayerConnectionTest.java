package model;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * Tests the functionality associated with the PINs that the login server gives
 * a client when they are connecting to an area server
 * 
 * @author Merlin
 * 
 */
public class PlayerConnectionTest extends DatabaseTest
{

	/**
	 * Generate default pins for all users
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public static void defaultAllPins() throws DatabaseException
	{
		for (int userID = 1; userID < 3; userID++)
		{
			PlayerConnection pp = new PlayerConnection(userID);
			pp.generateTestPin();
		}
	}

	/**
	 * Can store a new map file name
	 * @throws DatabaseException shouldn't
	 * @throws SQLException shouldn't
	 */
	@Test
	public void storesMapName() throws DatabaseException, SQLException
	{
		PlayerConnection pc = new PlayerConnection(2);
		pc.setMapName("thisMap.tmx");
		Connection connection = DatabaseManager.getSingleton().getConnection();
		String sql = "SELECT MapName FROM PlayerConnection WHERE PlayerID = ?";
		System.err.println("[DEBUG] " + sql + ": " + 2);
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, 2);
		ResultSet resultSet = stmt.executeQuery();
		resultSet.first();
		String actual  = resultSet.getString(1);
		resultSet.close();
		stmt.close();
		assertEquals( "thisMap.tmx", actual);
		assertEquals( actual, pc.getMapName());
	}
	/**
	 * When we generate a PIN for a player, it should be stored into the db
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws SQLException
	 *             shouldn't
	 */
	@Test
	public void generatesAndStoresPin() throws DatabaseException, SQLException
	{
		PlayerConnection playerPin = new PlayerConnection(2);
		double pin = playerPin.generatePin();
		Connection connection = DatabaseManager.getSingleton().getConnection();
		String sql = "SELECT pin FROM PlayerConnection WHERE PlayerID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, 2);
		ResultSet resultSet = stmt.executeQuery();
		resultSet.first();
		double storedPin = resultSet.getDouble(1);
		resultSet.close();
		stmt.close();
		assertEquals(pin, storedPin, 0.00001);
	}

	/**
	 * If we generate a PIN twice, there should only be one entry in the db and
	 * it should be the most recent one we generated
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws SQLException
	 *             shouldn't
	 */
	@Test
	public void cantHaveTwoPins() throws DatabaseException, SQLException
	{
		PlayerConnection playerPin = new PlayerConnection(2);
		playerPin.generatePin();
		double pin2 = playerPin.generatePin();
		String sql = "SELECT pin FROM PlayerConnection WHERE PlayerID = ?";
		Connection connection = DatabaseManager.getSingleton().getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, 2);
		ResultSet resultSet = stmt.executeQuery();
		resultSet.first();
		double storedPin = resultSet.getDouble(1);
		assertFalse(resultSet.next());
		resultSet.close();
		assertEquals(pin2, storedPin, 0.00001);
	}

	/**
	 * Make sure we convert a time string from mySQL to a GregorianCalendar
	 * Correctly
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void canParseTime() throws DatabaseException
	{
		PlayerConnection playerPin = new PlayerConnection(2);
		GregorianCalendar cal = playerPin.parseTimeString("2013-10-07 13:24:23");
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
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws SQLException
	 *             shouldn't
	 */
	@Test
	public void testIsExpiredWithoutAPin() throws DatabaseException, SQLException
	{
		PlayerConnection playerPin = new PlayerConnection(2);
		playerPin.generatePin();
		playerPin.deletePlayerPin();
		assertTrue(playerPin.isExpired());
	}

	/**
	 * Make sure the expiration time for the pin is in the future (rounding
	 * errors make it difficult to make this test more precise
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testNewPinIsNotExpired() throws DatabaseException
	{
		PlayerConnection playerPin = new PlayerConnection(2);
		playerPin.generatePin();
		assertFalse(playerPin.isExpired());
	}

	/**
	 * Make sure that if we set the pin, we can retrieve it
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void isPinValid() throws DatabaseException
	{
		PlayerConnection playerPin = new PlayerConnection(2);
		double pin = playerPin.generatePin();
		assertTrue(playerPin.isPinValid(pin));
		assertFalse(playerPin.isPinValid(pin+1));
	}
}
