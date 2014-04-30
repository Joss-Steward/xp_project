package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Observable;

import org.junit.Test;

/**
 * Tests the Npc Class
 * 
 * @author Steve
 *
 */
public class NpcTest 
{
	/**
	 * Test that the npc hits the behavior like it should
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void testBehaviorFires() throws InterruptedException 
	{
		MockBehavior mb = new MockBehavior();
		Npc npc = new Npc();
		npc.setBehavior(mb);
		npc.start();
		
		//Need to sleep for a known count + a grace period because threads are unpredictable
		Thread.sleep(mb.getPollingInterval() * 3 + (mb.getPollingInterval() / 5));
		
		npc.stop();
		
		assertEquals(3, mb.count);
	}
	
	/**
	 */
	private class MockBehavior extends Behavior
	{
		private static final long serialVersionUID = -1879830711372276973L;
		
		public MockBehavior()
		{
			pollingInterval = 50;
		}
		
		public int count = 0;
		public void doTimedEvent() 
		{	
			this.count++;
		}

		@Override
		public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes() {
			return null;
		}

		@Override
		public void update(Observable o, Object arg) {
		}		
	}
}