package edu.ship.shipsim.areaserver.datasource;

/**
 * A row data gateway for the quest table
 * @author merlin
 *
 */
public interface QuestRowDataGateway
{

	/**
	 * Used for testing - tells the mock version to reset its data back to the original state
	 */
	public void resetData();

	/**
	 * @return the unique quest ID for the row this gateway is managing
	 */
	public int getQuestID();

	/**
	 * @return the description for the quest this gateway is managing
	 */
	public String getQuestDescription();

}
