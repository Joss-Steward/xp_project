package edu.ship.shipsim.areaserver.datasource;

import data.Position;
import datasource.DatabaseException;

/**
 * 
 * @author Merlin
 *
 */
public interface PlayerRowDataGateway
{

	/**
	 * @return the map this player is currently on
	 */
	String getMapName();

	/**
	 * @return this player's player id
	 */
	int getPlayerID();

	/**
	 * @return the coordinate this player is currently on
	 */
	Position getPosition();

	/**
	 * @return the appearance type that this player should be animated with
	 */
	String getAppearanceType();
	
	/**
	 * @return this player's quiz score
	 */
	int getQuizScore();

	/**
	 * For testing purposes
	 */
	public void resetData();

	/**
	 * @param mapName the name of the map this player is currently on
	 */
	void setMapName(String mapName);

	/**
	 * Store this information into the data source
	 * @throws DatabaseException if we can't persist the data to the data source
	 */
	void persist() throws DatabaseException;

	/**
	 * @param position the coordinate the player is currently on
	 */
	void setPosition(Position position);

	/**
	 * @param appearanceType the name of the animation this player should use
	 */
	void setAppearanceType(String appearanceType);

	/**
	 * @param quizScore this player's new score
	 */
	void setQuizScore(int quizScore);

	/**
	 * @return the player's experience points
	 */
	int getExperiencePoints();

	/**
	 * @param experiencePoints this player's new experience points
	 */
	void setExperiencePoints(int experiencePoints);
}
