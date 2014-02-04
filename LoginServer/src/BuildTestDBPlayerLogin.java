import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import model.DatabaseException;
import model.DatabaseManager;
import model.PlayerLoginTest;



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
		}

		private static void createPlayerTable() throws SQLException
		{
			Statement stmt = connection.createStatement();

			stmt.executeUpdate("DROP TABLE PlayerLogins");
			StringBuffer sql = new StringBuffer("CREATE TABLE PlayerLogins(");
			sql.append("PlayerID       int NOT NULL AUTO_INCREMENT, ");
			sql.append("PlayerName VARCHAR(30) NOT NULL,");
			sql.append("Password VARCHAR(30) NOT NULL,");

			sql.append("PRIMARY KEY (PlayerID));");
			System.out.println(sql);
			stmt.executeUpdate(new String(sql));
			stmt.executeUpdate("ALTER TABLE PlayerLogins ENGINE = INNODB");
			stmt.executeUpdate("ALTER TABLE PlayerLogins ADD UNIQUE (PlayerName)");

			for (PlayerLoginTest.Players p : PlayerLoginTest.Players.values())
			{
				stmt.executeUpdate("INSERT INTO PlayerLogins (PlayerName, Password) VALUES ('" + p.getName() + "', '" + p.getPassword() + "');");
			}

		}
}
