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
public class PlayerConnection
{

	/**
	 * The units in which we measure expiration time of a pin
	 */
	public static final int EXPIRATION_TIME_UNITS = Calendar.HOUR;
	
	/**
	 * The number of expiration time units before a pin should expire
	 */
	public static final int EXPIRATION_TIME_QUANTITY = 12;
	/**
	 * An error message
	 */
	public static final String ERROR_PIN_NOT_EXIST = "Pin does not exist";
	/**
	 * An error message
	 */
	public static final String ERROR_PIN_EXPIRED = "Pin has expired";

	/**
	 * Used as the default pin in testing
	 */
	public static final int DEFAULT_PIN = 1;
	private int playerID;

	/**
	 * @param playerID
	 *            the player whose information we are using
	 */
	public PlayerConnection(int playerID)
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
	public int generatePin() throws DatabaseException
	{
		int pin = (int) (Math.random() * Integer.MAX_VALUE);
		setPin(pin);
		return pin;
	}

	private void setPin(int pin) throws DatabaseException
	{
		Connection connectionStatus = DatabaseManager.getSingleton()
				.getConnection();
		try
		{
			String sql;
			PreparedStatement stmt;
			deletePlayerPin();

			sql = "INSERT INTO PlayerConnection (PlayerID, Pin) VALUES (?, ?)";
			System.err.println("[DEBUG] " + sql + " " + playerID + " " + pin);
			stmt = connectionStatus.prepareStatement(sql);
			stmt.setInt(1, playerID);
			stmt.setInt(2, pin);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			new DatabaseException("Unable to generate pin for player id # "
					+ playerID, e);
		}
	}

	/**
	 * Used only for testing!!!!! Used to set the timestamp on the player's PIN
	 * 
	 * @param newTime
	 *            the time we want
	 * @throws DatabaseException if we cant set the time whan a player's connection info was changes
	 */
	public void setChangedOn(String newTime) throws DatabaseException
	{
		Connection connectionStatus = DatabaseManager.getSingleton()
				.getConnection();
		try
		{
			String sql;
			PreparedStatement stmt;

			sql = "UPDATE PlayerConnection SET changed_On=? WHERE PlayerID = ?";
			stmt = connectionStatus.prepareStatement(sql);
			stmt.setString(1, newTime);
			stmt.setInt(2, playerID);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Unable to generate pin for player id # " + playerID, e);
		}
	}

	protected void deletePlayerPin() throws DatabaseException
	{
		Connection connectionStatus = DatabaseManager.getSingleton()
				.getConnection();

		String sql = "DELETE from PlayerConnection WHERE PlayerID = ?";
		PreparedStatement stmt;
		try
		{
			stmt = connectionStatus.prepareStatement(sql);
			stmt.setInt(1, playerID);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Unable to delete the pin for player id # " + playerID, e);
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
			Connection connection = DatabaseManager.getSingleton()
					.getConnection();
			String sql = "SELECT changed_on FROM PlayerConnection WHERE PlayerID = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, playerID);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next())
			{
				String timeString = resultSet.getString(1);
				expirationTime = parseTimeString(timeString);
				expirationTime.add(EXPIRATION_TIME_UNITS,
						EXPIRATION_TIME_QUANTITY);
				if (expirationTime.after(now))
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
	 * @param pin
	 *            The pin to check against
	 * @return true or false for whether the given pin is valid or not
	 */
	public boolean isPinValid(double pin)
	{
		boolean found = false;
		try
		{
			Connection connection = DatabaseManager.getSingleton()
					.getConnection();
			String sql = "SELECT * FROM PlayerConnection WHERE PlayerID = ? AND Pin = ?";
			System.err.println("[DEBUG] " + sql + ": " + playerID + " " + pin);
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

	/**
	 * Get the name of the map the player was most recently on
	 * 
	 * @return the name of the tmx file
	 */
	public String getMapName()
	{
		String mapName = null;
		try
		{
			Connection connection = DatabaseManager.getSingleton()
					.getConnection();
			String sql = "SELECT MapName FROM PlayerConnection WHERE PlayerID = ?";
			System.err.println("[DEBUG] " + sql + ": " + playerID);
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, playerID);
			ResultSet resultSet = stmt.executeQuery();
			resultSet.first();
			mapName = resultSet.getString(1);

			resultSet.close();
			stmt.close();
		} catch (SQLException | DatabaseException e)
		{
			e.printStackTrace();
		}

		return mapName;
	}

	/**
	 * Store the map that the player is using
	 * 
	 * @param mapFileTitle
	 *            the title of the tmx file
	 * @throws DatabaseException
	 *             if we cannot update their state in the database
	 */
	public void setMapName(String mapFileTitle) throws DatabaseException
	{
		try
		{
			Connection connectionStatus = DatabaseManager.getSingleton()
					.getConnection();

			String sql;
			PreparedStatement stmt;

			sql = "UPDATE PlayerConnection SET mapName=? WHERE PlayerID = ?";
			stmt = connectionStatus.prepareStatement(sql);
			stmt.setString(1, mapFileTitle);
			stmt.setInt(2, playerID);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Unable to store map information for player id # "
							+ playerID, e);
		}
	}
}
