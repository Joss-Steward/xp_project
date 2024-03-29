package communication.handlers;

import model.CommandAdventureStateChange;
import model.ClientModelFacade;
import communication.messages.AdventureStateChangeMessage;
import communication.messages.Message;

/**
 * Handles the Adventure State Change Message
 * @author Ryan
 *
 */
public class AdventureStateChangeMessageHandler  extends MessageHandler 
{

	/**
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg) 
	{
		AdventureStateChangeMessage ourMsg = (AdventureStateChangeMessage)msg;
		CommandAdventureStateChange cmd = new CommandAdventureStateChange(ourMsg);
		ClientModelFacade.getSingleton().queueCommand(cmd);
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle() 
	{
		return AdventureStateChangeMessage.class;
	}

}
