package Quest;

import static org.junit.Assert.*;

import org.junit.Test;
import Quest.QuestPropertiesObjectReader;
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
		QuestPropertiesObjectReader qor = new QuestPropertiesObjectReader();
		assertNotNull(qor);

	}

	/**
	 * Tests all defaults of newly created QuestLayerObjectReader
	 */
	@Test
	public void testNullProperties()
	{
		// trigger properties

		MapProperties properties = null;
		Trigger trig = QuestPropertiesObjectReader.getTrigger(properties);
		assertEquals(null, trig.getName());
		assertEquals(null, trig.getParentName());
		assertEquals(null, trig.getPosition());
	}

	/**
	 * Tests putting in triggers into map properties
	 */
	@Test
	public void testFillingInTriggerProperties()
	{
		MapProperties properties = new MapProperties();

		MapObject bob = new MapObject();
		bob.getProperties();

		properties.put("type", "trigger");
		properties.put("name", "myTrigger");
		properties.put("parent name", "task1");
		properties.put("positionX", "1");
		properties.put("positionY", "3");
		properties.put("enum", "ACTIVATE_TASK");
		Trigger t = QuestPropertiesObjectReader.getTrigger(properties);
		assertNotNull(t);
		assertEquals("myTrigger", t.getName());
		assertEquals(1, t.getPosition().getRow());
		assertEquals(3, t.getPosition().getColumn());
		assertEquals("task1", t.getParentName());
		assertEquals(TriggerTypes.ACTIVATE_TASK, t.getTriggerType());

	}
}
