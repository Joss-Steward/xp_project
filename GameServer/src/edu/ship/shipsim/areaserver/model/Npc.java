package edu.ship.shipsim.areaserver.model;

import java.util.Timer;
import java.util.TimerTask;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * NPC extends Player to be compatible with the player manager and simplify many
 * messages that can go to the client.
 * 
 * @author Steve
 *
 */
@DatabaseTable(tableName = "Npc")
public class Npc extends Player
{
	@DatabaseField
	private String behaviorClassName;
	
	private NPCBehavior behavior;
	
	@DatabaseField(canBeNull = false)
	private String mapName;
	
	@DatabaseField(canBeNull = false)
	private String name;

	private NpcTimerTask timedEvent;
	private Timer timer;
	
	/**
	 * How often the npc behavior is queued
	 */
	public static int TIMER_SECONDS = 1;
	
	/**
	 * Instantiate the NPC
	 */
	public Npc()
	{
		timer = new Timer();
	}
	
	/**
	 * Use the behavior class name stored in the database to create a Behavior instance
	 * if available
	 */
	public void initializeFromDatabase()
	{
		if(behaviorClassName != null)
		{
			try
			{
				System.out.println("Creating behavior for " + behaviorClassName);
				behavior = (NPCBehavior) Class.forName(behaviorClassName).newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) 
			{
				behavior = null;
				e.printStackTrace();
			}
		}
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
	 * 
	 * @param behavior The new behavior
	 */
	public void setBehavior(NPCBehavior behavior)
	{
		this.behavior = behavior;
		this.behaviorClassName = behavior.getClass().getName();
	}
	
	/**
	 * 
	 * @return the npc's name
	 */
	public String getPlayerName()
	{
		return name;
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

	/**
	 * 
	 * @param name the new name
	 */
	public void setName(String name) 
	{
		this.name = name;
	}
}