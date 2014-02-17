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
 * The behaviors associated with the PINs that are given to players when the
 * are switching servers
 * @author Merlin
 *
 */
public class PlayerPin
{

	static final int EXPIRATION_TIME_UNITS = Calendar.HOUR;
	static final int EXPIRATION_TIME_QUANTITY = 12;
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
	 * @param newTime the time we want 
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
			stmt.setString(1,newTime);
			stmt.setInt(2, playerID);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			new DatabaseException("Unable to generate pin for player id # " + playerID, e);
		}
	}
	protected void deletePlayerPin() throws SQLException, DatabaseException
	{
		Connection connectionStatus = DatabaseManager.getSingleton().getConnection();

		String sql = "DELETE from PlayerPins WHERE PlayerID = ?";
		PreparedStatement stmt = connectionStatus.prepareStatement(sql);
		stmt.setInt(1, playerID);
		stmt.executeUpdate();
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
		try
		{
			Connection connection = DatabaseManager.getSingleton().getConnection();
			String sql = "SELECT changed_on FROM PlayerPins WHERE PlayerID = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, playerID);
			ResultSet resultSet = stmt.executeQuery();
			GregorianCalendar changedOn = null;
			if (resultSet.next())
			{
				String timeString = resultSet.getString(1);
				changedOn = parseTimeString(timeString);
				changedOn.add(EXPIRATION_TIME_UNITS, EXPIRATION_TIME_QUANTITY);
			} 
			resultSet.close();
			return changedOn;
		
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Generates the default PIN for testing purposes only!
	 * @throws DatabaseException shouldn't
	 */
	public void generateTestPin() throws DatabaseException
	{
		this.setPin(DEFAULT_PIN);
	}
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
			} 
			resultSet.close();
			return pin;
		
		} catch (SQLException e)
		{
			throw new DatabaseException("Error retrieving Pin for user #" + playerID,e);
		}
	}
}
