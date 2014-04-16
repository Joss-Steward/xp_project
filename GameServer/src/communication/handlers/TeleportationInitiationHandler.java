package communication.handlers;

import java.sql.SQLException;

import model.DatabaseException;
import model.MapToServerMapping;
import model.ModelFacade;
import model.MovePlayerSilentlyCommand;
import model.PersistPlayerCommand;
import model.PlayerManager;
import communication.messages.TeleportationInitiationMessage;
import communication.messages.TeleportationContinuationMessage;
import communication.messages.Message;

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
			MovePlayerSilentlyCommand command = new MovePlayerSilentlyCommand(
					currentMsg.getPlayerId(), currentMsg.getPosition());
			ModelFacade.getSingleton().queueCommand(command);

			PersistPlayerCommand persistCommand = new PersistPlayerCommand(currentMsg.getPlayerId());
			ModelFacade.getSingleton().queueCommand(persistCommand);
			
			MapToServerMapping mapping = MapToServerMapping.retrieveMapping(currentMsg
					.getMapName());
			double pin = PlayerManager.getSingleton().getNewPinFor(playerID);
			TeleportationContinuationMessage response = new TeleportationContinuationMessage(
					mapping.getMapName(), mapping.getHostName(), mapping.getPortNumber(),
					playerID, pin);
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
