package model;

import java.util.Timer;

import model.InformationQueue;

/**
 * A facade that allows things outside the model to change or retrieve things
 * from inside the model
 * 
 * @author Merlin
 * 
 */
public class ClientModelFacade
{

	private static ClientModelFacade singleton;

	/**
	 * @return the only one of these there is
	 */
	public synchronized static ClientModelFacade getSingleton()
	{
		if (singleton == null)
		{
			singleton = new ClientModelFacade();
		}
		return singleton;
	}

	/**
	 * Used for testing to reset the state of the model
	 * 
	 */
	public synchronized static void resetSingleton()
	{
		if (singleton != null)
		{
			while (singleton.hasCommandsPending())
			{
				try
				{
					Thread.sleep(100);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		singleton = new ClientModelFacade();
	}

	private InformationQueue commandQueue;
	private boolean commandsPending;
	private Timer timer;

	public static void killThreads()
	{
		if (singleton!=null)
		{
			singleton.timer.cancel();
		}
	}
	/**
	 * Checks if commands are pending
	 * @return if commands are pending
	 */
	public boolean hasCommandsPending()
	{
		return commandsPending;
	}
	/**
	 * Make the default constructor private
	 */
	private ClientModelFacade()
	{
		commandQueue = new InformationQueue();

		timer = new java.util.Timer();
		timer.schedule(new ProcessCommandQueueTask(), 0, 250);
	}

	private class ProcessCommandQueueTask extends java.util.TimerTask
	{
		/**
		 * @see java.util.TimerTask#run()
		 */
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
	 * @return The number of commands in queue
	 */
	public int queueSize()
	{
		return commandQueue.getQueueSize();
	}
}
