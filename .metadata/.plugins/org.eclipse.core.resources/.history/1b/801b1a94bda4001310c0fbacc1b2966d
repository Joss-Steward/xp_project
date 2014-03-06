package Quest;
import static org.junit.Assert.*;

import org.junit.Test;

import Quest.Task;
import Quest.Trigger;
import data.Position;

/**
 * @author Joshua Tests for tasks
 */
public class TaskTest
{
	/**
	 * Tests the inialization
	 */
	@Test
	public void testInitialization()
	{
		Task t = new Task("task1");
		assertEquals("task1", t.getName());
		assertEquals(null, t.getTrigger());

	}

	/**
	 * Tests the chaning name of the task
	 */
	@Test
	public void testNameChange()
	{
		Task t = new Task("t");
		assertEquals("t", t.getName());
		t.changeName("name");
		assertEquals("name", t.getName());

		t.setParentName("Quest1");
		assertEquals("Quest1", t.getParentName());

	}

	/**
	 * Tests the description of the task
	 */
	@Test
	public void testDescriptions()
	{
		Task t = new Task("t");
		t.setDescription("Hello");

		assertEquals("Hello", t.getDescription());
	}

	/**
	 * Tests task completedness
	 */
	@Test
	public void testCompleted()
	{
		Task t = new Task("t");
		assertEquals(false, t.isCompleted());
		t.setCompleted(true);
		assertEquals(true, t.isCompleted());

	}

	/**
	 * Tests setting triggers inside of tasks
	 */
	@Test
	public void testTriggerCreation()
	{
		Task t = new Task("t");
		Trigger trig = new Trigger();
		t.setTrigger(trig);
		t.setTrigger(trig);

		assertEquals(trig, t.getTrigger());

	}

	/**
	 * Tests activating a task
	 */
	@Test
	public void testActivateTask()
	{
		Task t = new Task("t");
		Trigger trig = new Trigger();
		t.setTrigger(trig);

		assertEquals(false, t.isActive());

		t.activateTask(true);
		assertEquals(true, t.isActive());

	}

	/**
	 * Tests deactivating a task
	 */
	@Test
	public void testDeactivateTask()
	{
		Task t = new Task("t");
		Trigger trig = new Trigger();
		t.activateTask(true);
		trig.activateTrigger(true);
		t.setTrigger(trig);

		t.activateTask(false);
		assertEquals(false, t.isActive());
		assertEquals(false, t.getTrigger().isActive());
	}

	/**
	 * Tests that a task can hold a position
	 */
	@Test
	public void testPositions()
	{
		Task t = new Task("task");
		t.setPosition(new Position(2, 3));

		assertEquals(2, t.getPosition().getRow());
		assertEquals(3, t.getPosition().getColumn());

	}

}
