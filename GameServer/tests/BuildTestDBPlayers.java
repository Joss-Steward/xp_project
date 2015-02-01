
import java.sql.SQLException;

import model.DatabaseException;
import model.OptionsManager;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import datasource.PlayersForTest;
import edu.ship.shipsim.areaserver.datasource.NPCRowDataGatewayRDS;
import edu.ship.shipsim.areaserver.datasource.NPCsForTest;
import edu.ship.shipsim.areaserver.datasource.PlayerRowDataGatewayRDS;
import edu.ship.shipsim.areaserver.model.CharacterIDGenerator;
import edu.ship.shipsim.areaserver.model.PlayerManager;

/**
 * Builds the Player portion of the database
 * 
 * @author Merlin
 * 
 */
public class BuildTestDBPlayers
{

	private static JdbcConnectionSource connectionSource;

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
		OptionsManager.getSingleton(false);
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
		PlayerRowDataGatewayRDS.createTable();

		for (PlayersForTest p : PlayersForTest.values())
		{
			new PlayerRowDataGatewayRDS(p.getMapName(),p.getPosition(), p.getAppearanceType());
		}

	}
	
	private static void createNpcTable() throws SQLException, DatabaseException
	{
		NPCRowDataGatewayRDS.createTable();
		
		for (NPCsForTest p : NPCsForTest.values())
		{
			new NPCRowDataGatewayRDS(p.getPlayerID(),p.getBehaviorClass());
		}
	}
}
