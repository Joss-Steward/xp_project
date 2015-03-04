package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;

import datasource.DatabaseException;
import datasource.QuestStateEnum;

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
	void createRow(int playerID, int questID, QuestStateEnum state) throws DatabaseException;

	/**
	 * Change the state of a given quest for a given player
	 * @param playerID the player
	 * @param questID the ID of the quest
	 * @param newState the new state of the quest for this player
	 * @throws DatabaseException if the data source cannot make the change
	 */
	void udpateState(int playerID, int questID, QuestStateEnum newState) throws DatabaseException;

	/**
	 * Put data back to its original state - only useful in mock gateways for testing purposes
	 */
	void resetData();

}
