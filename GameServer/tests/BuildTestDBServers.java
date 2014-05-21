import java.sql.SQLException;

import model.DatabaseException;
import model.MapToServerMapping;
import model.ServersInDB;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import edu.ship.shipsim.areaserver.model.PlayerManager;

/**
 * Builds the Player portion of the database
 * 
 * @author Merlin
 * 
 */
public class BuildTestDBServers
{

	private static JdbcConnectionSource connectionSource;
	private static Dao<MapToServerMapping, String> serverDAO;

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
		createServerTable();
	}

	private static void createServerTable() throws SQLException, DatabaseException
	{

		PlayerManager pm = PlayerManager.getSingleton();
		connectionSource = pm.getConnectionSource();
		serverDAO = MapToServerMapping.getServerDao();
		TableUtils.dropTable(connectionSource, MapToServerMapping.class, true);
		TableUtils.createTableIfNotExists(connectionSource, MapToServerMapping.class);

		for (ServersInDB p : ServersInDB.values())
		{
			MapToServerMapping mapping = new MapToServerMapping();
			mapping.setMapName(p.getMapName());
			mapping.setHostName(p.getHostName());
			mapping.setPortNumber(p.getPortNumber());
			
			serverDAO.create(mapping);
		}

	}
}
