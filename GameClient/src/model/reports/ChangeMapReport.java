package model.reports;

import model.QualifiedObservableReport;
import data.Position;

/**
 * This is the ChangeMapReport contains information for connecting to a server.
 * 
 * @author Steve
 *
 */
public final class ChangeMapReport implements QualifiedObservableReport
{
	private final int playerID;
	private final Position position;
	private final String mapName;

	
	/**
	 * @param playerID the ID of the player teleporting
	 * @param position The position to connect to
	 * @param mapName is the Map the player is going to
	 */
	public ChangeMapReport(int playerID, Position position, String mapName) 
	{
		this.playerID = playerID;
		this.position = position;
		this.mapName = mapName;
	}
	
	/**
	 * @return the Id of the player teleporting
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * 
	 * @return the position to connect to
	 */
	public Position getPosition() 
	{
		return position;
	}
	
	/**
	 * 
	 * @return The map the player is going to
	 */
	public String getMapName()
	{
		return mapName;
	}

	

	/**
	 * @see java.lang.Object#equals(Object obj)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ChangeMapReport))
			return false;
		ChangeMapReport other = (ChangeMapReport) obj;
		boolean equals = true;
		
		if(this.getMapName() == null) {
			equals = equals && other.getMapName() == null;
		} else {
			equals = equals && this.getMapName().equals(other.getMapName());
		}
		
		if(this.getPosition() == null) {
			equals = equals && other.getPosition() == null;
		} else {
			equals = equals && this.getPosition().equals(other.getPosition());
		}
		
		
		return equals;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getMapName() == null) ? 0 : this.getMapName().hashCode());
		result = prime * result + ((this.getPosition() == null) ? 0 : this.getPosition().hashCode());
		return result;
	}
}
