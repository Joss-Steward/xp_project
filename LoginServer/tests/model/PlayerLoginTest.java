package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

/**
 * Tests the functionality required during a login
 * 
 * @author Merlin
 * 
 */
public class PlayerLoginTest extends DatabaseTest
{
	/**
	 * The players that are in the test database
	 * 
	 * @author Merlin
	 * 
	 */
	public enum Players
	{
		/**
		 * 
		 */
		JOHN("John", "pw"),
		/**
		 * 
		 */
		MERLIN("Merlin", "mypwd");

		private String name;
		private String password;

		Players(String n, String pwd)
		{
			this.name = n;
			this.password = pwd;
		}

		/**
		 * Get the name of the player
		 * 
		 * @return the player's name
		 */
		public String getName()
		{
			return name;
		}

		/**
		 * Get the player's password
		 * 
		 * @return the password
		 */
		public String getPassword()
		{
			return password;
		}
	}

	/**
	 * If the name and password are in the db, we should be able to determine
	 * the player's unique ID
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void simpleRead() throws DatabaseException
	{
		PlayerLogin pl = new PlayerLogin(Players.JOHN.getName(),
				Players.JOHN.getPassword());
		assertEquals(1, pl.getPlayerID());
	}

	/**
	 * If the player's name isn't in the db, we should throw an exception
	 * 
	 * @throws DatabaseException
	 *             should
	 */
	@Test(expected = DatabaseException.class)
	public void notThere() throws DatabaseException
	{
		new PlayerLogin(Players.JOHN.getName() + "z", Players.JOHN.getPassword());
	}

	/**
	 * If the password given doesn't match the db, we should throw an exception
	 * 
	 * @throws DatabaseException
	 *             should
	 */
	@Test(expected = DatabaseException.class)
	public void wrongPassword() throws DatabaseException
	{
		new PlayerLogin(Players.JOHN.getName(), Players.JOHN.getPassword() + 'Z');
	}

	/**
	 * When we generate a PIN for a player, it should be stored into the db
	 * @throws DatabaseException shouldn't
	 * @throws SQLException shouldn't
	 */
	@Test
	public void generatesAndStoresPin() throws DatabaseException, SQLException
	{
		PlayerLogin pl = new PlayerLogin(Players.MERLIN.getName(),
				Players.MERLIN.getPassword());
		double pin = pl.generatePin();
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
	 * @throws DatabaseException shouldn't
	 * @throws SQLException shouldn't
	 */
	@Test
	public void cantHaveTwoPins() throws DatabaseException, SQLException
	{
		PlayerLogin pl = new PlayerLogin(Players.MERLIN.getName(),
				Players.MERLIN.getPassword());
		pl.generatePin();
		double pin2 = pl.generatePin();
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
}
