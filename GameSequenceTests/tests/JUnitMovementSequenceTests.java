import static org.junit.Assert.assertEquals;

import java.io.IOException;

import model.SequenceTestRunner;
import model.ServerType;

import org.junit.Test;

import communication.CommunicationException;

import datasource.DatabaseException;

/**
 * Runs all of the movement related sequence tests as JUnit tests
 * 
 * @author Merlin
 *
 */
public class JUnitMovementSequenceTests
{

	/**
	 * Runs the simple movement
	 * 
	 * @throws IOException
	 *             shouldn't
	 * @throws CommunicationException
	 *             shouldn't
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testSuccess() throws IOException, CommunicationException,
			DatabaseException
	{
		SequenceTestRunner testToRun = new SequenceTestRunner(
				new MovementBasicSequenceTest());
		for (int i = 0; i < 4; i++)
		{
			if (i != 3)
			{
				assertEquals(SequenceTestRunner.SUCCESS_MSG,
						testToRun.run((ServerType.values())[i], false));
			}
		}
	}

	/**
	 * Runs the sequences for when the user steps on a place that triggers a quest
	 * 
	 * @throws IOException
	 *             shouldn't
	 * @throws CommunicationException
	 *             shouldn't
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testMovementTriggersQuest() throws IOException, CommunicationException, DatabaseException
	{
		SequenceTestRunner testToRun = new SequenceTestRunner(
				new MovementTriggerQuestSequenceTest());
		for (int i = 0; i < 4; i++)
		{
			if (i != 3)
			{
				assertEquals(SequenceTestRunner.SUCCESS_MSG,
						testToRun.run((ServerType.values())[i], false));
			}
		}
	}

}
