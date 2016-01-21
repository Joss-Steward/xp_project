package communication.handlers;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.ModelFacade;

import org.junit.Before;
import org.junit.Test;

import communication.messages.HighScoreResponseMessage;
import datasource.PlayerScoreRecord;

/**
 * @author nk3668
 *
 */
public class HighScoreResponseHandlerTest 
{

	/**
	 * Reset the ModelFacade
	 */
	@Before
	public void reset()
	{
		ModelFacade.resetSingleton();
		ModelFacade.getSingleton(true, false);
	}
	
	/**
	 * 
	 */
	@Test
	public void testTypeWeHandle() 
	{
		HighScoreResponseMessageHandler h = new HighScoreResponseMessageHandler();
		assertEquals(HighScoreResponseMessage.class, h.getMessageTypeWeHandle());
	}

	/**
	 * Tests that we queue the message to the facade.
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void handleHighScoreResponseMessage() throws InterruptedException
	{
		reset();
		
		ArrayList<PlayerScoreRecord> list = new ArrayList<PlayerScoreRecord>();
		list.add(new PlayerScoreRecord("Ethan", 0));
		list.add(new PlayerScoreRecord("Weaver", 3));
		list.add(new PlayerScoreRecord("Merlin", 9001));
		list.add(new PlayerScoreRecord("Nate", 984257));
		
		HighScoreResponseMessage msg = new HighScoreResponseMessage(list);
		HighScoreResponseMessageHandler h = new HighScoreResponseMessageHandler();
		h.process(msg);

		assertEquals(1, ModelFacade.getSingleton().getCommandQueueLength());
		while(ModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
		reset();
	}
}
