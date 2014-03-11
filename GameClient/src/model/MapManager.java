package model;

import model.reports.NewMapReport;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import data.Position;

/**
 * @author Merlin
 * 
 */
public class MapManager extends QualifiedObservable
{

	private static MapManager singleton;
	private TiledMap tiledMap;
	private boolean[][] passabilityMap;
	private boolean headless;
	private boolean noCollisionLayer;
	
	private static final String COLLISION_LAYER = "Collision";

	/**
	 * Make the default constructor private
	 */
	private MapManager()
	{
		reportTypes.add(NewMapReport.class);

		registerReportTypesWeNotify();
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
			setMap(new TmxMapLoader().load(fileTitle));
		}
		this.notifyObservers(new NewMapReport(tiledMap));
	}

	/**
	 * gets the map layer from the tiled map
	 * 
	 * @param layerName
	 *            the name of the layer to get
	 * @return MapLayer the map layer
	 */
	public MapLayer getMapLayer(String layerName)
	{
		MapLayers layers = tiledMap.getLayers();
		return layers.get(layerName);
	}

	/**
	 * Whether Gdx is running
	 * 
	 * @param headless
	 *            boolean running or not
	 */
	public void setHeadless(boolean headless)
	{
		this.headless = headless;
	}

	/**
	 * setting the Tiled map that is managed by this
	 * 
	 * @param tiledMap
	 *            TiledMap map
	 */
	public void setMap(TiledMap tiledMap)
	{
		this.tiledMap = tiledMap;
		
		//set the map's passability
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap
				.getLayers().get(COLLISION_LAYER);

		if (collisionLayer != null)
		{
			this.passabilityMap = new boolean[collisionLayer.getHeight()][collisionLayer.getWidth()];
			
			for (int col = 0; col < collisionLayer.getWidth(); col++)
			{
				for (int row = 0; row < collisionLayer.getHeight(); row++)
				{
					try 
					{
						Cell cell = collisionLayer.getCell(col, row);
						TiledMapTile tile = cell.getTile();
						int id = tile.getId();
						this.passabilityMap[row][col] = id != 0;
					}
					// Null pointers appear when a tile is not drawn in the map
					// at a specified location
					catch (NullPointerException e)
					{
						this.passabilityMap[row][col] = true;
					}
				}
			}
		} 
		else
		{
			noCollisionLayer = true;
		}
	}

	/**
	 * @param p
	 *            The position to check
	 * @return TRUE if the given position is passable terrain, FALSE if not
	 */
	public boolean getIsTilePassable(Position p)
	{
		//allow walking anywhere when there is no collision layer
		if (noCollisionLayer || headless) 
		{
			return true;
		}
		//prevent walking out of bounds
		if (p.getColumn() > this.passabilityMap.length || 
			p.getColumn() < 0 ||
			p.getRow() > this.passabilityMap[0].length ||
			p.getRow() < 0)
		{
			return false;
		}
		//check against the passability map for capable movement
		return this.passabilityMap[p.getRow()][p.getColumn()];
	}

	/**
	 * Sets the passabilityMap for testing purposes
	 * 
	 * @param pass
	 *            The new passabilityMap
	 */
	public void setPassability(boolean[][] pass)
	{
		this.passabilityMap = pass;
	}
}
