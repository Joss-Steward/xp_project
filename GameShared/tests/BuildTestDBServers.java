import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.DatabaseException;
import model.DatabaseManager;
import model.OptionsManager;
import datasource.ServerRowDataGatewayRDS;
import datasource.ServersForTest;

/**
 * Builds the Player portion of the database
 * 
 * @author Merlin
 * 
 */
public class BuildTestDBServers
{


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
		createServerTable();
	}

	private static void createServerTable() throws SQLException, DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		PreparedStatement stmt = connection.prepareStatement("DELETE From Server");
		stmt.executeUpdate();
		for (ServersForTest p : ServersForTest.values())
		{
			new ServerRowDataGatewayRDS(p.getMapName(),p.getHostName(),p.getPortNumber());
		}

	}
}
