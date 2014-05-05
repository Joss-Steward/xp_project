package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * The behaviors associated with the PINs that are given to players when the are
 * switching servers
 * 
 * @author Merlin
 * 
 */
public class PlayerPin
{

	static final int EXPIRATION_TIME_UNITS = Calendar.HOUR;
	static final int EXPIRATION_TIME_QUANTITY = 12;
	static final String ERROR_PIN_NOT_EXIST = "Pin does not exist";
	static final String ERROR_PIN_EXPIRED = "Pin has expired";
	
	/**
	 * Used as the default pin in testing
	 */
	public static final double DEFAULT_PIN = 0.5;
	private int playerID;

	PlayerPin(int playerID)
	{
		this.playerID = playerID;
	}

	/**
	 * Generate the PIN this player should use for logging into his area server
	 * and put it in the DB
	 * 
	 * @return the PIN
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public double generatePin() throws DatabaseException
	{
		double pin = Math.random();
		setPin(pin);
		return pin;
	}

	private void setPin(double pin) throws DatabaseException
	{
		Connection connectionStatus = DatabaseManager.getSingleton().getConnection();
		try
		{
			String sql;
			PreparedStatement stmt;
			deletePlayerPin();

			sql = "INSERT INTO PlayerPins (PlayerID, Pin) VALUES (?, ?)";
			stmt = connectionStatus.prepareStatement(sql);
			stmt.setInt(1, playerID);
			stmt.setDouble(2, pin);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			new DatabaseException("Unable to generate pin for player id # " + playerID, e);
		}
	}

	/**
	 * Used only for testing!!!!! Used to set the timestamp on the player's PIN
	 * 
	 * @param newTime
	 *            the time we want
	 * @throws DatabaseException
	 */
	protected void setChangedOn(String newTime) throws DatabaseException
	{
		Connection connectionStatus = DatabaseManager.getSingleton().getConnection();
		try
		{
			String sql;
			PreparedStatement stmt;

			sql = "UPDATE PlayerPins SET changed_On=? WHERE PlayerID = ?";
			stmt = connectionStatus.prepareStatement(sql);
			stmt.setString(1, newTime);
			stmt.setInt(2, playerID);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			new DatabaseException("Unable to generate pin for player id # " + playerID, e);
		}
	}

	protected void deletePlayerPin() throws DatabaseException
	{
		Connection connectionStatus = DatabaseManager.getSingleton().getConnection();

		String sql = "DELETE from PlayerPins WHERE PlayerID = ?";
		PreparedStatement stmt;
		try
		{
			stmt = connectionStatus.prepareStatement(sql);
			stmt.setInt(1, playerID);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to delete the pin for player id # "
					+ playerID, e);
		}

	}

	/**
	 * Get the time when this player's pin expires in GMT
	 * 
	 * @return the expiration time
	 */
	public boolean isExpired()
	{
		GregorianCalendar now = new GregorianCalendar();
		now.setTimeZone(TimeZone.getTimeZone("GMT"));
		GregorianCalendar expirationTime = null;
		boolean expired = true;
		try
		{
			Connection connection = DatabaseManager.getSingleton().getConnection();
			String sql = "SELECT changed_on FROM PlayerPins WHERE PlayerID = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, playerID);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next())
			{
				String timeString = resultSet.getString(1);
				expirationTime = parseTimeString(timeString);
				expirationTime.add(EXPIRATION_TIME_UNITS, EXPIRATION_TIME_QUANTITY);
				if(expirationTime.after(now))
				{
					expired = false;
				}
			}
			
			resultSet.close();
		} catch (SQLException | DatabaseException e)
		{
			e.printStackTrace();
		}
		
		return expired;
	}

	protected GregorianCalendar parseTimeString(String timeString)
	{
		GregorianCalendar result = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			result.setTime(sdf.parse(timeString));
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Generates the default PIN for testing purposes only!
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public void generateTestPin() throws DatabaseException
	{
		this.setPin(DEFAULT_PIN);
	}

	/**
	 * check if a pin is valid for a given player
	 * 
	 * @param pin The pin to check against
	 * @return true or false for whether the given pin is valid or not
	 */
	public boolean isPinValid(double pin)
	{
		boolean found = false;
		try
		{
			Connection connection = DatabaseManager.getSingleton().getConnection();
			String sql = "SELECT * FROM PlayerPins WHERE PlayerID = ? AND Pin = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, playerID);
			stmt.setDouble(2, pin);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next())
			{
				found = true;
			}
			resultSet.close();
		} catch (SQLException | DatabaseException e)
		{
			e.printStackTrace();
		}
		
		return found;
	}
}
