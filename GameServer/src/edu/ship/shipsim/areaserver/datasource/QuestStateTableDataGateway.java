package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;

import datasource.DatabaseException;

/**
 * Requirements for all implementations of the table gateway into the quest state data source
 * @author Merlin
 *
 */
public interface QuestStateTableDataGateway
{

	/**
	 * Get all of the quest states for a given player
	 * @param playerID the player
	 * @return a list of states
	 * @throws DatabaseException if we can't talk to the data source
	 */
	ArrayList<QuestStateRecord> getQuestStates(int playerID) throws DatabaseException;

}
