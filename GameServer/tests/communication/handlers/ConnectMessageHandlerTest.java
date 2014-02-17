package communication.handlers;

import static org.junit.Assert.assertEquals;
import model.Player;
import model.PlayerManager;
import model.PlayerPin;

import org.junit.Test;

import communication.ConnectionManager;
import communication.messages.ConnectMessage;

/**
 * 
 * @author merlin
 * 
 */
public class ConnectMessageHandlerTest
{

	/**
	 * The incoming message should cause creation of the player in the model and
	 * notification of the player's playerID to the state accumulator
	 */
	@Test
	public void tellsStateAccumulatorIfPlayerIDPinIsRecognized()
	{
		ConnectMessageHandler handler = new ConnectMessageHandler();
		ConnectionManager connectionManager = new ConnectionManager();
		handler.setConnectionManager(connectionManager);
		ConnectMessage msg = new ConnectMessage(1, PlayerPin.DEFAULT_PIN);
		handler.process(msg);
		assertEquals(1, connectionManager.getPlayerID());
	}

	/**
	 * The incoming message should cause creation of the player in the model and
	 * notification of the player's player ID to the state accumulator
	 */
	@Test
	public void tellsModel()
	{
		ConnectMessageHandler handler = new ConnectMessageHandler();
		ConnectMessage msg = new ConnectMessage(1, PlayerPin.DEFAULT_PIN);
		handler.process(msg);

		// if this doesn't throw a PlayerNotFoundExcetion, all is well
		PlayerManager.getSingleton().getPlayerFromID(34);
	}

}
