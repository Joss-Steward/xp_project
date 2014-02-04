package communication.handlers;

import java.io.IOException;
import java.net.Socket;

import communication.handlers.MessageHandler;
import communication.messages.LoginSuccessfulMessage;
import communication.messages.Message;

/**
 * Should process an incoming LoginSuccessulMessage. This means that we should
 * move our connection to the area server specified by that msg and initiate a
 * session with that server
 * 
 * @author merlin
 * 
 */
public class LoginSuccessfulMessageHandler extends MessageHandler
{

	/**
	 * 
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		System.out.println("received " + msg);
		if (msg.getClass().equals(LoginSuccessfulMessage.class))
		{
			LoginSuccessfulMessage rMsg = (LoginSuccessfulMessage) msg;
			try
			{
				connectionManager.moveToNewSocket(
						new Socket(rMsg.getHostName(), rMsg.getPortNumber()),
						rMsg.getUserID(), rMsg.getPin());
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
		return LoginSuccessfulMessage.class;
	}

}
