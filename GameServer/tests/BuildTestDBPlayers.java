import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import data.Position;
import model.DatabaseException;
import model.DatabaseManager;
import model.PlayersInDB;

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
			sql.append("PlayerID int NOT NULL, \n");
			sql.append("AppearanceType VARCHAR(30) NOT NULL,\n");
			sql.append("Row int, \n");
			sql.append("Col int, \n");
			sql.append("PRIMARY KEY (PlayerID),\n");
			sql.append("FOREIGN KEY (PlayerId) REFERENCES PlayerLogins(PlayerId));");
			System.out.println(sql);
			stmt.executeUpdate(new String(sql));
			stmt.executeUpdate("ALTER TABLE PlayerLogins ENGINE = INNODB");
			
			for (PlayersInDB p : PlayersInDB.values())
			{
				Position pos = p.getPosition();
				int row = pos.getRow();
				int column = pos.getColumn();
				stmt.executeUpdate("INSERT INTO Player (PlayerID, AppearanceType, row, col ) VALUES ('" + p.getPlayerID() + "', '" + p.getAppearanceType() +
						"', " + row + "," + column + ");");
			}

		}
}
