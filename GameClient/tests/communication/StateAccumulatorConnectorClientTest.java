package communication;
import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Player;

import org.junit.Test;

import communication.CommunicationException;
import communication.MessagePackerSet;
import communication.StateAccumulator;
import communication.StateAccumulatorConnectorClient;
import communication.messages.LoginMessage;
import communication.messages.Message;
import communication.messages.MovementMessage;
import data.Position;

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
		MessagePackerSet packerSet = connector.getMessagePackerSet();
		MessagePacker packer = packerSet.getPackerFor(Player.getSingleton(), "fred");
		assertEquals(LoginMessagePacker.class, packer.getClass());
		
		StateAccumulator accum = new StateAccumulator(connector);
		accum.update(Player.getSingleton(), "fred");
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
		StateAccumulatorConnectorClient connector = new StateAccumulatorConnectorClient();
		MessagePackerSet packerSet = connector.getMessagePackerSet();
		MessagePacker packer = packerSet.getPackerFor(Player.getSingleton(), new Position(4,3));
		assertEquals(MovementMessagePacker.class, packer.getClass());
		
		StateAccumulator accum = new StateAccumulator(connector);
		accum.update(Player.getSingleton(), new Position(4,3));
		ArrayList<Message> queue = accum.getPendingMsgs();
		assertEquals(1, queue.size());
		assertEquals(MovementMessage.class, queue.get(0).getClass());
	}

}
