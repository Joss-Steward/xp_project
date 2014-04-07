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
	private final Position position;
	private final String destination;
	private final int port;
	
	/**
	 * @param position The position to connect to
	 * @param destination The URL to connect to
	 * @param port The port to connect to
	 */
	public ChangeMapReport(Position position, String destination, int port) 
	{
		this.position = position;
		this.destination = destination;
		this.port = port;
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
	 * @return The server to connect to
	 */
	public String getDestination()
	{
		return destination;
	}

	/**
	 * 
	 * @return The port to connect to
	 */
	public int getPort()
	{
		return port;
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
		
		if(this.getDestination() == null) {
			equals = equals && other.getDestination() == null;
		} else {
			equals = equals && this.getDestination().equals(other.getDestination());
		}
		
		if(this.getPosition() == null) {
			equals = equals && other.getPosition() == null;
		} else {
			equals = equals && this.getPosition().equals(other.getPosition());
		}
		
		equals = equals && this.getPort() == other.getPort();
		
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
		result = prime * result + ((this.getDestination() == null) ? 0 : this.getDestination().hashCode());
		result = prime * result + ((this.getPosition() == null) ? 0 : this.getPosition().hashCode());
		result = prime * result + this.getPort();
		return result;
	}
}
