package communication.handlers;

import communication.handlers.MessageHandler;
import communication.messages.ConnectMessage;
import communication.messages.Message;
import communication.messages.PlayerJoinedMessage;
import edu.ship.shipsim.areaserver.model.CommandAddPlayer;
import edu.ship.shipsim.areaserver.model.ModelFacade;
import edu.ship.shipsim.areaserver.model.Player;
import edu.ship.shipsim.areaserver.model.PlayerManager;

/**
 * Handles a message that the player is connecting to this area server
 * 
 * @author merlin
 * 
 */
public class ConnectMessageHandler extends MessageHandler
{

	/**
	 * Add this player to the player list
	 * 
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(ConnectMessage.class))
		{
			ConnectMessage cMsg = (ConnectMessage) msg;
			if (connectionManager != null)
			{
				System.err.println("setting player");
				connectionManager.setPlayerID(cMsg.getPlayerID());
			}
			CommandAddPlayer cmd = new CommandAddPlayer(cMsg.getPlayerID(), cMsg.getPin());
			
			ModelFacade.getSingleton().queueCommand(cmd);
			
			for (Player p : PlayerManager.getSingleton().getConnectedPlayers())
			{
				PlayerJoinedMessage pMsg = new PlayerJoinedMessage(
						p.getID(), p.getPlayerName(), p.getAppearanceType(), p.getPlayerPosition());
				this.getStateAccumulator().queueMessage(pMsg);
			}
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return ConnectMessage.class;
	}

}
