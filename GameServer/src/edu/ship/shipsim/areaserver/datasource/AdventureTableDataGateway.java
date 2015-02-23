package edu.ship.shipsim.areaserver.datasource;

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


}
