package communication.messages;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author Merlin
 * 
 */
public class MapFileMessage implements Message, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mapFileName;

	/**
	 * @param fileTitle
	 *            the name of the file we want to send
	 * @throws IOException
	 *             if the file doesn't exist or is poorly formatted
	 */
	public MapFileMessage(String fileTitle) throws IOException
	{
		this.mapFileName = fileTitle;
	}


	/**
	 * @return a string describing this message
	 */
	public String toString()
	{
		return this.mapFileName;
	}

	/**
	 * @return the name of the map file we're referencing
	 */
	public String getFileName()
	{
		return this.mapFileName;
	}

}
