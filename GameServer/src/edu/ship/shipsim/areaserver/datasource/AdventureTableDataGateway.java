package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;

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
	 */
	ArrayList<AdventureRecord> getAdventuresForQuest(int questID);


}
