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
		MessagePackerSet packerSet = EasyMock.createMock(MessagePackerSet.class);
		packerSet.hookUpObservationFor(EasyMock.isA(StateAccumulator.class));
		EasyMock.replay(packerSet);
		
		new StateAccumulator(packerSet);
		EasyMock.verify(packerSet);
	}
	
	/**
	 * When we ask it for the pending messages, it should empty itself so we don't see them again
	 */
	@Test
	public void emptiesOnQuery()
	{
		MessagePackerSet packerSet = EasyMock.createMock(MessagePackerSet.class);
		packerSet.hookUpObservationFor(EasyMock.isA(StateAccumulator.class));
		Message msg = EasyMock.createMock(Message.class);
		EasyMock.replay(msg);
		EasyMock.replay(packerSet);
		
		StateAccumulator accum = new StateAccumulator(packerSet);
		ArrayList<Message> mockMessages = new ArrayList<Message>();
		mockMessages.add(msg);
		accum.pendingMsgs = mockMessages;
		ArrayList<Message> returnedMessages = accum.getPendingMsgs();
		assertEquals(1, returnedMessages.size());
		assertEquals(0, accum.pendingMsgs.size());
		EasyMock.verify(packerSet);
		EasyMock.verify(msg);
	}

	/**
	 * Make sure that an appropriate message gets queued when the accumulator gets updated
	 */
	@Test
	public void queuesOnUpdate()
	{
		MockObservable obs = new MockObservable();
		MessagePackerSet packerSet = new MessagePackerSet();
		MessagePacker packer = new MockMessagePacker();
		packerSet.registerPacker( packer);
		
		StateAccumulator accum = new StateAccumulator(packerSet);
		accum.update(obs, new MockReport());
		ArrayList<Message> pending = accum.pendingMsgs;
		assertEquals(1, pending.size());
	}

	private class MockObservable extends Observable
	{

		
	}
	private class MockReport implements QualifiedObservableReport
	{
	}
	private class MockMessagePacker implements MessagePacker
	{

		/**
		 * @see communication.MessagePacker#pack(model.QualifiedObservableReport)
		 */
		@Override
		public Message pack(QualifiedObservableReport object)
		{
			return EasyMock.createMock(Message.class);
		}

		/**
		 * @see communication.MessagePacker#getReportWePack()
		 */
		@Override
		public Class<?> getReportWePack()
		{
			return MockReport.class;
		}
		
	}
}
