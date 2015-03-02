package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;

import datasource.DatabaseException;

/**
 * Defines the operations required by a gateway into the states of adventures for each player
 * @author Merlin
 *
 */
public interface AdventureStateTableDataGateway
{

	/**
	 * Get a player's states of all of the adventures for a given quest
	 * @param playerID the player
	 * @param questID the quest
	 * @return all of the adventure states
	 * @throws DatabaseException  if we can't get the info from the data source
	 */
	ArrayList<AdventureStateRecord> getAdventureStates(int playerID, int questID) throws DatabaseException;

}
