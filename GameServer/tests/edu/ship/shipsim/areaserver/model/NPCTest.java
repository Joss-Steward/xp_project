package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.assertEquals;

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
		
		assertEquals(3, mb.getCount());
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
	
	
}