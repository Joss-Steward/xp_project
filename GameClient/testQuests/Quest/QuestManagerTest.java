package Quest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Quest.Quest;
import Quest.QuestManager;
import data.Position;

/**
 * @author Joshua Tests the QuestManager
 */
public class QuestManagerTest
{
	/**
	 * Tests the inialization of the QuestManager
	 */
	@Test
	public void testInit()
	{
		QuestManager qm = new QuestManager();

		assertEquals(0, qm.getPlayerPosition().getColumn());
		assertEquals(0, qm.getQuestList().size());
	}

	/**
	 * Tests holding a list of quests
	 */
	@Test
	public void testSetQuestList()
	{
		QuestManager qm = new QuestManager();
		ArrayList<Quest> ql = new ArrayList<Quest>();
		ql.add(new Quest("bob1"));
		ql.add(new Quest("bob2"));
		ql.add(new Quest("bob3"));
		ql.add(new Quest("bob4"));
		qm.setQuestList(ql);

		assertEquals("bob1", qm.getQuestList().get(0).getName());
		assertEquals("bob2", qm.getQuestList().get(1).getName());
		assertEquals("bob3", qm.getQuestList().get(2).getName());
		assertEquals("bob4", qm.getQuestList().get(3).getName());
	}

	/**
	 * Tests adding quests to the list
	 */
	@Test
	public void testAddQuests()
	{
		QuestManager qm = new QuestManager();
		Quest q1 = new Quest("bob1");
		Quest q2 = new Quest("bob2");

		qm.addQuest(q1);
		qm.addQuest(q2);

		assertEquals(q1, qm.getQuestList().get(0));
		assertEquals(q2, qm.getQuestList().get(1));

		// should not add quest to list when it already exists
		qm.addQuest(q1);
		assertEquals(q1, qm.getQuestList().get(0));
		assertEquals(q2, qm.getQuestList().get(1));

	}

	/**
	 * Tests changing player position TODO need to change to observable
	 * ->observe movement message
	 */
	@Test
	public void testPlayerPosition()
	{
		QuestManager qm = new QuestManager();
		ArrayList<Quest> ql = new ArrayList<Quest>();
		ql.add(new Quest("bob1"));
		ql.add(new Quest("bob2"));
		qm.setQuestList(ql);
		qm.setPlayerPosition(new Position(2, 3));

		assertEquals(2, qm.getPlayerPosition().getRow());
		assertEquals(3, qm.getPlayerPosition().getColumn());

	}

	/**
	 * Tests that quests update from player movement TODO: see bottom of test
	 */
	@Test
	public void testCheckQuests()
	{
		QuestManager qm = new QuestManager();

		Quest q = new Quest("bob1");
		q.activateQuest(true);
		qm.addQuest(q);

		Quest q2 = new Quest("bob2");
		q2.activateQuest(true);
		qm.addQuest(q2);

		Quest q3 = new Quest("bob3");
		q3.activateQuest(true);
		qm.addQuest(q3);

		qm.checkQuests(new Position(0, 0));
		// TODO get player to move into position to update triggers in map layer
	}

}
