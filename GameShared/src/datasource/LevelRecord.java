package datasource;

/**
 * Level object class that contains a description and levelUpPoints
 * @author Merlin
 *
 */
public class LevelRecord implements Comparable<LevelRecord>
{

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + levelUpPoints;
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LevelRecord other = (LevelRecord) obj;
		if (description == null)
		{
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (levelUpPoints != other.levelUpPoints)
			return false;
		return true;
	}

	private String description;

	private int levelUpPoints;

	/**
	 * Constructor for level object
	 * @param description the level's description
	 * @param levelUpPoints the levelUpPoints for the level
	 */
	public LevelRecord(String description, int levelUpPoints)
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

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(LevelRecord o)
	{
		return levelUpPoints - o.levelUpPoints;
	}

}
