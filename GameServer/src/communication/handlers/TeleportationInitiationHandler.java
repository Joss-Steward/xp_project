package communication.handlers;

import java.sql.SQLException;

import model.MapToServerMapping;
import model.ModelFacade;
import model.MovePlayerSilentlyCommand;
import communication.messages.TeleportationInitiationMessage;
import communication.messages.TeleportationContinuationMessage;
import communication.messages.Message;

/**
 * Process GetServerInfoMessages by gathering the requested data and sending it back to the requestor
 * @author Merlin
 *
 */
public class TeleportationInitiationHandler extends MessageHandler
{

	/**
	 * This handler should retrieve the information requested and send it back
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		TeleportationInitiationMessage currentMsg = (TeleportationInitiationMessage)msg;
		try
		{
			MovePlayerSilentlyCommand command = new MovePlayerSilentlyCommand(currentMsg.getPlayerId(), currentMsg.getPosition());
			ModelFacade.getSingleton().queueCommand(command);
			
			MapToServerMapping mapping = MapToServerMapping.retrieveMapping(currentMsg.getMapName());
			TeleportationContinuationMessage response = new TeleportationContinuationMessage(mapping.getMapName(), mapping.getHostName(), mapping.getPortNumber());
			this.getStateAccumulator().queueMessage(response);
		} catch (SQLException e)
		{
			System.out.println("Unable to retrieve server mapping for map file named:" + currentMsg.getMapName());
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return TeleportationInitiationMessage.class;
	}

}
