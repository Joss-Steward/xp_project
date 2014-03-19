package communication.handlers;

import static org.junit.Assert.assertEquals;
import model.ModelFacade;
import model.PlayerManager;
import model.PlayerPin;

import org.junit.Before;
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
	 * Reset the PlayerManager before each test
	 */
	@Before
	public void reset()
	{
		PlayerManager.resetSingleton();
		ModelFacade.resetSingleton();
	}

	/**
	 * The incoming message should cause creation of the player in the model and
	 * notification of the player's playerID to the state accumulator
	 * 
	 * @throws InterruptedException
	 *             Shouldn't
	 */
	@Test
	public void tellsStateAccumulatorIfPlayerIDPinIsRecognized()
			throws InterruptedException
	{
		ConnectMessageHandler handler = new ConnectMessageHandler();
		ConnectionManager connectionManager = new ConnectionManager();
		handler.setConnectionManager(connectionManager);
		ConnectMessage msg = new ConnectMessage(1, PlayerPin.DEFAULT_PIN);
		handler.process(msg);
		while (ModelFacade.getSingleton().queueSize() > 0)
		{
			Thread.sleep(100);
		}
		assertEquals(1, connectionManager.getPlayerID());
	}

	/**
	 * The incoming message should cause creation of the player in the model and
	 * notification of the player's player ID to the state accumulator
	 * 
	 * @throws InterruptedException
	 *             Shouldn't
	 */
	@Test
	public void tellsModel() throws InterruptedException
	{
		ConnectMessageHandler handler = new ConnectMessageHandler();
		ConnectMessage msg = new ConnectMessage(1, PlayerPin.DEFAULT_PIN);
		handler.process(msg);
		while (ModelFacade.getSingleton().queueSize() > 0)
		{
			Thread.sleep(100);
		}
		// if this doesn't throw a PlayerNotFoundExcetion, all is well
		PlayerManager.getSingleton().getPlayerFromID(34);
	}

}
