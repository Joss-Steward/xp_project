package datasource;

/**
 * @author Merlin
 *
 */
public enum LevelsForTest
{

	/**
	 * 
	 */
	ONE("Serf", 45),
	/**
	 * 
	 */
	TWO("Freemerchant", 85),
	/**
	 * 
	 */
	THREE("Lord", 125),
	/**
	 * 
	 */
	FOUR("King", Integer.MAX_VALUE);
	
	private String description;

	private int levelUpPoints;

	LevelsForTest(String description, int levelUpPoints)
	{
		this.description = description;
		this.levelUpPoints = levelUpPoints;
	}

	/**
	 * @return the description of the level
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return the number of points to level up out of this level
	 */
	public int getLevelUpPoints()
	{
		return levelUpPoints;
	}
}
