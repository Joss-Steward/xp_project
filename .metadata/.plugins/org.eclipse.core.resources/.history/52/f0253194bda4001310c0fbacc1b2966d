package Quest;
import static org.junit.Assert.*;

import org.junit.Test;

import Quest.Trigger;
import data.Position;

/**
 * @author Joshua Tests triggers
 */
public class TriggerTest
{
	/**
	 * Tests inialization
	 */
	@Test
	public void testInitialization()
	{
		Trigger trig = new Trigger();
		assertEquals(false, trig.isActive());
	}

	/**
	 * Tests the name of the trigger
	 */
	@Test
	public void testName()
	{
		Trigger trig = new Trigger();
		trig.setName("trigger");
		assertEquals("trigger", trig.getName());

		trig.setParentName("parentTask");
		assertEquals("parentTask", trig.getParentName());
	}

	/**
	 * Tests the xy coordinates
	 */
	@Test
	public void testPosition()
	{
		Trigger trig = new Trigger();
		Position position = new Position(2, 3);
		trig.setPosition(position);

		assertEquals(2, trig.getPosition().getRow());
		assertEquals(3, trig.getPosition().getColumn());
	}

}