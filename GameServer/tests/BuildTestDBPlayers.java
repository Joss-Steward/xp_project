import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import model.DatabaseException;
import model.DatabaseManager;
import model.PlayerTest;

/**
 * Builds the Player portion of the database
 * @author Merlin
 *
 */
public class BuildTestDBPlayers
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

			stmt.executeUpdate("DROP TABLE Player");
			StringBuffer sql = new StringBuffer("CREATE TABLE Player(");
			sql.append("PlayerID int NOT NULL, ");
			sql.append("PlayerType VARCHAR(30) NOT NULL,");
			sql.append("PRIMARY KEY (PlayerID),");
			sql.append("FOREIGN KEY (PlayerId) REFERENCES PlayerLogins(PlayerId));");
			System.out.println(sql);
			stmt.executeUpdate(new String(sql));
			stmt.executeUpdate("ALTER TABLE PlayerLogins ENGINE = INNODB");
			
			for (PlayerTest.Players p : PlayerTest.Players.values())
			{
				stmt.executeUpdate("INSERT INTO Player (PlayerID, PlayerType) VALUES ('" + p.getPlayerID() + "', '" + p.getPlayerType() + "');");
			}

		}
}
