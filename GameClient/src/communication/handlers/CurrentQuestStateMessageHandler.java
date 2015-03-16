package communication.handlers;

import communication.messages.CurrentQuestStateMessage;
import communication.messages.Message;
import edu.ship.shipsim.client.model.ModelFacade;
import edu.ship.shipsim.client.model.CommandOverwriteQuestState;

public class CurrentQuestStateMessageHandler extends MessageHandler
{

	@Override
	public void process(Message msg)
	{
		CurrentQuestStateMessage ourMsg = (CurrentQuestStateMessage)msg;
		CommandOverwriteQuestState cmd = new CommandOverwriteQuestState(ourMsg);
		ModelFacade.getSingleton().queueCommand(cmd);
	}

	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return CurrentQuestStateMessage.class;
	}

}
