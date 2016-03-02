package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import model.NPC;

import org.junit.Test;

/**
 * Tests the Npc Class
 * 
 * @author Steve
 *
 */
public class NPCTest 
{
	/**
	 * Test that the npc hits the behavior like it should
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void testBehaviorFires() throws InterruptedException 
	{
		NPCMockBehavior mb = new NPCMockBehavior();
		NPC npc = new NPC();
		npc.setBehavior(mb);
		npc.start();
		
		//Need to sleep for a known count + a grace period because threads are unpredictable
		Thread.sleep(mb.getPollingInterval() * 3 + (mb.getPollingInterval() / 5));
		
		npc.stop();
		
		assertTrue( mb.getCount()>0);
	}
	
	/**
	 * Test that the npc polling is optional
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void testBehaviorPollingIsOptional() throws InterruptedException 
	{
		NPCMockBehavior mb = new NPCMockBehavior();
		mb.pollingInterval = 0;
		NPC npc = new NPC();
		npc.setBehavior(mb);
		npc.start();
		
		//Need to sleep for a known count + a grace period because threads are unpredictable
		Thread.sleep(mb.getPollingInterval() * 3 + (mb.getPollingInterval() / 5));
		
		npc.stop();
		
		assertEquals(0, mb.getCount());
	}
	/**
	 * Just for testing how behaviors work
	 */
	private class NPCMockBehavior extends NPCBehavior
	{
		private static final long serialVersionUID = -1879830711372276973L;

		
		private int count = 0;

		/**
		 * @return the number of times we have done our timed event
		 */
		public int getCount()
		{
			return count;
		}

		/**
		 * 
		 */
		public NPCMockBehavior()
		{
			pollingInterval = 50;
		}

		/**
		 * @see model.NPCBehavior#doTimedEvent()
		 */
		public void doTimedEvent()
		{
			this.count++;
		}

		/**
		 * @see model.NPCBehavior#getReportTypes()
		 */
		@Override
		public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
		{
			return null;
		}

		
		/**
		 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
		 */
		@Override
		public void receiveReport(QualifiedObservableReport arg)
		{
		}
	}
	
}