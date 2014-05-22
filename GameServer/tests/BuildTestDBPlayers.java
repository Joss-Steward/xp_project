import java.sql.SQLException;

import model.DatabaseException;
import model.PlayerLogin;
import model.PlayersInDB;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import data.Position;
import edu.ship.shipsim.areaserver.model.CharacterIDGenerator;
import edu.ship.shipsim.areaserver.model.Npc;
import edu.ship.shipsim.areaserver.model.NpcsInDB;
import edu.ship.shipsim.areaserver.model.Player;
import edu.ship.shipsim.areaserver.model.PlayerManager;
import edu.ship.shipsim.areaserver.model.QuizBotBehavior;

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
	private static Dao<Npc, Integer> npcDAO;

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
		createCharacterIDTable();
		createPlayerTable();
		createNpcTable();
	}
	
	private static void createCharacterIDTable() throws SQLException, DatabaseException
	{
		PlayerManager pm = PlayerManager.getSingleton();
		connectionSource = pm.getConnectionSource();
		TableUtils.dropTable(connectionSource, CharacterIDGenerator.class, true);
		TableUtils.createTable(connectionSource, CharacterIDGenerator.class);
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
			player.setId(CharacterIDGenerator.getNextId());
			PlayerLogin.createNewPlayerLogin(p.getPlayerName(), p.getPlayerPassword(), player.getID());
			PlayerLogin pl = PlayerLogin.readPlayerLogin(p.getPlayerName());
			player.setPlayerLogin(pl);
			player.setPlayerPosition(pos);
			player.setAppearanceType(p.getAppearanceType());
			playerDAO.create(player);
		}

	}
	
	private static void createNpcTable() throws SQLException, DatabaseException
	{
		PlayerManager pm = PlayerManager.getSingleton();
		connectionSource = pm.getConnectionSource();
		npcDAO = pm.getNpcDao();
		TableUtils.dropTable(connectionSource, Npc.class, true);
		TableUtils.createTable(connectionSource, Npc.class);
		
		for (NpcsInDB p : NpcsInDB.values())
		{
			Position pos = p.getPosition();
			Npc player = new Npc();
			player.setId(CharacterIDGenerator.getNextId());
			player.setName(p.getPlayerName());
			player.setPlayerPosition(pos);
			player.setAppearanceType(p.getAppearanceType());
			player.setMap(p.getMapName());
			player.setBehavior(new QuizBotBehavior());
			
			npcDAO.create(player);
		}
	}
}
