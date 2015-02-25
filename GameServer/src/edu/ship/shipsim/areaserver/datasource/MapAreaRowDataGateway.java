package edu.ship.shipsim.areaserver.datasource;

/**
 * Defines what is required of all Map Area gateways
 * @author Merlin
 *
 */
public abstract class MapAreaRowDataGateway
{

	/**
	 * @return the area name of this map area
	 */
	public abstract String getMapAreaName();

	/**
	 * @return the quest ID of the quest that is associated with this map area
	 */
	public abstract int getQuestID();

	/**
	 * reset the data in mock versions of this gateway
	 */
	public abstract void resetData();

}
