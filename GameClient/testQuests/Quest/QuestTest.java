package Quest;

import static org.junit.Assert.*;

import org.junit.Test;

import Quest.Quest;
import Quest.Task;
import data.Position;

/**
 * @author Joshua Tests the quest class
 */
public class QuestTest
{
	/**
	 * Tests initial creation
	 */
	@Test
	public void testInitialization()
	{
		Quest q = new Quest("test");
		assertEquals("test", q.getName());
		assertEquals(0, q.getTaskCount());

	}

	/**
	 * Tests changing the name of the quest
	 */
	@Test
	public void testNameChanges()
	{
		Quest q = new Quest("test");

		q.changeName("bob");
		assertEquals("bob", q.getName());

	}

	/**
	 * Tests changing the description
	 */
	@Test
	public void testDescription()
	{
		Quest q = new Quest("test");
		assertEquals(null, q.getDescription());
		q.setDescription("hello");
		assertEquals("hello", q.getDescription());
	}

	/**
	 * Tests giving and taking tasks from a quest
	 */
	@Test
	public void testAddRemoveTasks()
	{
		Quest q = new Quest("test");
		assertEquals(0, q.getTaskCount());

		q.addTask(null);
		assertEquals(0, q.getTaskCount());

		Task t = new Task("testTask");
		q.addTask(t);
		assertEquals(1, q.getTaskCount());

		Task t2 = new Task("testTask");
		Task t3 = new Task("testTask");
		q.addTask(t2);
		q.addTask(t3);
		assertEquals(3, q.getTaskCount());

		q.removeTask(0);
		assertEquals(2, q.getTaskCount());

		q.removeTask(0);
		q.removeTask(0);
		assertEquals(0, q.getTaskCount());

		q.removeTask(0);
		assertEquals(0, q.getTaskCount());

		assertEquals(null, q.getTask(0));

	}

	/**
	 * Tests finding a task given a specific name
	 */
	@Test
	public void testGetTaskByName()
	{
		Quest q = new Quest("test");

		Task t1 = new Task("testTask1");
		Task t2 = new Task("testTask2");
		Task t3 = new Task("testTask3");
		Task t4 = new Task("testTask4");

		q.addTask(t1);
		q.addTask(t2);
		q.addTask(t3);
		q.addTask(t4);

		assertEquals(null, q.getTaskByName("bob"));
		assertEquals(t1, q.getTaskByName("testTask1"));
		assertEquals(t2, q.getTaskByName("testTask2"));
		assertEquals(t3, q.getTaskByName("testTask3"));
		assertEquals(t4, q.getTaskByName("testTask4"));
	}

	/**
	 * Tests removing tasks by a specific name
	 */
	@Test
	public void testRemoveTasksByName()
	{
		Quest q = new Quest("test");
		Task t1 = new Task("testTask1");
		Task t4 = new Task("testTask3");
		Task t2 = new Task("testTask2");
		Task t3 = new Task("testTask3");

		q.addTask(t1);
		q.addTask(t2);
		q.addTask(t3);
		q.addTask(t4);

		assertEquals(4, q.getTaskCount());

		q.removeTaskByName("testTask2");
		assertEquals(3, q.getTaskCount());
		assertEquals("testTask1", q.getTask(0).getName());

		q.removeTaskByName("testTask3");
		assertEquals(2, q.getTaskCount());
		assertEquals("testTask1", q.getTask(0).getName());
		assertEquals("testTask3", q.getTask(1).getName());

		q.removeTaskByName("Bob");
		assertEquals(2, q.getTaskCount());

	}

	/**
	 * Tests getting whether a task is active
	 */
	@Test
	public void testActive()
	{
		Quest q = new Quest("testQuest");
		assertEquals(false, q.isActive());

		q.activateQuest(true);

		assertEquals(true, q.isActive());
	}

	/**
	 * tests that the first task becomes active when the quest is activated
	 */
	@Test
	public void testActivateQuest()
	{
		Quest q = new Quest("testQuest");
		for (int i = 0; i < 5; i++)
		{
			q.addTask(new Task("bob"));
		}

		assertEquals(false, q.isActive());
		assertEquals(false, q.getTask(0).isActive());
		assertEquals(false, q.getTask(1).isActive());
		assertEquals(false, q.getTask(2).isActive());

		q.activateQuest(true);
		assertEquals(true, q.getTask(0).isActive());
		assertEquals(false, q.getTask(1).isActive());
		assertEquals(false, q.getTask(2).isActive());
	}

	/**
	 * Tests that all tasks are deactivated with the quest
	 */
	@Test
	public void testDeactivateQuest()
	{
		Quest q = new Quest("testQuest");
		for (int i = 0; i < 5; i++)
		{
			q.addTask(new Task("bob"));
		}

		q.activateQuest(true);
		assertEquals(true, q.getTask(0).isActive());
		assertEquals(false, q.getTask(1).isActive());
		assertEquals(false, q.getTask(2).isActive());

		q.activateQuest(false);
		assertEquals(false, q.isActive());
		assertEquals(false, q.getTask(0).isActive());
		assertEquals(false, q.getTask(1).isActive());
		assertEquals(false, q.getTask(2).isActive());
	}

	/**
	 * tests that completeing all tasks completed the quest
	 */
	@Test
	public void testCheckCompleted()
	{
		Quest q = new Quest("test");
		Task t1 = new Task("testTask1");
		Task t2 = new Task("testTask3");

		q.addTask(t1);
		q.addTask(t2);

		assertEquals(false, q.checkCompleted());

		t1.setCompleted(true);
		t2.setCompleted(true);

		assertEquals(true, q.checkCompleted());
	}

	/**
	 * Tests xy coordinates of quest
	 */
	@Test
	public void testPosition()
	{
		Quest quest = new Quest("bob");
		assertEquals(null, quest.getPosition());

		Position p = new Position(2, 3);
		quest.setPosition(p);
		assertEquals(p, quest.getPosition());
	}

	/**
	 * Tests that an addition of a new uncompleted tasks changes the
	 * completedness of the quest
	 */
	@Test
	public void testCompleted()
	{
		Quest quest = new Quest("bob");
		quest.setCompleted(true);
		assertEquals(true, quest.checkCompleted());
		assertEquals(true, quest.isCompleted());
		Task t = new Task("bobTask");
		t.activateTask(true);
		quest.addTask(t);
		assertEquals(false, quest.checkCompleted());
		assertEquals(false, quest.isCompleted());

		t.setCompleted(true);
		assertEquals(true, quest.checkCompleted());
		assertEquals(true, quest.isCompleted());

	}

	/**
	 * Tests completedness of quest
	 */
	@Test
	public void testCheckTasks()
	{
		Quest quest = new Quest("bob");
		Task t = new Task("bobTask");
		t.activateTask(true);
		quest.addTask(t);

		quest.checkTasks();
		assertEquals(false, quest.checkCompleted());
		assertEquals(false, quest.isCompleted());

		t.setCompleted(true);
		assertEquals(true, quest.checkCompleted());
		assertEquals(true, quest.isCompleted());
	}
}
