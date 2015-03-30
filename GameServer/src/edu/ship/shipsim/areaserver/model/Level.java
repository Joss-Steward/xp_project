package edu.ship.shipsim.areaserver.model;

/**
 * Level object class that contains a description and levelUpPoints
 * @author Merlin
 *
 */
public class Level
{

	private String description;

	private int levelUpPoints;

	/**
	 * Constructor for level object
	 * @param description the level's description
	 * @param levelUpPoints the levelUpPoints for the level
	 */
	public Level(String description, int levelUpPoints)
	{
		super();
		this.description = description;
		this.levelUpPoints = levelUpPoints;
	}

	/**
	 * @return description of the level
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return levelUpPoints of the level
	 */
	public int getLevelUpPoints()
	{
		return levelUpPoints;
	}

}
