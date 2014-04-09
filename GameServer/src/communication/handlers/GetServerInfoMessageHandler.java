package communication.handlers;

import java.sql.SQLException;

import model.MapToServerMapping;
import communication.messages.GetServerInfoMessage;
import communication.messages.GetServerInfoResponseMessage;
import communication.messages.Message;

/**
 * Process GetServerInfoMessages by gathering the requested data and sending it back to the requestor
 * @author Merlin
 *
 */
public class GetServerInfoMessageHandler extends MessageHandler
{

	/**
	 * This handler should retrieve the information requested and send it back
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		GetServerInfoMessage currentMsg = (GetServerInfoMessage)msg;
		try
		{
			MapToServerMapping mapping = MapToServerMapping.retrieveMapping(currentMsg.getMapName());
			GetServerInfoResponseMessage response = new GetServerInfoResponseMessage(mapping.getMapName(), mapping.getHostName(), mapping.getPortNumber());
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
		return GetServerInfoMessage.class;
	}

}
