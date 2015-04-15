package communication.handlers;

import communication.messages.ExperienceChangedMessage;
import communication.messages.Message;

import edu.ship.shipsim.client.model.CommandOverwriteExperience;
import edu.ship.shipsim.client.model.ModelFacade;

/**
 * @author Ryan
 *
 */
public class ExperienceChangedMessageHandler extends MessageHandler
{

	/**
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg) 
	{
		if (msg.getClass().equals(ExperienceChangedMessage.class))
		{
			ExperienceChangedMessage experienceChangedMessage = (ExperienceChangedMessage) msg;
			CommandOverwriteExperience cmd = new CommandOverwriteExperience(experienceChangedMessage.getExperiencePoints(),
					experienceChangedMessage.getLevel());
			ModelFacade.getSingleton().queueCommand(cmd);
		}
		
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle() 
	{
		return ExperienceChangedMessage.class;
	}

}
