package model;

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
	 * @return the only one of these there is
	 */
	public synchronized static ModelFacade getSingleton()
	{
		if (singleton == null)
		{
			singleton = new ModelFacade();
		}
		return singleton;
	}

	/**
	 * Used for testing to reset the state of the model
	 * 
	 */
	public synchronized static void resetSingleton()
	{
		singleton = new ModelFacade();
	}

	private InformationQueue commandQueue;

	/**
	 * Make the default constructor private
	 */
	private ModelFacade()
	{
		commandQueue = new InformationQueue();

		java.util.Timer timer = new java.util.Timer();
		timer.schedule(new ProcessCommandQueueTask(), 0, 250);
	}

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

}
