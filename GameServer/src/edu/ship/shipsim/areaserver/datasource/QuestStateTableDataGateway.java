package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;

import datasource.DatabaseException;
import datasource.QuestStateList;

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

	/**
	 * Create the table from the data in QuestStatesForTest
	 * @throws DatabaseException if we can't talk to the data source
	 */
	void createTable() throws DatabaseException;

	/**
	 * Put a new row in the table
	 * @param playerID the player's ID
	 * @param questID the quest ID
	 * @param state the player's state for that quest
	 * @throws DatabaseException if we can't talk to the data source
	 */
	void createRow(int playerID, int questID, QuestStateList state) throws DatabaseException;

}
