package model;

import java.util.Iterator;

import model.reports.NewMapReport;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ObjectMap;

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
	private ObjectMap<Position, TeleportHotSpot> teleportMap;

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
		} else
		{
			this.noCollisionLayer = true;
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

		// set the map's passability
		TiledMapTileLayer collisionLayer = null;
		MapProperties properties = null;
		
		if (!headless)
		{
			collisionLayer = (TiledMapTileLayer) tiledMap.getLayers()
					.get(COLLISION_LAYER);
			properties = this.tiledMap.getProperties();
		}

		if (collisionLayer != null)
		{
			int width = tiledMap.getProperties().get("width", Integer.class);
			int height = tiledMap.getProperties().get("height", Integer.class);
			this.passabilityMap = new boolean[height][width];

			for (int row = 0; row < height; row++)
			{
				//System.out.println((collisionLayer.getHeight()-row-1)+ ": ");
				for (int col = 0; col < width; col++)
				{

					Cell cell = collisionLayer.getCell(col, row);
					if (cell == null)
					{
						passabilityMap[height - row - 1][col] = true;
						//System.out.print("t");
					} else
					{
						this.passabilityMap[height - row - 1][col] = false;
						//System.out.print("f");
					}

				}
				//System.out.println();
			}

		} else
		{
			noCollisionLayer = true;
		}
		
		//handle parsing out teleportation hotspots
		if (properties != null)
		{
			if (this.teleportMap == null)
			{
				this.teleportMap = new ObjectMap<Position, TeleportHotSpot>();
			}
			this.teleportMap.clear();
			
			Iterator<String> propKeys = properties.getKeys();
			while (propKeys.hasNext())
			{
				String key = propKeys.next();
				//parse position of the hotspot when a property isn't a hotspot definition
				if (key.matches("[0-9]+ [0-9]+"))
				{
					String[] values = key.split(" ", 2);
					int x, y;
					x = Integer.parseInt(values[0]);
					y = Integer.parseInt(values[1]);
					Position from = new Position(x, y);
					
					values = properties.get(key).toString().split(" ");
					String mapName = values[0];
					Position to = new Position(Integer.parseInt(values[1]), Integer.parseInt(values[2]));
					
					TeleportHotSpot hotspot = new TeleportHotSpot(mapName, to);
					this.teleportMap.put(from, hotspot);		
				}
			}
		}
	}

	/**
	 * @param p
	 *            The position to check
	 * @return TRUE if the given position is passable terrain, FALSE if not
	 */
	public boolean getIsTilePassable(Position p)
	{
		// allow walking anywhere when there is no collision layer
		if (noCollisionLayer)
		{
			return true;
		}
		// prevent walking out of bounds
		if (p.getColumn() >= this.passabilityMap[0].length || p.getColumn() < 0
				|| p.getRow() >= this.passabilityMap.length || p.getRow() < 0)
		{
			return false;
		}
		//System.out.println("passability at " + p.getRow() + " " + p.getColumn() + " is "
		//		+ passabilityMap[p.getRow()][p.getColumn()]);
		// check against the passability map for capable movement
		return this.passabilityMap[p.getRow()][p.getColumn()];
	}
	
	/**
	 * @param p
	 * 		the position of the tile that is being checked to see if it's a
	 * 		teleportation hotspot
	 * @return true if the tile is a teleportation hotspot
	 * 				else return false
	 */
	public boolean getIsTileTeleport(Position p)
	{
		if (this.teleportMap == null)
			return false;
		return teleportMap.containsKey(p);
	}

	/**
	 * Sets the passabilityMap for testing purposes
	 * 
	 * @param pass
	 *            The new passabilityMap
	 */
	public void setPassability(boolean[][] pass)
	{
		this.noCollisionLayer = false;
		this.passabilityMap = pass;
	}

	/**
	 * Sets the teleport hotspot locations for testing purposes
	 * @param teleportMap
	 * 				The hashmap of teleport hotspots
	 */
	public void setTeleportHotspots(ObjectMap<Position, TeleportHotSpot> teleportMap)
	{
		this.teleportMap = teleportMap;
	}
}
