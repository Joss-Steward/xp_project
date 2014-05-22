package communication.handlers;

import java.io.IOException;
import java.net.Socket;

import communication.handlers.MessageHandler;
import communication.messages.Message;
import communication.messages.PinFailedMessage;
import edu.ship.shipsim.client.model.CommandPinFailed;
import edu.ship.shipsim.client.model.ModelFacade;

/**
 * Should process an incoming LoginFailedMessage. 
 * 
 * Creates the CommandPinFailed and queues it into the ModelFacade singleton. 
 * 
 * @author Andy and Matt
 * 
 */
public class PinFailedMessageHandler extends MessageHandler
{

	/**
	 * 
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		System.out.println("received " + msg + " of class " + msg.getClass().getCanonicalName());
		if (msg.getClass().equals(PinFailedMessage.class))
		{
			try 
			{
				connectionManager.moveToNewSocket(
						new Socket("localhost", 1871),
						0, 0);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			CommandPinFailed cmd = new CommandPinFailed(msg.toString());
			ModelFacade.getSingleton().queueCommand(cmd);
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return PinFailedMessage.class;
	}

}