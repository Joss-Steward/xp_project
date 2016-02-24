import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import communication.CommunicationException;
import model.SequenceTestRunner;
import model.ServerType;

public class JUnitAdventureNotificationComplete 
{

	@Test
	public void testAdventureNotificationComplete() throws IOException, CommunicationException
	{
		SequenceTestRunner testToRun = new SequenceTestRunner(new AdventureNotificationCompleteSequenceTest());
		assertEquals(SequenceTestRunner.SUCCESS_MSG, testToRun.run(ServerType.AREA_SERVER, true));
	}

}
