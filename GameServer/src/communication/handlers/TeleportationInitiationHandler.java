package communication.handlers;

import java.sql.SQLException;

import model.DatabaseException;
import model.MapToServerMapping;
import communication.messages.TeleportationInitiationMessage;
import communication.messages.TeleportationContinuationMessage;
import communication.messages.Message;
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
					currentMsg.getPlayerId(), currentMsg.getPosition());
			ModelFacade.getSingleton().queueCommand(command);

			CommandPersistPlayer persistCommand = new CommandPersistPlayer(currentMsg.getPlayerId());
			ModelFacade.getSingleton().queueCommand(persistCommand);
			
			MapToServerMapping mapping = MapToServerMapping.retrieveMapping(currentMsg
					.getMapName());
			int pin = PlayerManager.getSingleton().getNewPinFor(playerID);
			TeleportationContinuationMessage response = new TeleportationContinuationMessage(
					mapping.getMapName(), mapping.getHostName(), mapping.getPortNumber(),
					playerID, pin);
			System.err.println("[DEBUG] pin is " + response.getPin());
			this.getStateAccumulator().queueMessage(response);
		} catch (SQLException e)
		{
			System.out.println("Unable to retrieve server mapping for map file named:"
					+ currentMsg.getMapName());
			e.printStackTrace();
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
