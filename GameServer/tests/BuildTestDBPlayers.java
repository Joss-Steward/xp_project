import java.sql.SQLException;

import model.DatabaseException;
import model.Player;
import model.PlayerLogin;
import model.PlayerManager;
import model.PlayersInDB;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import data.Position;

/**
 * Builds the Player portion of the database
 * 
 * @author Merlin
 * 
 */
public class BuildTestDBPlayers
{

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
		createPlayerTable();
	}

	private static void createPlayerTable() throws SQLException, DatabaseException
	{

		PlayerManager pm = PlayerManager.getSingleton();
		connectionSource = pm.getConnectionSource();
		playerDAO = pm.getPlayerDao();
		TableUtils.dropTable(connectionSource, Player.class, true);
		TableUtils.createTableIfNotExists(connectionSource, Player.class);

		for (PlayersInDB p : PlayersInDB.values())
		{
			Position pos = p.getPosition();
			Player player = new Player();
			PlayerLogin pl = PlayerLogin.readPlayerLogin(p.getPlayerName());
			player.setPlayerLogin(pl);
			player.setPlayerPosition(pos);
			player.setAppearanceType(p.getAppearanceType());
			playerDAO.create(player);
		}

	}
}
