package model;

import com.badlogic.gdx.utils.Timer;
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

	private static ModelFacade singleton;

	/**
	 * @param headless
	 *            TODO
	 * @return the only one of these there is
	 */
	public synchronized static ModelFacade getSingleton(boolean headless)
	{
		if (singleton == null)
		{
			singleton = new ModelFacade(headless);
		}
		return singleton;
	}

	/**
	 * @param headless TODO
	 * 
	 */
	public synchronized static void resetSingleton(boolean headless)
	{
		singleton = new ModelFacade(headless);
		MapManager.resetSingleton();
	}

	private InformationQueue commandQueue;


	/**
	 * Make the default constructor private
	 * 
	 * @param headless
	 *            TODO
	 */
	private ModelFacade(boolean headless)
	{
		setHeadless(headless);
		commandQueue = new InformationQueue();
		if (!headless)
		{
			Timer.schedule(new Task()
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

			}, (float) 0.25, (float) 0.25);
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
	public void setHeadless(boolean headless)
	{
		MapManager.getSingleton().setHeadless(headless);
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

}
