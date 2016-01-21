package datasource;

import java.util.ArrayList;

import datasource.DatabaseException;

/**
 * Requirements for all gateways into the adventure table
 * @author merlin
 *
 */
public interface AdventureTableDataGateway
{

	/**
	 * return all of the adventures for a given quest
	 * @param questID the quest's unique ID
	 * @return null if there aren't any
	 * @throws DatabaseException if we have trouble talking to the data source
	 */
	ArrayList<AdventureRecord> getAdventuresForQuest(int questID) throws DatabaseException;

	/**
	 * Get the information about a specific adventure
	 * @param questID the quest containing the adventure
	 * @param adventureID the adventure's ID within that quest
	 * @return the adventure's information
	 * @throws DatabaseException if we have trouble talking to the data source
	 */
	AdventureRecord getAdventure(int questID, int adventureID) throws DatabaseException;


}
