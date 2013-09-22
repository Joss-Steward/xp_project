package communication;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import model.Player;
import model.reports.LoginInitiatedReport;

import org.junit.Test;

import communication.messages.LoginMessage;
import communication.messages.Message;

/**
 * Make sure the StateAccumulator gets hooked to all of the right places
 * @author merlin
 *
 */
public class StateAccumulatorConnectorClientTest
{


	/**
	 * 
	 * @throws CommunicationException shouldn't
	 */
	@Test
	public void testLoginIsHookedUp() throws CommunicationException
	{
		StateAccumulatorConnectorClient connector = new StateAccumulatorConnectorClient();
		StateAccumulator accum = new StateAccumulator(connector);
		MessagePackerSet packerSet = connector.setUpPackersAndObservation(accum);
		MessagePacker packer = packerSet.getPackerFor(LoginInitiatedReport.class);
		assertEquals(LoginMessagePacker.class, packer.getClass());
		
		accum.update(Player.getSingleton(), new LoginInitiatedReport("ralph","dog"));
		ArrayList<Message> queue = accum.getPendingMsgs();
		assertEquals(1, queue.size());
		assertEquals(LoginMessage.class, queue.get(0).getClass());
	}
	
	/**
	 * 
	 * @throws CommunicationException shouldn't
	 */
	@Test
	public void testLMovementIsHookedUp() throws CommunicationException
	{
		fail();
//		StateAccumulatorConnectorClient connector = new StateAccumulatorConnectorClient();
//		MessagePackerSet packerSet = connector.getMessagePackerSet();
//		MessagePacker packer = packerSet.getPackerFor(Player.getSingleton(), new Position(4,3));
//		assertEquals(MovementMessagePacker.class, packer.getClass());
//		
//		StateAccumulator accum = new StateAccumulator(connector);
//		accum.update(Player.getSingleton(), new Position(4,3));
//		ArrayList<Message> queue = accum.getPendingMsgs();
//		assertEquals(1, queue.size());
//		assertEquals(MovementMessage.class, queue.get(0).getClass());
	}

}
