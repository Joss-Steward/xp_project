package model;

/**
 * A facade that allows things outside the model to change or retrieve things
 * from inside the model
 * 
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
	 * 
	 * @param fileTitle
	 *            the title of the new file
	 */
	public void setMapFile(String fileTitle)
	{
		MapManager.getSingleton().changeToNewFile(fileTitle);
	}

	/**
	 * Tell the model whether or not it can use the functions of libGDX that
	 * require graphics. Note that the default is that we do use graphics - we
	 * just need to turn it off for testing
	 * 
	 * @param headless
	 *            true if we can't use graphics related things
	 */
	public void setHeadless(boolean headless)
	{
		MapManager.getSingleton().setHeadless(headless);
	}

	/**
	 * @return the name of the map file we are using
	 */
	public String getMapFileTitle()
	{
		return MapManager.getSingleton().getMapFileTitle();
	}

}
