package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.util.Observer;

import model.reports.NewMapReport;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * @author Merlin
 * 
 */
public class MapManagerTest
{
	/**
	 * reset the singleton
	 */
	@Before
	public void setup()
	{
		QualifiedObservableConnector.resetSingleton();
		ModelFacade.resetSingleton(true);
	}

	/**
	 * make sure it is a good singleton
	 */
	@Test
	public void isResetableSingleton()
	{
		MapManager facade1 = MapManager.getSingleton();
		MapManager facade2 = MapManager.getSingleton();
		assertSame(facade1, facade2);
		MapManager.resetSingleton();
		assertNotSame(facade1, MapManager.getSingleton());
	}

	/**
	 * Should update observers of NewMapReport when it gets a new map
	 */
	@Test
	public void updatesOnNewMap()
	{
		Observer obs = EasyMock.createMock(Observer.class);
		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
		cm.registerObserver(obs, NewMapReport.class);
		obs.update(EasyMock.isA(MapManager.class), EasyMock.isA(NewMapReport.class));
		EasyMock.replay(obs);

		MapManager map = MapManager.getSingleton();
		
		map.changeToNewFile("testmaps/simple.tmx");
		EasyMock.verify(obs);
	}

	/**
	 * Since we can't really open the tmx file (libgdx can't do that headless),
	 * we have to mock it
	 */
	@Test
	public void canMockTiledMapAndGetALayer()
	{
		TiledMap tiledMap = EasyMock.createMock(TiledMap.class);
		MapLayers mapLayers = EasyMock.createMock(MapLayers.class);
		EasyMock.expect(tiledMap.getLayers()).andReturn(mapLayers);
		MapLayer mapLayer = EasyMock.createMock(MapLayer.class);
		EasyMock.expect(mapLayers.get("Foreground")).andReturn(mapLayer);
		EasyMock.replay(tiledMap);
		EasyMock.replay(mapLayers);
		EasyMock.replay(mapLayer);

		MapManager.getSingleton().setMapForTesting(tiledMap);
		MapLayer layer1 = MapManager.getSingleton().getMapLayer("Foreground");
		assertEquals(mapLayer, layer1);

		EasyMock.verify(tiledMap);
		EasyMock.verify(mapLayers);
		EasyMock.verify(mapLayer);
	}
}
