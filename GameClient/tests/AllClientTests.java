import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import view.DirectionTest;
import view.PlayerSpriteTest;
import view.ScreenListenerTest;
import view.ScreenMapInputTest;
import communication.handlers.ChatMessageHandlerTest;
import communication.handlers.LoginFailedMessageHandlerTest;
import communication.handlers.MapFileMessageHandlerTest;
import communication.handlers.MovementMessageHandlerTest;
import communication.handlers.PlayerJoinedMessageHandlerTest;
import communication.packers.AreaCollisionMessagePackerTest;
import communication.packers.ChatMessagePackerTest;
import communication.packers.LoginMessagePackerTest;
import communication.packers.MovementMessagePackerTest;
import communication.packers.TeleportationInitiationMessagePackerTest;
import edu.ship.shipsim.client.model.ChatManagerTest;
import edu.ship.shipsim.client.model.CommandChatMessageReceivedTest;
import edu.ship.shipsim.client.model.CommandChatMessageSentTest;
import edu.ship.shipsim.client.model.CommandInitializePlayerTest;
import edu.ship.shipsim.client.model.CommandLoginFailedTest;
import edu.ship.shipsim.client.model.CommandLoginTest;
import edu.ship.shipsim.client.model.CommandNewMapTest;
import edu.ship.shipsim.client.model.MapManagerTest;
import edu.ship.shipsim.client.model.ModelFacadeTest;
import edu.ship.shipsim.client.model.PlayerManagerTest;
import edu.ship.shipsim.client.model.PlayerTest;
import edu.ship.shipsim.client.model.ThisClientsPlayerTest;
import edu.ship.shipsim.client.model.reports.AreaCollisionReportTest;
import edu.ship.shipsim.client.model.reports.LoginFailedReportTest;
import edu.ship.shipsim.client.model.reports.LoginInitiatedReportTest;
import edu.ship.shipsim.client.model.reports.NewMapReportTest;
import edu.ship.shipsim.client.model.reports.PlayerMovedReportTest;

/**
 * All of the tests for the client code. Notice that the packages, and classes
 * within them, are in order by the way they show in the package explorer. This
 * is to make it easy to see if a class is missing from this list. Helper
 * classes that do not contain tests are included in the list, but commented out
 * 
 * @author Merlin
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{
		// communication.handlers
		MapFileMessageHandlerTest.class,
		PlayerJoinedMessageHandlerTest.class,
		MovementMessageHandlerTest.class,
		ChatMessageHandlerTest.class,
		LoginFailedMessageHandlerTest.class,

		// communication.packers
		AreaCollisionMessagePackerTest.class,
		ChatMessagePackerTest.class,
		LoginMessagePackerTest.class,
		MovementMessagePackerTest.class,
		TeleportationInitiationMessagePackerTest.class,

		// model
		CommandInitializePlayerTest.class, CommandLoginTest.class,
		CommandNewMapTest.class, MapManagerTest.class, ModelFacadeTest.class,
		PlayerManagerTest.class, PlayerTest.class,
		ThisClientsPlayerTest.class,
		ChatManagerTest.class,
		CommandChatMessageReceivedTest.class,
		CommandChatMessageSentTest.class,
		CommandLoginFailedTest.class,

		// model.reports
		AreaCollisionReportTest.class,
		LoginInitiatedReportTest.class, 
		NewMapReportTest.class,
		PlayerMovedReportTest.class,
		LoginFailedReportTest.class,

		// view
		ScreenListenerTest.class, 
		ScreenMapInputTest.class, 
		PlayerSpriteTest.class,
		DirectionTest.class,

})
/**
 * Runs all client tests
 * @author Merlin
 *
 */
public class AllClientTests
{
	// empty block
}
