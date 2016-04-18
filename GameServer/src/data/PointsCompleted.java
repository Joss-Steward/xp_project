package data;

/**
 * @author Emily Maust, Matthew Croft
 *
 */
public class PointsCompleted implements AdventureCompletionCriteria
{
	private static final long serialVersionUID = 1L;
	
	private int points;
	
	/**
	 * @param points
	 * the points needed to complete this adventure
	 */
	public PointsCompleted(int points)
	{
		this.points = points;
	}
	
	/**
	 * @return points for this adventure
	 */
	public int getPoints()
	{
		return points;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + points;
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PointsCompleted other = (PointsCompleted) obj;
		if (points != other.points)
			return false;
		return true;
	}
}
