package edu.ship.shipsim.areaserver.datasource;

import data.Position;

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

	/**
	 * 
	 * @return the name of the map that contains the trigger point for this quest
	 */
	public String getTriggerMapName();

	/**
	 * 
	 * @return the position that should trigger this quest
	 */
	public Position getTriggerPosition();

}
