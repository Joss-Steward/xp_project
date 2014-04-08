package communication.messages;

/**
 * A message that allows a client to ask for the server & port number that are
 * managing a given map (tmx file)
 * 
 * @author Merlin
 * 
 */
public class GetServerInfoMessage
{

	private String mapName;

	/**
	 * Create the request message
	 * 
	 * @param mapName
	 *            the name of the tmx file of the map we are interested in
	 */
	public GetServerInfoMessage(String mapName)
	{
		this.mapName = mapName;
	}

	/**
	 * Get the name of the map we are interested in
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
		return "GetServerInfoMessage: mapName = " + mapName;
	}

}
