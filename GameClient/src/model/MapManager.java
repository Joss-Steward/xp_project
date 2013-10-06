package model;

import model.reports.NewMapReport;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * @author Merlin
 * 
 */
class MapManager extends QualifiedObservable
{

	private static MapManager singleton;
	private TiledMap tiledMap;
	private boolean headless;

	/**
	 * Make the default constructor private
	 */
	private MapManager()
	{
		QualifiedObservableConnector.getSingleton().registerQualifiedObservable(this,
				NewMapReport.class);
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
	public synchronized static MapManager getSingleton()
	{
		if (singleton == null)
		{
			singleton = new MapManager();
		}
		return singleton;
	}

	/**
	 * @param fileTitle
	 *            the title of the file we should switch to
	 */
	public void changeToNewFile(String fileTitle)
	{
		if (!headless)
		{
			tiledMap = new TmxMapLoader().load(fileTitle);
		}
		this.notifyObservers(new NewMapReport(tiledMap));
	}

	// TODO why are we not getting javadoc warnings here???
	@Override
	public boolean notifiesOn(Class<?> reportType)
	{
		if (reportType == NewMapReport.class)
		{
			return true;
		}
		return false;
	}

	public MapLayer getMapLayer(String layerName)
	{
		MapLayers layers = tiledMap.getLayers();
		return layers.get(layerName);
	}

	public void setHeadless(boolean headless)
	{
		this.headless = headless;
	}

	public void setMapForTesting(TiledMap tiledMap)
	{
		this.tiledMap = tiledMap;
	}

}
