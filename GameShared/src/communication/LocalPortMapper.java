package communication;

import java.util.HashMap;

/**
 * Contains hardcoded localhost mapping of mapfile name -> port number
 * 
 * @author Steve
 *
 */
public class LocalPortMapper 
{
	private HashMap<String, Integer> mapping;
	
	/**
	 * Initialize our data
	 */
	public LocalPortMapper()
	{
		mapping = new HashMap<String, Integer>();
		mapping.put("current.tmx", 1872);
		mapping.put("quiznasium.tmx", 1873);
	}
	
	/**
	 * 
	 * @param map The map we want the port for
	 * @return The port that this map server is always locally serving from
	 */
	public int getPortForMapName(String map)
	{
		return mapping.get(map);
	}
}
