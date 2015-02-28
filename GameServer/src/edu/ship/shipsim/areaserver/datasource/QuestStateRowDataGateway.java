package edu.ship.shipsim.areaserver.datasource;

/**
 * The behaviors requires for gateways into the quest state data source.  There is
 * one row in this data source for every quest ID/player ID pair.
 * @author Merlin
 *
 */
public interface QuestStateRowDataGateway
{

	/**
	 * reset the data to a known state (for testing only)
	 */
	void resetData();

	/**
	 * @return the quest ID for the state we are managing
	 */
	int getQuestID();

	/**
	 * 
	 * @return the player ID for the state we are managing
	 */
	int getPlayerID();

	/**
	 * 
	 * @return The state of the given quest for the given player
	 */
	QuestState getQuestState();

}
