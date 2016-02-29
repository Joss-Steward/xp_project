package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Quest;

import org.junit.Test;

import data.AdventureCompletionType;
import data.AdventureRecord;
import data.CriteriaString;
import data.GameLocation;
import data.Position;
import data.QuestCompletionActionType;

/**
 * This class holds the tests for the Quest class
 * 
 * @author Scott Lantz, LaVonne Diller
 *
 */
public class QuestTest
{

	/**
	 * Test the initialization of Quests and its parameters
	 */
	@Test
	public void testInitialize()
	{
		ArrayList<AdventureRecord> adventures = new ArrayList<AdventureRecord>();
		adventures.add(new AdventureRecord(5, 42, "Merlin Zone", 4,
				AdventureCompletionType.CHAT, new CriteriaString("Henry")));
		adventures.add(new AdventureRecord(5, 420, "Library Quest", 8,
				AdventureCompletionType.MOVEMENT, new GameLocation("current.tmx",
						new Position(42, 3))));

		Position pos = new Position(33, 44);

		Quest q = new Quest(245, "I am a description", "HappyZone", pos, adventures, 42,
				13, QuestCompletionActionType.NO_ACTION, null);

		assertEquals(245, q.getQuestID());
		assertEquals("I am a description", q.getDescription());
		assertEquals("HappyZone", q.getMapName());
		assertEquals(pos, q.getPos());
		assertEquals(q.getAdventures(), adventures);
		assertEquals(4, q.getAdventureXP(42));
		assertEquals(8, q.getAdventureXP(420));
		assertEquals(42, q.getExperiencePointsGained());
		assertEquals(13, q.getAdventuresForFulfillment());
		ArrayList<AdventureRecord> advs = q.getAdventures();
		for (AdventureRecord a : advs)
		{
			if (a.getAdventureID() == 42)
			{
				assertEquals(AdventureCompletionType.CHAT, a.getCompletionType());
				assertEquals(new CriteriaString("Henry"), a.getCompletionCriteria());
			} else if (a.getAdventureID() == 420)
			{
				assertEquals(AdventureCompletionType.MOVEMENT, a.getCompletionType());
				assertEquals(new GameLocation("current.tmx", new Position(42, 3)),
						a.getCompletionCriteria());
			} else
			{
				fail("Unexpected adventure with description "
						+ a.getAdventureDescription());
			}
		}
	}

	/**
	 * Test the setters for setting description and state of quests
	 */
	@Test
	public void testSetters()
	{
		Quest q = new Quest(-1, null, null, null, null, 42, 45, null, null);
		ArrayList<AdventureRecord> adventures = new ArrayList<AdventureRecord>();
		adventures.add(new AdventureRecord(5, 42, "Merlin Zone", 4,
				AdventureCompletionType.CHAT, new CriteriaString("Henry")));
		adventures.add(new AdventureRecord(5, 420, "Library Quest", 8,
				AdventureCompletionType.MOVEMENT, new GameLocation("current.tmx",
						new Position(42, 3))));
		Position pos = new Position(22, 20);

		q.setQuestID(44);
		q.setDescription("I am set");
		q.setMapName("Map Name");
		q.setPos(pos);
		q.setAdventures(adventures);

		assertEquals(44, q.getQuestID());
		assertEquals("I am set", q.getDescription());
		assertEquals("Map Name", q.getMapName());
		assertEquals(pos, q.getPos());
		assertNotNull(q.getAdventures().contains(adventures));
	}
}
