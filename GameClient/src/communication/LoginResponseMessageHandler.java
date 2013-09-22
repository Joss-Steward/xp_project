package communication;
import java.io.IOException;
import java.net.Socket;

import communication.MessageHandler;
import communication.messages.LoginResponseMessage;
import communication.messages.Message;



/**
 * Should process an incoming LoginResponseMessage.  This means that we should move our connection to the area server specified by that msg and initiate a session with that server
 * @author merlin
 *
 */
public class LoginResponseMessageHandler implements MessageHandler
{

	/**
	 * 
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		System.out.println("received " + msg);
		if (msg.getClass().equals(LoginResponseMessage.class))
		{
			LoginResponseMessage rMsg = (LoginResponseMessage)msg;
			try
			{
				ConnectionManager.getSingleton().moveToNewSocket(new Socket(rMsg.getHostName(),rMsg.getPortNumber()), rMsg.getUserID(), rMsg.getPin());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

}
