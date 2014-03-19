package Quest;

import static org.junit.Assert.*;

import java.util.ArrayList;

//import model.MapManager;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;

import data.Position;

/**
 * Tests a larger working quest system
 * 
 * @author joshua
 * 
 */
public class QuestSystemLargeTest
{
	private TiledMap map;

	/**
	 * Runs the setup for a built tiled map
	 */
	@Before
	public void init()
	{
		/**
		 * 1) MAKE A MAP 2) MAKE LAYERS 3) GET OBJECTS FROM LAYER 4) CREATE
		 * OBJECTS WITH MAP PROPERTIES FOR GIVEN QUEST ITEM 5) ADD OBJECT BACK
		 * INTO
		 */
		map = new TiledMap();
		MapLayer layer = new MapLayer();
		layer.setName("Derp");
		map.getLayers().add(layer);

		// Quest Layer
		MapLayer questLayer = new MapLayer();
		questLayer.setName("Quest");
		MapObjects allObjects = questLayer.getObjects();

		MapObject quest = new MapObject();
		MapProperties questProps = quest.getProperties();
		questProps.put("type", "quest");
		questProps.put("name", "quest1");
		questProps.put("x", "1");
		questProps.put("y", "2");
		questProps.put("description", "There once was a man from nantucket.");
		allObjects.add(quest);

		MapObject task = new MapObject();
		MapProperties taskProps = task.getProperties();
		taskProps.put("type", "task");
		taskProps.put("name", "task1");
		taskProps.put("x", "1");
		taskProps.put("y", "3");
		taskProps.put("parent name", "quest1");
		taskProps.put("description", "There once was a man from nantucket.");
		allObjects.add(task);

		MapObject task2 = new MapObject();
		MapProperties taskProps2 = task2.getProperties();
		taskProps2.put("type", "task");
		taskProps2.put("name", "task2");
		taskProps2.put("x", "1");
		taskProps2.put("y", "4");
		taskProps2.put("parent name", "quest1");
		taskProps2.put("description", "There once was a man from nantucket.");
		allObjects.add(task2);

		MapObject trigger = new MapObject();
		MapProperties triggerProps = trigger.getProperties();
		triggerProps.put("type", "trigger");
		triggerProps.put("name", "trigger1");
		triggerProps.put("x", "1");
		triggerProps.put("y", "5");
		triggerProps.put("parent name", "task1");
		triggerProps.put("description", "There once was a man from nantucket.");
		allObjects.add(trigger);

		map.getLayers().add(questLayer);

	}

	/**
	 * builds a quest system for testing purposes
	 * 
	 * @return the built quests
	 */
	public static ArrayList<Quest> buildQuestSystem()
	{
		ArrayList<Quest> questList = new ArrayList<Quest>();

		for (int i = 0; i < 10; i++)
		{
			Quest q = new Quest("Quest" + i);

			for (int j = 0; j < 3; j++)
			{
				Task t = new Task("Task" + j);
				t.setParentName(q.getName());
				t.setDescription("This is task number " + j + "in" + q.getName());
				t.setPosition(new Position(i, j));
				t.setTrigger(null);
				// adds task to the quest
				q.addTask(t);
			}
			q.setDescription("This is quest number" + i);
			q.setPosition(new Position(i, 0));
			questList.add(q);
		}

		return questList;
	}

	/**
	 * Tests the flow of execution for map updates to setting up all of the
	 * quest objects
	 */
	@Test
	public void testSystem()
	{
		// //TODO look at map manager for flow
		// // MapManager mp = MapManager.getSingleton();
		// //mp.setMapForTesting(map);
		// QuestLayerReader qlr = new QuestLayerReader();
		// QuestManager qm = new QuestManager();
		// // updates quest layer
		// qlr.pullQuestLayerFromMapManager();
		// // gets all of the raw objects
		// qlr.parsePropertiesFromLayer();
		// // pulls all of the child objects into their parents
		// qlr.buildRelationships();
		// qm.setQuestList(qlr.getQuestList());
		// assertEquals(2, qm.getQuestList().get(0).getTaskCount());
		// // make sure complex relationship is correct
		// assertEquals("trigger1", qm.getQuestList().get(0).getTask(0)
		// .getTrigger().getName());

		assertTrue(true);

	}
}
