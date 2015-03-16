package edu.ship.shipsim.client.model;

import model.InformationQueue;

import com.badlogic.gdx.utils.Timer.Task;

/**
 * A facade that allows things outside the model to change or retrieve things
 * from inside the model
 * 
 * @author Merlin
 * 
 */
public class ModelFacade
{

	class ProcessCommandQueueTask extends java.util.TimerTask
	{
		@Override
		public void run()
		{
			while (commandQueue.getQueueSize() > 0)
			{
				Command cmd;
				try
				{
					cmd = (Command) commandQueue.getInfoPacket();
					cmd.execute();
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}

			}
		}

	}

	private static ModelFacade singleton;

	private boolean mockMode;

	/**
	 * Get the singleton. If one is there, use it. Otherwise set it up in
	 * production mode
	 * 
	 * @return the only one of these we have
	 */
	public synchronized static ModelFacade getSingleton()
	{
		if (singleton != null)
		{
			return singleton;
		}
		return getSingleton(false, false);
	}

	/**
	 * @param headless
	 *            true if we are running libgdx headless
	 * @param mockMode
	 *            true if this is running in testing mode
	 * @return the only one of these there is
	 */
	public synchronized static ModelFacade getSingleton(boolean headless, boolean mockMode)
	{
		if (singleton == null)
		{
			singleton = new ModelFacade(headless, mockMode);
		} else
		{
			if ((singleton.headless != headless) || (singleton.mockMode != mockMode))
			{
				throw new IllegalArgumentException(
						"Can't change the mode of this singleton without resetting it first");
			}
		}
		return singleton;
	}

	/**
	 * Used for testing to reset the state of the model
	 * 
	 */
	public synchronized static void resetSingleton()
	{
		singleton = null;

		MapManager.resetSingleton();
	}

	private InformationQueue commandQueue;

	private boolean headless;

	/**
	 * Make the default constructor private
	 * 
	 * @param headless
	 *            true if we are running libgdx headless
	 */
	private ModelFacade(boolean headless, boolean mockMode)
	{
		this.headless = headless;
		this.mockMode = mockMode;
		setHeadless(headless);
		commandQueue = new InformationQueue();
		if (!mockMode)
		{
			if (!headless)
			{
				com.badlogic.gdx.utils.Timer.schedule(new Task()
				{

					@Override
					public void run()
					{
						while (commandQueue.getQueueSize() > 0)
						{
							Command cmd;
							try
							{
								cmd = (Command) commandQueue.getInfoPacket();
								cmd.execute();
								if (cmd.doDump())
								{
									//let it clear
									while (commandQueue.getQueueSize() > 0) 
									{
										commandQueue.getInfoPacket();
									}
								}
							} catch (InterruptedException e)
							{
								e.printStackTrace();
							}

						}
					}

				}, (float) 0.25, (float) 0.25);
			} else
			{
				java.util.Timer timer = new java.util.Timer();
				timer.schedule(new ProcessCommandQueueTask(), 0, 250);
			}
		}
	}

	/**
	 * Get how many commands are waiting to be queued
	 * 
	 * @return the number of commands in the queue
	 */
	public int getCommandQueueLength()
	{
		return commandQueue.getQueueSize();
	}

	/**
	 * Check if we are supposed to run without libGDX (for testing purposes)
	 * 
	 * @return true if libGDX is not being used
	 */
	public boolean getHeadless()
	{
		return headless;
	}

	/**
	 * Check if we are supposed to not process the commands - just queue them
	 * (for testing purposes)
	 * 
	 * @return true if we should not process them
	 */
	public boolean getMockMode()
	{
		return mockMode;
	}

	/**
	 * Queue a command for the model to process
	 * 
	 * @param cmd
	 *            the command to be processed
	 */
	public void queueCommand(Command cmd)
	{
		commandQueue.queueInfoPacket(cmd);
	}

	/**
	 * Tell the model whether or not it can use the functions of libGDX that
	 * require graphics. Note that the default is that we do use graphics - we
	 * just need to turn it off for testing
	 * 
	 * @param headless
	 *            true if we can't use graphics related things
	 */
	private void setHeadless(boolean headless)
	{
		MapManager.getSingleton().setHeadless(headless);
	}

	/**
	 * Used by tests to wait until the command they have queued has been
	 * processed
	 * 
	 * @return the number of commands that are in the queue
	 */
	public int queueSize()
	{
		return commandQueue.getQueueSize();
	}

	public Command getNextCommand() throws InterruptedException
	{
		return (Command) commandQueue.getInfoPacket();
	}

}
