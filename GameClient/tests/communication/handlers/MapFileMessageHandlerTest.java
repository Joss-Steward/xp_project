package communication.handlers;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import junitx.framework.FileAssert;
import model.ModelFacade;

import org.junit.Before;
import org.junit.Test;

import communication.messages.MapFileMessage;

/**
 * @author Merlin
 * 
 */
public class MapFileMessageHandlerTest
{

	/**
	 * reset the singletons and tell the model we are running headless
	 */
	@Before
	public void setUp()
	{
		ModelFacade.resetSingleton();
		ModelFacade.getSingleton(true, false);
	}

	/**
	 * The handler should tell the model that the new file is there. We will
	 * know that happens if the model reports the change to its observers
	 * 
	 * @throws IOException
	 *             shouldn't
	 * @throws InterruptedException
	 *             shouldn't
	 */
	@Test
	public void tellsEngine() throws IOException, InterruptedException
	{
		MapFileMessage msg = new MapFileMessage("testMaps/simple.tmx");
		MapFileMessageHandler handler = new MapFileMessageHandler();
		handler.process(msg);

		assertEquals(1, ModelFacade.getSingleton().getCommandQueueLength());
	}
}
