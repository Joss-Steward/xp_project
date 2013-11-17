package Quest;
import static org.junit.Assert.*;

import org.junit.Test;

import Quest.Quest;
import Quest.QuestLayerObjectReader;
import Quest.Task;
import Quest.Trigger;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

/**
 * @author Joshua Tests the QuestObjectReader
 */
public class QuestLayerObjectReaderTest
{

	/**
	 * Tests creating a QuestLayerObjectReader
	 */
	@Test
	public void testInitialization()
	{
		QuestLayerObjectReader qor = new QuestLayerObjectReader();
		assertNotNull(qor);

	}

	/**
	 * Tests all defaults of newly created QuestLayerObjectReader
	 */
	@Test
	public void testNullProperties()
	{
		QuestLayerObjectReader qor = new QuestLayerObjectReader();
		MapProperties properties = new MapProperties();
		// quest properties
		Quest q = qor.getQuest(properties);
		assertEquals(null, q.getDescription());
		assertEquals(null, q.getName());
		assertEquals(null, q.getPosition());
		assertEquals(null, q.getTask(0));
		assertEquals(0, q.getTaskCount());

		// task properties
		Task t = qor.getTask(properties);
		assertEquals(null, t.getDescription());
		assertEquals(null, t.getName());
		assertEquals(null, t.getParentName());
		assertEquals(null, t.getPosition());
		assertEquals(null, t.getTrigger());

		// trigger properties
		Trigger trig = qor.getTrigger(properties);
		assertEquals(null, trig.getName());
		assertEquals(null, trig.getParentName());
		assertEquals(null, trig.getPosition());

	}

	/**
	 * Tests that task properties can be added TiledMap properties
	 */
	@Test
	public void testFillingInTaskProperties()
	{
		QuestLayerObjectReader qor = new QuestLayerObjectReader();
		MapProperties properties = new MapProperties();

		MapObject bob = new MapObject();
		bob.getProperties();

		properties.put("type", "task");
		properties.put("x", "1");
		properties.put("y", "3");
		properties.put("parent name", "quest1");
		properties.put("name", "task1");

		properties
				.put("description",
						"There once was a man from nantucket. Your mission, should you choose to accept it, is to track down this man and give him the keys to his car. Good luck soldier!");
		Task t = qor.getTask(properties);

		assertEquals(
				"There once was a man from nantucket. Your mission, should you choose to accept it, is to track down this man and give him the keys to his car. Good luck soldier!",
				t.getDescription());
		assertEquals("task1", t.getName());
		assertEquals(1, t.getPosition().getRow());
		assertEquals(3, t.getPosition().getColumn());
		assertEquals("quest1", t.getParentName());
		assertEquals(null, t.getTrigger());

	}

	/**
	 * Tests adding quest details to a map property
	 */
	@Test
	public void testFillingInQuestProperties()
	{
		QuestLayerObjectReader qor = new QuestLayerObjectReader();
		MapProperties properties = new MapProperties();

		MapObject bob = new MapObject();
		bob.getProperties();

		properties.put("type", "quest");
		properties.put("name", "quest1");
		properties.put("x", "1");
		properties.put("y", "3");
		properties.put("description", "There once was a man from nantucket.");
		Quest q = qor.getQuest(properties);

		assertEquals("There once was a man from nantucket.", q.getDescription());
		assertEquals("quest1", q.getName());
		assertEquals(1, q.getPosition().getRow());
		assertEquals(3, q.getPosition().getColumn());
		assertEquals(0, q.getTaskCount());

	}

	/**
	 * Tests putting in triggers into map properties
	 */
	@Test
	public void testFillingInTriggerProperties()
	{
		QuestLayerObjectReader qor = new QuestLayerObjectReader();
		MapProperties properties = new MapProperties();

		MapObject bob = new MapObject();
		bob.getProperties();

		properties.put("type", "trigger");
		properties.put("name", "myTrigger");
		properties.put("parent name", "task1");
		properties.put("x", "1");
		properties.put("y", "3");
		Trigger t = qor.getTrigger(properties);
		assertNotNull(t);
		assertEquals("myTrigger", t.getName());
		assertEquals(1, t.getPosition().getRow());
		assertEquals(3, t.getPosition().getColumn());
		assertEquals("task1", t.getParentName());

	}
}
