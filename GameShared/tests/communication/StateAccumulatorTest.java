package communication;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Observable;

import model.QualifiedObservableReport;

import org.easymock.EasyMock;
import org.junit.Test;

import communication.MessagePacker;
import communication.MessagePackerSet;
import communication.StateAccumulator;
import communication.StateAccumulatorConnector;
import communication.messages.Message;


/** 
 * Tests for StateAccumulators
 * @author merlin
 *
 */
public class StateAccumulatorTest
{

	/**
	 * When it initializes, it should use the given StateAccumulatorConnector to register itself as an
	 * observer to all of the things it should observe
	 */
	@Test
	public void initializes()
	{
		StateAccumulatorConnector conn = EasyMock.createMock(StateAccumulatorConnector.class);
		EasyMock.expect(conn.getMessagePackerSet(EasyMock.anyObject(StateAccumulator.class))).andReturn(new MessagePackerSet());
		EasyMock.replay(conn);
		
		new StateAccumulator(conn);
		EasyMock.verify(conn);
	}
	
	/**
	 * When we ask it for the pending messages, it should empty itself so we don't see them again
	 */
	@Test
	public void emptiesOnQuery()
	{
		StateAccumulatorConnector conn = EasyMock.createMock(StateAccumulatorConnector.class);
		EasyMock.expect(conn.getMessagePackerSet(EasyMock.anyObject(StateAccumulator.class))).andReturn(new MessagePackerSet());
		Message msg = EasyMock.createMock(Message.class);
		EasyMock.replay(msg);
		EasyMock.replay(conn);
		
		StateAccumulator accum = new StateAccumulator(conn);
		ArrayList<Message> mockMessages = new ArrayList<Message>();
		mockMessages.add(msg);
		accum.pendingMsgs = mockMessages;
		ArrayList<Message> returnedMessages = accum.getPendingMsgs();
		assertEquals(1, returnedMessages.size());
		assertEquals(0, accum.pendingMsgs.size());
		EasyMock.verify(conn);
	}

	/**
	 * Make sure that an appropriate message gets queued when the accumulator gets updated
	 */
	@Test
	public void queuesOnUpdate()
	{
		MockObservable obs = new MockObservable();
		QualifiedObservableReport object = EasyMock.createMock(QualifiedObservableReport.class);
		Message msg = EasyMock.createMock(Message.class);
		MessagePackerSet packerSet = new MessagePackerSet();
		MessagePacker packer = EasyMock.createMock(MessagePacker.class);
		EasyMock.expect(packer.pack(object)).andReturn(msg);
		packerSet.registerPacker( object.getClass(), packer);
		StateAccumulatorConnector conn = EasyMock.createMock(StateAccumulatorConnector.class);
		EasyMock.expect(conn.getMessagePackerSet(EasyMock.anyObject(StateAccumulator.class))).andReturn(packerSet);
		
		EasyMock.replay(conn);
		EasyMock.replay(msg);
		EasyMock.replay(packer);
		
		StateAccumulator accum = new StateAccumulator(conn);
		accum.update(obs, object);
		ArrayList<Message> pending = accum.pendingMsgs;
		assertEquals(1, pending.size());
		assertEquals(msg, pending.get(0));
		
		EasyMock.verify(conn);
		EasyMock.verify(msg);
		EasyMock.verify(packer);
	}

	private class MockObservable extends Observable
	{

		
	}
}
