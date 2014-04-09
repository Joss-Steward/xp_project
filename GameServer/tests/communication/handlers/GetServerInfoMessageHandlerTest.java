package communication.handlers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.GetServerInfoMessage;
import communication.messages.GetServerInfoResponseMessage;
import communication.messages.Message;
import model.ServersInDB;

/**
 * Test the handler for GetServerInfoMessages
 * @author Merlin
 *
 */
public class GetServerInfoMessageHandlerTest
{
	/**
	 * It should correctly report the type of messages it handles
	 */
	@Test
	public void messageTypeCorrect()
	{
		GetServerInfoMessageHandler handler = new GetServerInfoMessageHandler();
		assertEquals(GetServerInfoMessage.class, handler.getMessageTypeWeHandle());
	}
	
	/**
	 * Make sure that the appropriate reponse message gets queued into the accumulator
	 */
	@Test
	public void generatesCorrectResponse()
	{
		GetServerInfoMessageHandler handler = new GetServerInfoMessageHandler();
		StateAccumulator accum = new StateAccumulator(null);
		handler.setAccumulator(accum);
		GetServerInfoMessage msg = new GetServerInfoMessage(ServersInDB.FIRST_SERVER.getMapName());
		
		handler.process(msg);
		
		ArrayList<Message> queue = accum.getPendingMsgs();
		assertEquals(1, queue.size());
		GetServerInfoResponseMessage response = (GetServerInfoResponseMessage) queue.get(0);
		assertEquals(ServersInDB.FIRST_SERVER.getMapName(), response.getMapName());
		assertEquals(ServersInDB.FIRST_SERVER.getHostName(), response.getHostName());
		assertEquals(ServersInDB.FIRST_SERVER.getPortNumber(), response.getPortNumber());
	}

}
