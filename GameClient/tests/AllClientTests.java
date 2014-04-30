import model.ChatManagerTest;
import model.CommandChatMessageReceivedTest;
import model.CommandChatMessageSentTest;
import model.CommandInitializePlayerTest;
import model.CommandLoginFailedTest;
import model.CommandLoginTest;
import model.CommandNewMapTest;
import model.MapManagerTest;
import model.ModelFacadeTest;
import model.PlayerManagerTest;
import model.PlayerTest;
import model.ThisClientsPlayerTest;
import model.reports.LoginFailedReportTest;
import model.reports.LoginInitiatedReportTest;
import model.reports.NewMapReportTest;
import model.reports.PlayerMovedReportTest;

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
import communication.packers.LoginMessagePackerTest;
import communication.packers.MovementMessagePackerTest;
import communication.packers.TeleportationInitiationMessagePackerTest;

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
		LoginInitiatedReportTest.class, NewMapReportTest.class,
		PlayerMovedReportTest.class,
		LoginFailedReportTest.class,

		// view
		ScreenListenerTest.class, ScreenMapInputTest.class, PlayerSpriteTest.class,
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
