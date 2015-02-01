import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import datasource.DatabaseException;
import datasource.PlayersForTest;
import model.DatabaseManager;

/**
 * Builds the login portion of the database
 * 
 * @author Merlin
 * 
 */
public class BuildTestDBPlayerLogin
{

	private static Connection connection;

	/**
	 * 
	 * @param args
	 *            unused
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws SQLException
	 *             shouldn't
	 */
	public static void main(String[] args) throws DatabaseException, SQLException
	{
		connection = DatabaseManager.getSingleton().getConnection();
		createPlayerTable();
		createPinTable();
	}

	private static void createPinTable() throws SQLException
	{
		Statement stmt = connection.createStatement();

		stmt.executeUpdate("DROP TABLE PlayerConnection");
		StringBuffer sql = new StringBuffer("CREATE TABLE PlayerConnection(");
		sql.append("PlayerID int NOT NULL, ");
		sql.append("Pin double NOT NULL,");
		sql.append("changed_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,");
		sql.append("MapName VARCHAR(30),");

		sql.append("PRIMARY KEY (PlayerID));");
		System.out.println(sql);
		stmt.executeUpdate(new String(sql));
		stmt.executeUpdate("ALTER TABLE PlayerConnection ENGINE = INNODB");
		stmt.executeUpdate("ALTER TABLE PlayerConnection ADD UNIQUE (PlayerID)");
		
		for (PlayersForTest p : PlayersForTest.values())
		{
			String sqlUpdateString = "INSERT INTO PlayerConnection (PlayerID, Pin, MapName) VALUES ('"
					+ p.getPlayerID() + "', '" + 111111111 + "','" + p.getMapName() + "');";
			stmt.executeUpdate(sqlUpdateString);
		}
		stmt.close();
	}

	private static void createPlayerTable() throws SQLException
	{
		Statement stmt = connection.createStatement();

		stmt.executeUpdate("DROP TABLE PlayerLogins");
		StringBuffer sql = new StringBuffer("CREATE TABLE PlayerLogins(");
		sql.append("playerID int NOT NULL AUTO_INCREMENT, ");
		sql.append("playerName VARCHAR(30) NOT NULL,");
		sql.append("password VARCHAR(30) NOT NULL,");

		sql.append("PRIMARY KEY (PlayerID));");
		System.out.println(sql);
		stmt.executeUpdate(new String(sql));
		stmt.executeUpdate("ALTER TABLE PlayerLogins ENGINE = INNODB");
		stmt.executeUpdate("ALTER TABLE PlayerLogins ADD UNIQUE (PlayerName)");
		stmt.executeUpdate("ALTER TABLE PlayerLogins ADD UNIQUE (PlayerID)");
		
		for (PlayersForTest p : PlayersForTest.values())
		{
			stmt.executeUpdate("INSERT INTO PlayerLogins (PlayerName, Password) VALUES ('"
					+ p.getPlayerName() + "', '" + p.getPlayerPassword() + "');");
		}

	}
}
