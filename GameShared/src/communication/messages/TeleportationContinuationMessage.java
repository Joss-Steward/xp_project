package communication.messages;

import java.io.Serializable;

/**
 * A message that allows a client to ask for the server & port number that are
 * managing a given map (tmx file)
 * 
 * @author Merlin
 * 
 */
public class TeleportationContinuationMessage implements Message, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mapName;
	private String hostName;
	private int portNumber;

	/**
	 * Create the request message
	 * 
	 * @param mapName
	 *            the name of the tmx file of the map we are interested in
	 * @param hostName
	 *            the name of the host where the server managing the given map
	 *            resides
	 * @param portNumber
	 *            the port number the server managing the given map is listening
	 */
	public TeleportationContinuationMessage(String mapName, String hostName, int portNumber)
	{
		this.mapName = mapName;
		this.hostName = hostName;
		this.portNumber = portNumber;
	}

	/**
	 * Get the name of the map we are interested in
	 * 
	 * @return the map name
	 */
	public String getMapName()
	{
		return mapName;
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "TeleportationContinuationMessage: mapName = " + mapName + " and hostName = "
				+ hostName + " and portNumber = " + portNumber;
	}

	/**
	 * Get the name of the host managing the given map
	 * 
	 * @return the hostname (something like mmo.cs.ship.edu)
	 */
	public String getHostName()
	{
		return hostName;
	}

	/**
	 * Get the portnumber that the server managing the given tmx file is
	 * listening to
	 * 
	 * @return the portnumber
	 */
	public int getPortNumber()
	{
		return portNumber;
	}

}
