package model;

import java.util.Timer;
import java.util.TimerTask;

import com.j256.ormlite.field.DataType;
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
	@DatabaseField(dataType = DataType.SERIALIZABLE)
	private Behavior behavior;
	
	@DatabaseField(canBeNull = false)
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
	public Npc()
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
	 * 
	 * @param behavior The new behavior
	 */
	public void setBehavior(Behavior behavior)
	{
		this.behavior = behavior;
	}
	
	/**
	 * Begin timing for the npc's behavior
	 */
	public void start()
	{
		timedEvent = new NpcTimerTask(behavior);
		//The event is going to occur every seconds*1000 ms (second param), but will start after seconds*1000ms (first)
		// has passed. This is so behavior doesn't occur as soon as start happens
		timer.scheduleAtFixedRate(timedEvent, behavior.getPollingInterval(), behavior.getPollingInterval());
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
		Behavior behavior;
		
		public NpcTimerTask(Behavior behavior)
		{
			this.behavior = behavior;
		}
		
		@Override
		public void run() 
		{
			this.behavior.doTimedEvent();
		}
	}
}