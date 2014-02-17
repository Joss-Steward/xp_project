package model;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Test;

/**
 * Tests the functionality associated with the PINs that the login server gives
 * a client when they are connecting to an area server
 * 
 * @author Merlin
 * 
 */
public class PlayerPinTest extends DatabaseTest
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
			PlayerPin pp = new PlayerPin(userID);
			pp.generateTestPin();
		}
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
		PlayerPin playerPin = new PlayerPin(2);
		double pin = playerPin.generatePin();
		Connection connection = DatabaseManager.getSingleton().getConnection();
		String sql = "SELECT pin FROM PlayerPins WHERE PlayerID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, 2);
		ResultSet resultSet = stmt.executeQuery();
		resultSet.first();
		double storedPin = resultSet.getDouble(1);
		resultSet.close();
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
		PlayerPin playerPin = new PlayerPin(2);
		playerPin.generatePin();
		double pin2 = playerPin.generatePin();
		String sql = "SELECT pin FROM PlayerPins WHERE PlayerID = ?";
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
		PlayerPin playerPin = new PlayerPin(2);
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
	public void testDeleteAndHavingNoSuchPin() throws DatabaseException, SQLException
	{
		PlayerPin playerPin = new PlayerPin(2);
		playerPin.generatePin();
		playerPin.deletePlayerPin();
		assertNull(playerPin.getExpirationTime());
	}

	/**
	 * Make sure the expiration time for the pin is in the future (rounding
	 * errors make it difficult to make this test more precise
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void expirationDateIsForward() throws DatabaseException
	{
		PlayerPin playerPin = new PlayerPin(2);
		playerPin.generatePin();
		GregorianCalendar now = new GregorianCalendar();
		now.setTimeZone(TimeZone.getTimeZone("GMT"));
		GregorianCalendar expTime = playerPin.getExpirationTime();
		assertTrue(expTime.after(now));
	}

	/**
	 * Make sure we can play with the time stamp. This shouldn't be done
	 * normally, but tests require it.
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void canSetExpirationTime() throws DatabaseException
	{
		PlayerPin playerPin = new PlayerPin(2);
		playerPin.setChangedOn("2013-12-25 12:13:14");
		GregorianCalendar expected = playerPin.parseTimeString("2013-12-25 12:13:14");
		expected.add(PlayerPin.EXPIRATION_TIME_UNITS, PlayerPin.EXPIRATION_TIME_QUANTITY);
		GregorianCalendar actual = playerPin.getExpirationTime();
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.DAY_OF_MONTH),
				actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
		assertEquals(expected.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
		assertEquals(expected.get(Calendar.SECOND), actual.get(Calendar.SECOND));

	}

}
