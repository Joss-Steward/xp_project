package Quest;

import static org.junit.Assert.*;
//import model.MapManager;

import org.easymock.EasyMock;
import org.junit.Test;

import Quest.Quest;
import Quest.QuestLayerReader;
import Quest.Task;
import Quest.Trigger;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;

;
/**
 * Tests for reading in a quest layer
 * 
 * @author Joshua
 * 
 */
public class QuestLayerReaderTest
{
	/**
	 * Test getting map layer from mapmanager
	 */

	public void testGetMapLayer()
	{
		// TODO look into reading the map layer again
		TiledMap tiledMap = EasyMock.createMock(TiledMap.class);
		MapLayers mapLayers = EasyMock.createMock(MapLayers.class);
		EasyMock.expect(tiledMap.getLayers()).andReturn(mapLayers);
		MapLayer mapLayer = EasyMock.createMock(MapLayer.class);
		EasyMock.expect(mapLayers.get("Foreground")).andReturn(mapLayer);
		EasyMock.replay(tiledMap);
		EasyMock.replay(mapLayers);
		EasyMock.replay(mapLayer);

		// MapManager.getSingleton().setMapForTesting(tiledMap);
		// MapLayer layer1 =
		// MapManager.getSingleton().getMapLayer("Foreground");
		// assertEquals(mapLayer, layer1);

		EasyMock.verify(tiledMap);
		EasyMock.verify(mapLayers);
		EasyMock.verify(mapLayer);
	}

	/**
	 * Tests initializing TiledMap and getting from mapmanager
	 */

	public void testInit()
	{

		// TODO look into reading the map layer again.
		MapLayer layer = new MapLayer();
		layer.setName("Quest");
		QuestLayerReader qlr = new QuestLayerReader();

		TiledMap tiled = new TiledMap();
		// try to pullQuest first
		// MapManager.getSingleton().setMapForTesting(tiled);
		assertFalse(qlr.pullQuestLayerFromMapManager());

		// add mapLayer to the TiledMap
		MapLayers layers = tiled.getLayers();
		layers.add(layer);
		// MapManager.getSingleton().setMapForTesting(tiled);
		// should find the Quest Layer
		assertTrue(qlr.pullQuestLayerFromMapManager());
	}

	/**
	 * Tests Pulling quest properties from a map layer
	 */
	@Test
	public void testPullQuestPropertiesFromLayer()
	{

		MapLayer layer = new MapLayer();
		layer.setName("Quest");
		MapObjects mapObjects = layer.getObjects();
		MapObject mp = new MapObject();
		MapProperties mps = mp.getProperties();
		mps.put("type", "quest");
		mapObjects.add(mp);
		QuestLayerReader qlr = new QuestLayerReader();
		qlr.setQuestLayer(layer);

		qlr.parsePropertiesFromLayer();

		assertEquals(1, qlr.getQuestList().size());

	}

	/**
	 * Tests pulling tasks from a map layer
	 */
	@Test
	public void testPullTaskPropertiesFromLayer()
	{
		MapLayer layer = new MapLayer();
		layer.setName("Quest");
		MapObjects mapObjects = layer.getObjects();
		MapObject mp = new MapObject();
		MapProperties mps = mp.getProperties();
		mps.put("type", "task");
		mapObjects.add(mp);
		QuestLayerReader qlr = new QuestLayerReader();
		qlr.setQuestLayer(layer);

		qlr.parsePropertiesFromLayer();

		assertEquals(1, qlr.getTasks().size());
	}

	/**
	 * Tests pulling triggers from a map layer
	 */
	@Test
	public void testPullTriggerPropertiesFromLayer()
	{
		MapLayer layer = new MapLayer();
		layer.setName("Quest");
		MapObjects mapObjects = layer.getObjects();
		MapObject mp = new MapObject();
		MapProperties mps = mp.getProperties();
		mps.put("type", "trigger");
		mapObjects.add(mp);
		QuestLayerReader qlr = new QuestLayerReader();
		qlr.setQuestLayer(layer);

		qlr.parsePropertiesFromLayer();

		assertEquals(1, qlr.getTriggers().size());

	}

	/**
	 * Tests hooking up triggers to tasks, and tasks to quests
	 */
	@Test
	public void testBuildRelationships()
	{
		Trigger trig = new Trigger();
		trig.setName("trig1");
		trig.setParentName("task1");

		Task task = new Task("task1");
		task.setParentName("quest1");

		Quest quest = new Quest("quest1");

		QuestLayerReader qlr = new QuestLayerReader();
		// creates new lists for quests,tasks, and triggers
		qlr.parsePropertiesFromLayer();

		qlr.getTasks().add(task);
		qlr.getTriggers().add(trig);
		qlr.getQuests().add(quest);

		qlr.buildRelationships();
		Quest builtQuest = qlr.getQuestList().get(0);
		assertNotNull(builtQuest);

		assertEquals("quest1", builtQuest.getName());
		assertEquals("task1", builtQuest.getTask(0).getName());
		assertEquals("trig1", builtQuest.getTask(0).getTrigger().getName());

	}
}
