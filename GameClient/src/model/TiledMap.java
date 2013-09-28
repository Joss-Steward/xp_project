package model;

/**
 * @author Merlin
 * 
 */
public class TiledMap
{

	private static TiledMap singleton;
	private String fileTitle;

	/**
	 * Make the default constructor private
	 */
	private TiledMap()
	{

	}

	/**
	 * Used for testing purposes
	 */
	public static void resetSingleton()
	{
		singleton = null;
	}

	/**
	 * @return the only one of these there is
	 */
	public synchronized static TiledMap getSingleton()
	{
		if (singleton == null)
		{
			singleton = new TiledMap();
		}
		return singleton;
	}

	/**
	 * @param fileTitle
	 *            the title of the file we should switch to
	 */
	public void changeToNewFile(String fileTitle)
	{
		this.fileTitle = fileTitle;

	}

	/**
	 * @return the title of the file we are using
	 */
	public String getMapFileTitle()
	{
		return fileTitle;
	}

}
