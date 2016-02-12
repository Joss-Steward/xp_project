package data;

public class MapLocation implements AdventureCompletionCriteria
{

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mapName == null) ? 0 : mapName.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapLocation other = (MapLocation) obj;
		if (mapName == null)
		{
			if (other.mapName != null)
				return false;
		} else if (!mapName.equals(other.mapName))
			return false;
		if (position == null)
		{
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

	private String mapName;
	private Position position;

	public MapLocation(String mapName, Position position)
	{
		this.mapName = mapName;
		this.position = position;
	}

	public String getMapName()
	{
		return mapName;
	}

	public Position getPosition()
	{
		return position;
	}

}
