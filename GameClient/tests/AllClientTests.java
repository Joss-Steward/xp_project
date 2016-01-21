import model.ChatManagerTest;
import model.CommandAdventureStateChangeTest;
import model.CommandChatMessageReceivedTest;
import model.CommandChatMessageSentTest;
import model.CommandClearModelStateTest;
import model.CommandHighScoreResponseTest;
import model.CommandInitializePlayerTest;
import model.CommandLoginFailedTest;
import model.CommandLoginTest;
import model.CommandMovePlayerTest;
import model.CommandNewMapTest;
import model.CommandOverwriteExperienceTest;
import model.CommandOverwriteQuestStateTest;
import model.CommandQuestStateChangeTest;
import model.CommandRemovePlayerTest;
import model.CommandSendQuestStateTest;
import model.MapManagerTest;
import model.ModelFacadeTest;
import model.PlayerManagerTest;
import model.PlayerTest;
import model.ThisClientsPlayerTest;
import model.reports.AdventureStateChangeReportTest;
import model.reports.AdventuresNeedingNotificationReportTest;
import model.reports.AreaCollisionReportTest;
import model.reports.ChangeMapReportTest;
import model.reports.ChatSentReportTest;
import model.reports.ExperiencePointsChangeReportTest;
import model.reports.HighScoreResponseReportTest;
import model.reports.LoginFailedReportTest;
import model.reports.LoginInitiatedReportTest;
import model.reports.NewMapReportTest;
import model.reports.PlayerMovedReportTest;
import model.reports.QuestStateChangeReportTest;
import model.reports.QuestStateReportTest;

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
