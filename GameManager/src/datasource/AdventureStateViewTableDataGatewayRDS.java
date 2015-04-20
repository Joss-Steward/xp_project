package datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DatabaseManager;
import edu.ship.shipsim.areaserver.datasource.AdventureRecord;
import edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGatewayRDS;

/**
 * A table data gateway that feels like it is a gateway into a view (for now it
 * is just doing the joins on the fly)
 * 
 * @author Merlin
 *
 */
public class AdventureStateViewTableDataGatewayRDS
{

	/**
	 * Get the adventures that are pending for a given player
	 * @param playerID the player's ID
	 * @return the list of pending adventures
	 * @throws DatabaseException if we fail to talk to the DB
	 */
	public static List<AdventureRecord> getPendingAdventureRecords(int playerID) throws DatabaseException
	{
		ArrayList<AdventureRecord> records = new ArrayList<AdventureRecord>();

		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(
					connection,
					"SELECT * FROM Adventures INNER JOIN AdventureStates ON Adventures.QuestID = AdventureStates.QuestID AND Adventures.AdventureID = AdventureStates.AdventureID"
							+ " WHERE adventureState = ? AND playerID = ?");
			stmt.setInt(1, AdventureStateEnum.PENDING.ordinal());
			stmt.setInt(2, playerID);
			ResultSet result = stmt.executeQuery();
			while (result.next())
			{
				records.add(new AdventureRecord(
						result.getInt("AdventureStates.questID"), result
								.getInt("AdventureStates.adventureID"), result
								.getString("Adventures.adventureDescription"), result.getInt("Adventures.experiencePointsGained")));

			}

		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to retrieve pending adventures for player #" + playerID, e);
		}
		return records;
	}

	/**
	 * Change the state of an adventure for a given player to needing notification
	 * @param playerID the player
	 * @param questID the quest containing the adventure
	 * @param adventureID the adventure
	 * @throws DatabaseException if we fail talking to the database
	 */
	public static void moveToNeedNotification(int playerID, int questID, int adventureID) throws DatabaseException
	{
		AdventureStateTableDataGatewayRDS.getSingleton().updateState(playerID, questID, adventureID, AdventureStateEnum.COMPLETED);
		
	}
}
