package communication;
import static org.junit.Assert.fail;

import org.junit.Test;

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
		fail("it should tell somethign in the engine");
//		Observer obs = EasyMock.createMock(Observer.class);
//		MovementNotifier.getSingleton().addObserver(obs);
//		obs.update(EasyMock.isA(MovementNotifier.class), EasyMock.isA(MovementMessage.class));
//		EasyMock.replay(obs);
//		
//		MovementMessageHandler handler = new MovementMessageHandler();
//		MovementMessage msg = new MovementMessage(45, new Position(32,42));
//		handler.process(msg);
//		EasyMock.verify(obs);
	}

}
