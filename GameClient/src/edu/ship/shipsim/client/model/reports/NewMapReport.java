package edu.ship.shipsim.client.model.reports;

import com.badlogic.gdx.maps.tiled.TiledMap;

import model.QualifiedObservableReport;

/**
 * Carries a TiledMap when we change the map we are using
 * 
 * @author Merlin
 * 
 */
public final class NewMapReport implements QualifiedObservableReport
{

	private TiledMap tiledMap;

	/**
	 * @param tiledMap
	 *            the new map we have moved to
	 */
	public NewMapReport(TiledMap tiledMap)
	{
		this.tiledMap = tiledMap;
	}

	/**
	 * @return the new map we have moved to
	 */
	public TiledMap getTiledMap()
	{
		return tiledMap;
	}

}
