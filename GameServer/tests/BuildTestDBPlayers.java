import java.sql.Connection;
import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import data.Position;
import model.DatabaseException;
import model.DatabaseManager;
import model.Player;
import model.PlayerManager;
import model.PlayersInDB;

/**
 * Builds the Player portion of the database
 * @author Merlin
 *
 */
public class BuildTestDBPlayers
{

		private static Connection connection;
		private static JdbcConnectionSource connectionSource;
		private static Dao<Player, Integer> playerDAO;


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
			
			PlayerManager pm = PlayerManager.getSingleton();
			connectionSource = pm.getConnectionSource();
			playerDAO = pm.getPlayerDao();
			TableUtils.createTableIfNotExists(connectionSource, Player.class);
			
			for (PlayersInDB p : PlayersInDB.values())
			{
				Position pos = p.getPosition();
				Player player = new Player();
				player.setPlayerPosition(pos);
				player.setPlayerName(p.getPlayerName());
				player.setAppearanceType(p.getAppearanceType());
				player.setPlayerID(p.getPlayerID());
				playerDAO.create(player);
			}

		}
}
