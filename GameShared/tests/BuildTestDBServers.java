import java.sql.Connection;
import java.sql.SQLException;

import testData.ServersForTest;
import model.OptionsManager;
import datasource.DatabaseException;
import datasource.DatabaseManager;
import datasource.ServerRowDataGatewayRDS;
import datasource.ClosingPreparedStatement;

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
	 * @param args unused
	 * @throws DatabaseException shouldn't
	 * @throws SQLException shouldn't
	 */
	public static void main(String[] args) throws DatabaseException, SQLException
	{
		OptionsManager.getSingleton().setTestMode(false);
		createServerTable();
	}

	private static void createServerTable() throws SQLException, DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection, "DELETE From Server");
		stmt.executeUpdate();
		for (ServersForTest p : ServersForTest.values())
		{
			new ServerRowDataGatewayRDS(p.getMapName(), p.getHostName(), p.getPortNumber());
		}

	}
}
