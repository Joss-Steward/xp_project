package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public GregorianCalendar getExpirationTime() throws DatabaseException
	{
		GregorianCalendar changedOn = null;
		try
		{
			Connection connection = DatabaseManager.getSingleton().getConnection();
			String sql = "SELECT changed_on FROM PlayerPins WHERE PlayerID = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, playerID);
			ResultSet resultSet = stmt.executeQuery();
			changedOn = null;
			if (resultSet.next())
			{
				String timeString = resultSet.getString(1);
				changedOn = parseTimeString(timeString);
				changedOn.add(EXPIRATION_TIME_UNITS, EXPIRATION_TIME_QUANTITY);
			}
			resultSet.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return changedOn;
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
	 * Retrieve the PIN from the database
	 * 
	 * @return the pin we read
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public double retrievePin() throws DatabaseException
	{
		try
		{
			Connection connection = DatabaseManager.getSingleton().getConnection();
			String sql = "SELECT Pin FROM PlayerPins WHERE PlayerID = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, playerID);
			ResultSet resultSet = stmt.executeQuery();
			double pin = 0;
			if (resultSet.next())
			{
				pin = resultSet.getDouble(1);
			} else
			{
				throw new DatabaseException("No pin for player id " + playerID);
			}
			resultSet.close();
			return pin;

		} catch (SQLException e)
		{
			throw new DatabaseException("Error retrieving Pin for user #" + playerID, e);
		}
	}
}
