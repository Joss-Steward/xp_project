package model;

/**
 * A facade that allows things outside the model to change or retrieve things from inside the model
 * @author Merlin
 *
 */
public class ModelFacade
{
	
	private static ModelFacade singleton;

	/**
	 * Make the default constructor private
	 */
	private ModelFacade()
	{
		
	}
	/**
	 * 
	 */
	public static void resetSingleton()
	{
		singleton = null;
	}

	/**
	 * @return the only one of these there is
	 */
	public synchronized static ModelFacade getSingleton()
	{
		if (singleton == null)
		{
			singleton = new ModelFacade();
		}
		return singleton;
	}
	/**
	 * Tell the model to use a new map file
	 * @param fileTitle the title of the new file
	 */
	public void setMapFile(String fileTitle)
	{
		TiledMap.getSingleton().changeToNewFile(fileTitle);
		
	}
	/**
	 * @return the name of the map file we are using
	 */
	public String getMapFileTitle()
	{
		return TiledMap.getSingleton().getMapFileTitle();
	}

}
