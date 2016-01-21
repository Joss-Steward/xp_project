package datasource;

import java.util.ArrayList;

import datasource.DatabaseException;
import datasource.PlayerScoreRecord;

/**
 * Defines the behavior required for Player table data gateways
 * @author Merlin
 *
 */
public interface PlayerTableDataGateway
{

	/**
	 * Used for testing to set the data back to a known state
	 */
	public abstract void resetData();

	/**
	 * @return the top ten players as ranked by experience points
	 * @throws DatabaseException  if we can't retrieve the data
	 */
	public abstract ArrayList<PlayerScoreRecord> getTopTenList() throws DatabaseException;

}
