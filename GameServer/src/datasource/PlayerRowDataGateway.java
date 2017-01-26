package datasource;

import datasource.DatabaseException;
import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;

/**
 * 
 * @author Merlin
 *
 */
public interface PlayerRowDataGateway
{

	/**
	 * @return the appearance type that this player should be animated with
	 */
	String getAppearanceType();

	/**
	 * @return the crew to which this player belongs
	 */
	Crew getCrew();

	/**
	 * @return this player's major
	 */
	Major getMajor();

	/**
	 * @return the player's experience points
	 */
	int getExperiencePoints();

	/**
	 * @return this player's player id
	 */
	int getPlayerID();

	/**
	 * @return the coordinate this player is currently on
	 */
	Position getPosition();

	/**
	 * @return this player's quiz score
	 */
	int getQuizScore();

	/**
	 * Store this information into the data source
	 * 
	 * @throws DatabaseException if we can't persist the data to the data source
	 */
	void persist() throws DatabaseException;

	/**
	 * For testing purposes
	 */
	public void resetData();

	/**
	 * @param appearanceType the name of the animation this player should use
	 */
	void setAppearanceType(String appearanceType);

	/**
	 * @param crew the crew to which this player should belong
	 */
	void setCrew(Crew crew);

	/**
	 * @param major of this player
	 */
	void setMajor(Major major);

	/**
	 * @param experiencePoints this player's new experience points
	 */
	void setExperiencePoints(int experiencePoints);

	/**
	 * @param position the coordinate the player is currently on
	 */
	void setPosition(Position position);

	/**
	 * @param quizScore this player's new score
	 */
	void setQuizScore(int quizScore);
}
