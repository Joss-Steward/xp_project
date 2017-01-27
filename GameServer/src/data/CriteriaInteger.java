package data;

/**
 * @author Emily Maust, Matthew Croft
 *
 */
public class CriteriaInteger implements AdventureCompletionCriteria
{
	private static final long serialVersionUID = 1L;

	private int target;

	/**
	 * @param target the value needed to complete this adventure
	 */
	public CriteriaInteger(int target)
	{
		this.target = target;
	}

	/**
	 * @return points for this adventure
	 */
	public int getTarget()
	{
		return target;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + target;
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
		CriteriaInteger other = (CriteriaInteger) obj;
		if (target != other.target)
			return false;
		return true;
	}
}
