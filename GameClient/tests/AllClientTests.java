import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import view.player.DirectionTest;
import view.player.PlayerSpriteTest;
import view.screen.ScreenListenerTest;
import view.screen.map.ScreenMapInputTest;
import view.screen.popup.AdventureCompleteBehaviorTest;
import communication.handlers.AdventureStateChangeMessageHandlerTest;
import communication.handlers.ChatMessageHandlerTest;
import communication.handlers.ExperienceChangedMessageHandlerTest;
import communication.handlers.HighScoreResponseHandlerTest;
import communication.handlers.InitializeThisClientsPlayerMessageHandlerTest;
import communication.handlers.LoginFailedMessageHandlerTest;
import communication.handlers.MapFileMessageHandlerTest;
import communication.handlers.MovementMessageHandlerTest;
import communication.handlers.PlayerJoinedMessageHandlerTest;
import communication.handlers.PlayerLeaveMessageHandlerTest;
import communication.handlers.QuestStateChangeMessageHandlerTest;
import communication.packers.AreaCollisionMessagePackerTest;
import communication.packers.ChatMessagePackerTest;
import communication.packers.LoginMessagePackerTest;
import communication.packers.MovementMessagePackerTest;
import communication.packers.TeleportationInitiationMessagePackerTest;
import edu.ship.shipsim.client.model.ChatManagerTest;
import edu.ship.shipsim.client.model.CommandAdventureStateChangeTest;
import edu.ship.shipsim.client.model.CommandChatMessageReceivedTest;
import edu.ship.shipsim.client.model.CommandChatMessageSentTest;
import edu.ship.shipsim.client.model.CommandClearModelStateTest;
import edu.ship.shipsim.client.model.CommandHighScoreResponseTest;
import edu.ship.shipsim.client.model.CommandInitializePlayerTest;
import edu.ship.shipsim.client.model.CommandLoginFailedTest;
import edu.ship.shipsim.client.model.CommandLoginTest;
import edu.ship.shipsim.client.model.CommandMovePlayerTest;
import edu.ship.shipsim.client.model.CommandNewMapTest;
import edu.ship.shipsim.client.model.CommandOverwriteExperienceTest;
import edu.ship.shipsim.client.model.CommandOverwriteQuestStateTest;
import edu.ship.shipsim.client.model.CommandQuestStateChangeTest;
import edu.ship.shipsim.client.model.CommandRemovePlayerTest;
import edu.ship.shipsim.client.model.CommandSendQuestStateTest;
import edu.ship.shipsim.client.model.MapManagerTest;
import edu.ship.shipsim.client.model.ModelFacadeTest;
import edu.ship.shipsim.client.model.PlayerManagerTest;
import edu.ship.shipsim.client.model.PlayerTest;
import edu.ship.shipsim.client.model.ThisClientsPlayerTest;
import edu.ship.shipsim.client.model.reports.AdventureStateChangeReportTest;
import edu.ship.shipsim.client.model.reports.AdventuresNeedingNotificationReportTest;
import edu.ship.shipsim.client.model.reports.AreaCollisionReportTest;
import edu.ship.shipsim.client.model.reports.ChangeMapReportTest;
import edu.ship.shipsim.client.model.reports.ChatSentReportTest;
import edu.ship.shipsim.client.model.reports.ExperiencePointsChangeReportTest;
import edu.ship.shipsim.client.model.reports.HighScoreResponseReportTest;
import edu.ship.shipsim.client.model.reports.LoginFailedReportTest;
import edu.ship.shipsim.client.model.reports.LoginInitiatedReportTest;
import edu.ship.shipsim.client.model.reports.NewMapReportTest;
import edu.ship.shipsim.client.model.reports.PlayerMovedReportTest;
import edu.ship.shipsim.client.model.reports.QuestStateChangeReportTest;
import edu.ship.shipsim.client.model.reports.QuestStateReportTest;

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
		AdventureStateChangeMessageHandlerTest.class,
		ChatMessageHandlerTest.class,
		ExperienceChangedMessageHandlerTest.class,
		HighScoreResponseHandlerTest.class,
		InitializeThisClientsPlayerMessageHandlerTest.class,
		LoginFailedMessageHandlerTest.class,
		MapFileMessageHandlerTest.class,
		MovementMessageHandlerTest.class,
		PlayerJoinedMessageHandlerTest.class,
		PlayerLeaveMessageHandlerTest.class,
		QuestStateChangeMessageHandlerTest.class,
		

		// communication.packers
		AreaCollisionMessagePackerTest.class,
		ChatMessagePackerTest.class,
		LoginMessagePackerTest.class,
		MovementMessagePackerTest.class,
		TeleportationInitiationMessagePackerTest.class,

		// model
		ChatManagerTest.class,
		CommandAdventureStateChangeTest.class,
		CommandChatMessageReceivedTest.class,
		CommandChatMessageSentTest.class,
		CommandClearModelStateTest.class,
		CommandHighScoreResponseTest.class,
		CommandInitializePlayerTest.class, 
		CommandLoginFailedTest.class,
		CommandLoginTest.class,
		CommandMovePlayerTest.class,
		CommandNewMapTest.class, 
		CommandOverwriteExperienceTest.class,
		CommandOverwriteQuestStateTest.class,
		CommandQuestStateChangeTest.class,
		CommandRemovePlayerTest.class,
		CommandSendQuestStateTest.class,
		MapManagerTest.class, 
		ModelFacadeTest.class,
		PlayerManagerTest.class, 
		PlayerTest.class,
		ThisClientsPlayerTest.class,
		
		// model.reports
		AdventuresNeedingNotificationReportTest.class,
		AdventureStateChangeReportTest.class,
		AreaCollisionReportTest.class,
		ChangeMapReportTest.class,
		ChatSentReportTest.class,
		ExperiencePointsChangeReportTest.class,
		HighScoreResponseReportTest.class,
		LoginFailedReportTest.class,
		LoginInitiatedReportTest.class, 
		NewMapReportTest.class,
		PlayerMovedReportTest.class,
		QuestStateChangeReportTest.class,
		QuestStateReportTest.class,

		// view.player
		DirectionTest.class,
		PlayerSpriteTest.class,
		
		// view.screen
		ScreenListenerTest.class, 
		
		//view.screen.map
		ScreenMapInputTest.class, 
		
		//view.screen.popup
		AdventureCompleteBehaviorTest.class,

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
