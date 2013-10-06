package model;

import model.reports.NewMapReport;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * @author Merlin
 * 
 */
class MapManager extends QualifiedObservable
{

	private static MapManager singleton;
	private String fileTitle;
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
		this.fileTitle = fileTitle;
		if (!headless)
		{
			tiledMap = new TmxMapLoader().load(fileTitle);
		}
		this.notifyObservers(new NewMapReport(tiledMap));
	}

	/**
	 * @return the title of the file we are using
	 */
	public String getMapFileTitle()
	{
		return fileTitle;
	}

	@Override
	public boolean notifiesOn(Class<?> reportType)
	{
		if (reportType == NewMapReport.class)
		{
			return true;
		}
		return false;
	}

	protected void setTiledMap(TiledMap tiledMap)
	{
		this.tiledMap = tiledMap;
	}

	public MapLayer getMapLayer(String layerName)
	{
		return tiledMap.getLayers().get(layerName);
	}

	public void setHeadless(boolean headless)
	{
		this.headless = headless;
	}

}
