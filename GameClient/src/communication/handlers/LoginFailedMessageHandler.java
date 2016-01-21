package communication.handlers;

import model.CommandLoginFailed;
import model.ClientModelFacade;
import communication.handlers.MessageHandler;
import communication.messages.LoginFailedMessage;
import communication.messages.Message;

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
			ClientModelFacade.getSingleton().queueCommand(cmd);
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
