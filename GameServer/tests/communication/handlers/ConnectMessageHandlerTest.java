package communication.handlers;

import static org.junit.Assert.assertEquals;
import model.PlayerManager;

import org.junit.Test;

import communication.StateAccumulator;
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
	 * notification of the player's userID to the state accumulator
	 */
	@Test
	public void tellsStateAccumulatorIfUserIDPinIsRecognized()
	{
		ConnectMessageHandler handler = new ConnectMessageHandler();
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		handler.setStateAccumulator(stateAccumulator);
		ConnectMessage msg = new ConnectMessage(34, 42);
		handler.process(msg);
		assertEquals(34, stateAccumulator.getPlayerID());
	}

	/**
	 * The incoming message should cause creation of the player in the model and
	 * notification of the player's userID to the state accumulator
	 */
	@Test
	public void tellsModel()
	{
		ConnectMessageHandler handler = new ConnectMessageHandler();
		ConnectMessage msg = new ConnectMessage(34, 42);
		handler.process(msg);

		// if this doesn't throw a PlayerNotFoundExcetion, all is well
		PlayerManager.getSingleton().getPlayerFromID(34);
	}

}
