package datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.PlayerScoreRecord;
import datasource.ClosingPreparedStatement;
import datasource.DatabaseException;
import datasource.DatabaseManager;

/**
 * The RDS implementation of the gateway
 * 
 * @author Merlin
 *
 */
public class PlayerTableDataGatewayRDS implements PlayerTableDataGateway
{
	private static PlayerTableDataGateway singleton;

	/**
	 * Retrieves the rds gateway singleton.
	 * 
	 * @return singleton
	 */
	public static synchronized PlayerTableDataGateway getSingleton()
	{
		if (singleton == null)
		{
			singleton = new PlayerTableDataGatewayRDS();
		}
		return singleton;
	}

	/**
	 * @see datasource.QuestStateTableDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		// Nothing required
	}

	/**
	 * @see datasource.PlayerTableDataGateway#getTopTenList()
	 */
	@Override
	public ArrayList<PlayerScoreRecord> getTopTenList() throws DatabaseException
	{
		ArrayList<PlayerScoreRecord> resultList = new ArrayList<PlayerScoreRecord>();
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"SELECT experiencePoints, playerName FROM Players, PlayerLogins where Players.playerID = PlayerLogins.playerID ORDER BY experiencePoints desc limit 10");
			ResultSet result = stmt.executeQuery();

			while (result.next())
			{
				PlayerScoreRecord rec = new PlayerScoreRecord(
						result.getString("playerName"), result.getInt("experiencePoints"));
				resultList.add(rec);
			}
			return resultList;
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find the top ten players", e);
		}
	}

}
