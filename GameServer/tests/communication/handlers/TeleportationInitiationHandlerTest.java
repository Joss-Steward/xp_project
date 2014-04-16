package communication.handlers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Observer;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.TeleportationInitiationMessage;
import communication.messages.TeleportationContinuationMessage;
import communication.messages.Message;
import data.Position;
import model.ModelFacade;
import model.Player;
import model.PlayerManager;
import model.PlayersInDB;
import model.QualifiedObservableConnector;
import model.ServersInDB;
import model.reports.PlayerMovedReport;

/**
 * Test the handler for GetServerInfoMessages
 * 
 * @author Merlin
 * 
 */
public class TeleportationInitiationHandlerTest
{
	/**
	 * Reset the PlayerManager
	 */
	@Before
	public void reset()
	{
		PlayerManager.resetSingleton();
		ModelFacade.resetSingleton();
	}

	/**
	 * It should correctly report the type of messages it handles
	 */
	@Test
	public void messageTypeCorrect()
	{
		TeleportationInitiationHandler handler = new TeleportationInitiationHandler();
		assertEquals(TeleportationInitiationMessage.class,
				handler.getMessageTypeWeHandle());
	}

	/**
	 * Make sure that the appropriate reponse message gets queued into the
	 * accumulator
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void generatesCorrectResponse() throws InterruptedException
	{
		PlayerManager.getSingleton().addPlayer(PlayersInDB.MERLIN.getPlayerID());
		TeleportationInitiationHandler handler = new TeleportationInitiationHandler();
		StateAccumulator accum = new StateAccumulator(null);
		handler.setAccumulator(accum);
		TeleportationInitiationMessage msg = new TeleportationInitiationMessage(
				PlayersInDB.MERLIN.getPlayerID(), ServersInDB.FIRST_SERVER.getMapName(), new Position(5, 6));
		// set up an observer who would be notified if the movement wasn't handled silently
		Observer obs = EasyMock.createMock(Observer.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, PlayerMovedReport.class);
		EasyMock.replay(obs);
		
		handler.process(msg);
		while (ModelFacade.getSingleton().queueSize() > 0)
		{
			Thread.sleep(100);
		}
		// Reset the singleton and re-add the player to make sure that the player is refreshed from the DB
		PlayerManager.resetSingleton();
		PlayerManager.getSingleton().addPlayer(PlayersInDB.MERLIN.getPlayerID());

		// make sure we moved the player without notifying observers
		Player p = PlayerManager.getSingleton().getPlayerFromID(PlayersInDB.MERLIN.getPlayerID());
		assertEquals(new Position(5, 6), p.getPlayerPosition());
		EasyMock.verify(obs);
		
		// make sure we queued the appropriate response
		ArrayList<Message> queue = accum.getPendingMsgs();
		assertEquals(1, queue.size());
		TeleportationContinuationMessage response = (TeleportationContinuationMessage) queue
				.get(0);
		assertEquals(ServersInDB.FIRST_SERVER.getMapName(), response.getMapName());
		assertEquals(ServersInDB.FIRST_SERVER.getHostName(), response.getHostName());
		assertEquals(ServersInDB.FIRST_SERVER.getPortNumber(), response.getPortNumber());
	}

}
