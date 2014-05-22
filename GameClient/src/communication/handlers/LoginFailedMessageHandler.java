package communication.handlers;

import communication.handlers.MessageHandler;
import communication.messages.LoginFailedMessage;
import communication.messages.Message;
import edu.ship.shipsim.client.model.CommandLoginFailed;
import edu.ship.shipsim.client.model.ModelFacade;

/**
 * Should process an incoming LoginFailedMessage. 
 * 
 * Creates the CommandLogInFailed and queues it into the ModelFacade singleton. 
 * 
 * @author Dave, Andy, Matt
 * 
 */
public class LoginFailedMessageHandler extends MessageHandler
{

	/**
	 * 
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		System.out.println("received " + msg);
		if (msg.getClass().equals(LoginFailedMessage.class))
		{
			CommandLoginFailed cmd = new CommandLoginFailed();
			ModelFacade.getSingleton().queueCommand(cmd);
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return LoginFailedMessage.class;
	}

}
