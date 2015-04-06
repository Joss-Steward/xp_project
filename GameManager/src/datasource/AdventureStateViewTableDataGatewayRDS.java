package datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DatabaseManager;
import edu.ship.shipsim.areaserver.datasource.AdventureRecord;
import edu.ship.shipsim.areaserver.datasource.NPCRowDataGateway;
import edu.ship.shipsim.areaserver.datasource.NPCRowDataGatewayRDS;

/**
 * A table data gateway that feels like it is a gateway into a view (for now it
 * is just doing the joins on the fly)
 * 
 * @author Merlin
 *
 */
public class AdventureStateViewTableDataGatewayRDS
{

	private int playerID;

	public AdventureStateViewTableDataGatewayRDS(int playerID)
	{
		this.playerID = playerID;
	}

	public List<AdventureRecord> getPendingAdventureRecords() throws DatabaseException
	{
		ArrayList<AdventureRecord> records = new ArrayList<AdventureRecord>();

		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(
					connection,
					"SELECT * FROM Adventures INNER JOIN AdventureStates ON Adventures.QuestID = AdventureStates.QuestID AND Adventures.AdventureID = AdventureStates.AdventureID"
							+ " WHERE adventureState = ?");
			stmt.setInt(1, AdventureStateEnum.PENDING.ordinal());
			ResultSet result = stmt.executeQuery();
			while (result.next())
			{
				records.add(new AdventureRecord(
						result.getInt("AdventureStates.questID"), result
								.getInt("AdventureStates.adventureID"), result
								.getString("Adventures.adventureDescription")));

			}

		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to retrieve pending adventures for player #" + playerID, e);
		}
		return records;
	}
}
