package model;

import static org.junit.Assert.*;

import org.junit.Test;

import datasource.QuestStateList;

/**
 * Test the 
 * @author nk3668
 *
 */
public class ClientPlayerQuestTest {

	/**
	 * Tests that a clients player will contain state, id, and a description.
	 */
	@Test
	public void testClientPlayerQuestInitialization() {
		ClientPlayerQuest q = new ClientPlayerQuest(1, "Test Quest 1", QuestStateList.HIDDEN);
		assertEquals(1, q.getQuestID());
		assertEquals("Test Quest 1", q.getQuestDescription());
		assertEquals(QuestStateList.HIDDEN, q.getQuestState());
	}

}
