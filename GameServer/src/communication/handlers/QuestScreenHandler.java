package communication.handlers;

import communication.messages.Message;
import communication.messages.QuestScreenMessage;
import edu.ship.shipsim.areaserver.model.Player;
import edu.ship.shipsim.areaserver.model.PlayerManager;

/**
 * 
 * @author Joshua
 * 
 */
public class QuestScreenHandler extends MessageHandler
{

	/**
	 * Add this user to the player list
	 * 
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(QuestScreenMessage.class))
		{
			QuestScreenMessage cMsg = (QuestScreenMessage) msg;
			cMsg.isLoadState();
			System.out.println("hello from server quest screen message handler");

			// Tell someone in the client
			Player player = PlayerManager.getSingleton().getPlayerFromID(
					cMsg.getPlayerID());

			if (player != null)
			{
				player.notifyObservers(msg);
			}

		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return QuestScreenMessage.class;
	}

}
