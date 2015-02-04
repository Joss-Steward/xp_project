package communication.handlers;

import model.MapToServerMapping;
import communication.messages.Message;
import communication.messages.TeleportationContinuationMessage;
import communication.messages.TeleportationInitiationMessage;
import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.model.CommandMovePlayerSilently;
import edu.ship.shipsim.areaserver.model.CommandPersistPlayer;
import edu.ship.shipsim.areaserver.model.ModelFacade;
import edu.ship.shipsim.areaserver.model.PlayerManager;

/**
 * Process GetServerInfoMessages by gathering the requested data and sending it
 * back to the requestor
 * 
 * @author Merlin
 * 
 */
public class TeleportationInitiationHandler extends MessageHandler
{

	/**
	 * This handler should retrieve the information requested and send it back
	 * 
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		int playerID = this.getStateAccumulator().getPlayerID();
		TeleportationInitiationMessage currentMsg = (TeleportationInitiationMessage) msg;
		try
		{
			CommandMovePlayerSilently command = new CommandMovePlayerSilently(
					currentMsg.getMapName(), currentMsg.getPlayerId(), currentMsg.getPosition());
			ModelFacade.getSingleton().queueCommand(command);

			CommandPersistPlayer persistCommand = new CommandPersistPlayer(currentMsg.getPlayerId());
			ModelFacade.getSingleton().queueCommand(persistCommand);
			
			MapToServerMapping mapping = new MapToServerMapping(currentMsg
					.getMapName());
			int pin = PlayerManager.getSingleton().getNewPinFor(playerID);
			TeleportationContinuationMessage response = new TeleportationContinuationMessage(
					mapping.getMapName(), mapping.getHostName(), mapping.getPortNumber(),
					playerID, pin);
			this.getStateAccumulator().queueMessage(response);
		} catch (DatabaseException e)
		{
			System.out.println("Unable to set up the completion info for teleporting");
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
