package model;

import java.util.Timer;

import model.InformationQueue;

import com.badlogic.gdx.utils.Timer.Task;

/**
 * A facade that allows things outside the model to change or retrieve things
 * from inside the model
 * 
 * @author Merlin
 * 
 */
public class ClientModelFacade
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
					synchronized (commandQueue)
					{
						cmd = (Command) commandQueue.getInfoPacket();
						cmd.execute();
						if (commandQueue.getQueueSize() == 0)
						{
							commandsPending = false;
						}
					}
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}

			}
		}

	}

	private static ClientModelFacade singleton;

	private boolean mockMode;

	/**
	 * Get the singleton. If one is there, use it. Otherwise set it up in
	 * production mode
	 * 
	 * @return the only one of these we have
	 */
	public synchronized static ClientModelFacade getSingleton()
	{
		if (singleton != null)
		{
			return singleton;
		}
		if (OptionsManager.getSingleton().isTestMode())
		{
			return getSingleton(true, true);
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
	public synchronized static ClientModelFacade getSingleton(boolean headless,
			boolean mockMode)
	{
		if (singleton == null)
		{
			singleton = new ClientModelFacade(headless, mockMode);
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
	private boolean commandsPending;
	private boolean headless;

	private Timer timer;

	/**
	 * Checks if commands are pending
	 * 
	 * @return if commands are pending
	 */
	public boolean hasCommandsPending()
	{
		return commandsPending;
	}

	/**
	 * Make the default constructor private
	 * 
	 * @param headless
	 *            true if we are running libgdx headless
	 */
	private ClientModelFacade(boolean headless, boolean mockMode)
	{
		this.headless = headless;
		this.mockMode = mockMode;
		setHeadless(headless);
		commandQueue = new InformationQueue();
		if (!headless && !mockMode)
		{
			com.badlogic.gdx.utils.Timer.schedule(new Task()
			{
				@Override
				public void run()
				{
					synchronized (commandQueue)
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
									// let it clear
									while (commandQueue.getQueueSize() > 0)
									{
										commandQueue.getInfoPacket();
									}
								}
								if (commandQueue.getQueueSize() == 0)
								{
									commandsPending = false;
								}
							} catch (InterruptedException e)
							{
								e.printStackTrace();
							}

						}
					}
				}

			}, (float) 0.25, (float) 0.25);
		} else
		{
			if (!mockMode)
			{
				timer = new java.util.Timer();
				timer.schedule(new ProcessCommandQueueTask(), 0, 250);
			}
		}
	}

	/**
	 * If we have created the thread that processes things, kill it
	 */
	public synchronized static void killThreads()
	{
		if (singleton != null)
		{
			singleton.timer.cancel();
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
		synchronized (commandQueue)
		{
			commandsPending = true;
			commandQueue.queueInfoPacket(cmd);
		}
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

	/**
	 * @return the next command in the queue
	 * @throws InterruptedException
	 *             shouldn't
	 */
	public Command getNextCommand() throws InterruptedException
	{
		return (Command) commandQueue.getInfoPacket();
	}

	/**
	 * Clear the queue of commands waiting to be processed
	 */
	public void emptyQueue()
	{
		commandQueue = new InformationQueue();

	}

}