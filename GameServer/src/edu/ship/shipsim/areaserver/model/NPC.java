package edu.ship.shipsim.areaserver.model;

import java.util.Timer;
import java.util.TimerTask;

/**
 * NPC extends Player to be compatible with the player manager and simplify many
 * messages that can go to the client.
 * 
 * @author Steve
 *
 */
public class NPC extends Player
{
	private String behaviorClass;
	
	public String getBehaviorClass()
	{
		return behaviorClass;
	}

	public void setBehaviorClass(String behaviorClass)
	{
		this.behaviorClass = behaviorClass;
		if(behaviorClass != null)
		{
			try
			{
				System.out.println("Creating behavior for " + behaviorClass);
				behavior = (NPCBehavior) Class.forName(behaviorClass).newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) 
			{
				behavior = null;
				e.printStackTrace();
			}
		}
	}

	private NPCBehavior behavior;
	
	private String mapName;

	private NpcTimerTask timedEvent;
	
	private Timer timer;
	
	/**
	 * How often the npc behavior is queued
	 */
	public static int TIMER_SECONDS = 1;
	
	/**
	 * Instantiate the NPC
	 */
	public NPC()
	{
		timer = new Timer();
	}
	
	/**
	 * Set the map this npc is on
	 * @param name The map name
	 */
	public void setMap(String name) 
	{
		mapName = name;
	}
	
	/**
	 * Begin timing for the npc's behavior
	 */
	public void start()
	{
		if(behavior != null && behavior.getPollingInterval() > 0)
		{
			//The event is going to occur every seconds*1000 ms (second param), but will start after seconds*1000ms (first)
			// has passed. This is so behavior doesn't occur as soon as start happens
			timedEvent = new NpcTimerTask(behavior);
			System.out.println("[DEBUG] Creating timer with interval " + behavior.getPollingInterval());
			timer.scheduleAtFixedRate(timedEvent, behavior.getPollingInterval(), behavior.getPollingInterval());
		}
	}
	
	/**
	 * Stop timing for the behavior
	 */
	public void stop()
	{
		timer.cancel();
		timer.purge();
	}

	/**
	 * Passes the number of ticks since it started to a behavior
	 * 
	 * This is a private class because it won't be used outside of NPC and the behavior is
	 * tested through NPC's behavior
	 * 
	 * @author Steve
	 *
	 */
	private class NpcTimerTask extends TimerTask
	{
		NPCBehavior behavior;
		
		public NpcTimerTask(NPCBehavior behavior)
		{
			this.behavior = behavior;
		}
		
		@Override
		public void run() 
		{
			this.behavior.doTimedEvent();
		}
	}

	public void setBehavior(NPCBehavior mb)
	{
		this.behavior = mb;
	}
}