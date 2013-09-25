package communication.handlers;

import model.PlayerManager;
import communication.handlers.MessageHandler;
import communication.messages.LoginMessage;
import communication.messages.Message;


/**
 * Handles a report of a player moving
 * @author merlin
 *
 */
public class LoginMessageHandler implements MessageHandler
{


	/**
	 * When a user tries to login, we should tell the model so that it can check the credentials
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		System.out.println("I think this is a login message: " + msg);
		LoginMessage loginMsg = (LoginMessage)msg;
		PlayerManager.getSingleton().login(loginMsg.getUserName(),loginMsg.getPassword());
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return LoginMessage.class;
	}

}
