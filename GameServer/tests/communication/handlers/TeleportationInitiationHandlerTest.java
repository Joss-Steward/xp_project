package communication.handlers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Observer;

import org.easymock.EasyMock;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.TeleportationInitiationMessage;
import communication.messages.TeleportationContinuationMessage;
import communication.messages.Message;
import data.Position;
import model.Player;
import model.PlayerManager;
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
	 */
	@Test
	public void generatesCorrectResponse()
	{
		PlayerManager.getSingleton().addPlayer(1);
		TeleportationInitiationHandler handler = new TeleportationInitiationHandler();
		StateAccumulator accum = new StateAccumulator(null);
		handler.setAccumulator(accum);
		TeleportationInitiationMessage msg = new TeleportationInitiationMessage(
				1, ServersInDB.FIRST_SERVER.getMapName(), new Position(5, 6));
		// set up an observer who would be notified if the movement wasn't handled silently
		Observer obs = EasyMock.createMock(Observer.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, PlayerMovedReport.class);
		EasyMock.replay(obs);
		
		handler.process(msg);

		// make sure we moved the player without notifying observers
		Player p = PlayerManager.getSingleton().getPlayerFromID(1);
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
