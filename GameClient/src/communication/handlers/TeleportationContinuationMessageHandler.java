package communication.handlers;

import java.io.IOException;
import java.net.Socket;

import communication.messages.Message;
import communication.messages.TeleportationContinuationMessage;

/**
 * Should process an incoming LoginSuccessulMessage. This means that we should
 * move our connection to the area server specified by that msg and initiate a
 * session with that server
 * 
 * @author merlin
 * 
 */
public class TeleportationContinuationMessageHandler extends MessageHandler
{

	/**
	 * 
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		System.out.println("received " + msg);
		if (msg.getClass().equals(TeleportationContinuationMessage.class))
		{
			TeleportationContinuationMessage rMsg = (TeleportationContinuationMessage) msg;
			try
			{
				System.err.println("Debug player id: " + rMsg.getPlayerID() + " & Pin: " + rMsg.getPin());
				connectionManager.moveToNewSocket(
						new Socket(rMsg.getHostName(), rMsg.getPortNumber()),
						rMsg.getPlayerID(), rMsg.getPin());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return TeleportationContinuationMessage.class;
	}

}
