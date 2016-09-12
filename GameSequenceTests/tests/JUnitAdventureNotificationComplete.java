import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import communication.CommunicationException;
import model.SequenceTestRunner;
import model.ServerType;

/**
 * Runs the adventure notification complete sequence tests as JUnit tests
 * @author Ronald Sease & Evan Stevenson
 *
 */
public class JUnitAdventureNotificationComplete 
{

	/**
	 * Runs the adventure notification complete sequence test
	 * @throws IOException
	 *         shouldn't
	 * @throws CommunicationException
	 *         shouldn't
	 */
	@Test
	public void testAdventureNotificationComplete() throws IOException, CommunicationException
	{
		SequenceTestRunner testToRun = new SequenceTestRunner(new AdventureNotificationCompleteSequenceTest());
		assertEquals(SequenceTestRunner.SUCCESS_MSG, testToRun.run(ServerType.AREA_SERVER, true));
	}

}
