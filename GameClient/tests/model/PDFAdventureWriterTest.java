package model;

import org.junit.Test;

import testData.PlayersForTest;
import data.AdventureStateEnum;
import data.QuestStateEnum;

/**
 * @author Merlin
 *
 */
public class PDFAdventureWriterTest
{
	/**
	 * This is really just a driver to generate a file - it doesn't "test"
	 * anything. You have to look at test.pdf to see if it was built correctly
	 */
	@Test
	public void testCanWritePDF()
	{
		ThisClientsPlayer cp = ThisClientsPlayerTest
				.setUpThisClientsPlayer(PlayersForTest.MERLIN);
		ClientPlayerAdventure a = new ClientPlayerAdventure(1,
				"This adventure's description", 0, AdventureStateEnum.TRIGGERED, true);
		ClientPlayerQuest q = new ClientPlayerQuest(1, "quest title", "Test Quest 1",
				QuestStateEnum.FINISHED, 1, 2, true);
		q.addAdventure(a);
		a = new ClientPlayerAdventure(1, "Another adventure's description", 0,
				AdventureStateEnum.TRIGGERED, true);
		q.addAdventure(a);
		cp.addQuest(q);

		PDFAdventureWriter writer = new PDFAdventureWriter();
		writer.createPDFOfTriggeredExternalAdventures("test.pdf");
	}
}
