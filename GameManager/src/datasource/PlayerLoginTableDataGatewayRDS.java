package datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DatabaseManager;
import model.PlayerID;

/**
 * a table gateway into the player login table
 * @author Merlin
 *
 */
public class PlayerLoginTableDataGatewayRDS
{
	/**
	 * @return a list of all of the names of the players in the database
	 * @throws DatabaseException
	 *             if we fail at retrieval
	 */
	public static List<PlayerID> getPlayerIDList() throws DatabaseException
	{
		List<PlayerID> list = new ArrayList<PlayerID>();
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"SELECT playerName, playerID FROM PlayerLogins");
			ResultSet result = stmt.executeQuery();
			while (result.next())
			{
				list.add(new PlayerID(result.getInt("playerID"), result
						.getString("playerName")));
			}
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't retrieve all of the players", e);
		}
		return list;
	}

}
