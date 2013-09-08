package communication;
import java.util.Observer;

import org.easymock.EasyMock;
import org.junit.Test;

import communication.MovementMessageHandler;
import communication.MovementNotifier;
import communication.messages.MovementMessage;
import data.Position;

/**
 * 
 * @author merlin
 *
 */
public class MovementMessageHandlerTest
{

	/**
	 * All incoming MovementMessages should be reported to the MovementNotifier.  We check this by
	 * making sure that the MovementNotifier's observers get notified.
	 */
	@Test
	public void tellsMovementNotifier()
	{
		Observer obs = EasyMock.createMock(Observer.class);
		MovementNotifier.getSingleton().addObserver(obs);
		obs.update(EasyMock.anyObject(MovementNotifier.class), EasyMock.anyObject(MovementMessage.class));
		EasyMock.replay(obs);
		
		MovementMessageHandler handler = new MovementMessageHandler();
		MovementMessage msg = new MovementMessage(45, new Position(32,42));
		handler.process(msg);
		EasyMock.verify(obs);
	}

}
