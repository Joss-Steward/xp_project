
import model.AdventureStateTest;
import model.ChatManagerTest;
import model.ChatMessageReceivedCommandTest;
import model.CommandAddPlayerTest;
import model.CommandAdventureNotificationCompleteTest;
import model.CommandKeyInputMessageReceivedTest;
import model.CommandMovePlayerSilentlyTest;
import model.CommandMovePlayerTest;
import model.CommandPersistPlayerTest;
import model.CommandRemovePlayerTest;
import model.LevelManagerTest;
import model.NPCMapperTest;
import model.NPCQuestionTest;
import model.NPCTest;
import model.OptionsManagerTest;
import model.PlayerConnectionTest;
import model.PlayerManagerTest;
import model.PlayerMapperTest;
import model.PlayerTest;
import model.QuestManagerTest;
import model.QuestStateTest;
import model.QuestTest;
import model.QuizBotBehaviorTest;
import model.reports.AdventureStateChangeReportTest;
import model.reports.ExperienceChangedReportTest;
import model.reports.KeyInputRecievedReportTest;
import model.reports.KnowledgeChangeReportTest;
import model.reports.PlayerConnectionReportTest;
import model.reports.PlayerMovedReportTest;
import model.reports.QuestStateChangeReportTest;
import model.reports.SendChatMessageReportTest;
import model.reports.TeleportOnQuestCompletionReportTest;
import model.reports.UpdatePlayerInformationReportTest;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.handlers.AdventureNotificationCompleteMessageHandlerTest;
import communication.handlers.ChatMessageHandlerTest;
import communication.handlers.DisconnectMessageHandlerTest;
import communication.handlers.HighScoreRequestMessageHandlerTest;
import communication.handlers.KeyInputMessageHandlerTest;
import communication.handlers.MovementMessageHandlerTest;
import communication.handlers.TeleportationInitiationHandlerTest;
import communication.packers.AdventureStateChangeMessagePackerTest;
import communication.packers.ChatMessagePackerTest;
import communication.packers.ExperienceChangedMessagePackerTest;
import communication.packers.KnowledgeChangedMessagePackerTest;
import communication.packers.MapFileMessagePackerTest;
import communication.packers.MovementMessagePackerTest;
import communication.packers.PlayerJoinedMessagePackerTest;
import communication.packers.QuestStateChangeMessagePackerTest;
import communication.packers.TeleportOnQuestCompletionPackerTest;
import communication.packers.UpdatePlayerInformationMessagePackerTest;
import datasource.AdventureRecordTest;
import datasource.AdventureStateTableDataGatewayMockTest;
import datasource.AdventureStateTableDataGatewayRDSTest;
import datasource.AdventureTableDataGatewayMockTest;
import datasource.AdventureTableDataGatewayRDSTest;
import datasource.LevelTableDataGatewayMockTest;
import datasource.LevelTableDataGatewayRDSTest;
import datasource.NPCQuestionRowDataGatewayMockTest;
import datasource.NPCQuestionRowDataGatewayRDSTest;
import datasource.NPCRowDataGatewayMockTest;
import datasource.NPCRowDataGatewayRDSTest;
import datasource.PlayerRowDataGatewayMockTest;
import datasource.PlayerRowDataGatewayRDSTest;
import datasource.QuestRowDataGatewayMockTest;
import datasource.QuestRowDataGatewayRDSTest;
import datasource.QuestStateTableDataGatewayMockTest;
import datasource.QuestStateTableDataGatewayRDSTest;

/**
 * All of the tests for the area servers code. Notice that the packages, and
 * classes within them, are in order by the way they show in the package
 * explorer. This is to make it easy to see if a class is missing from this
 * list. Helper classes that do not contain tests are included in the list, but
 * commented out
 * 
 * @author Merlin
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{
		// communication.handlers
		AdventureNotificationCompleteMessageHandlerTest.class, ChatMessageHandlerTest.class,
		// ConnectMessageHandlerTest.class,
		DisconnectMessageHandlerTest.class, HighScoreRequestMessageHandlerTest.class, KeyInputMessageHandlerTest.class,
		MovementMessageHandlerTest.class, TeleportationInitiationHandlerTest.class,

		// communication.packers
		AdventureStateChangeMessagePackerTest.class, ChatMessagePackerTest.class,
		ExperienceChangedMessagePackerTest.class, MapFileMessagePackerTest.class, MovementMessagePackerTest.class,
		PlayerJoinedMessagePackerTest.class, QuestStateChangeMessagePackerTest.class,
		TeleportOnQuestCompletionPackerTest.class, UpdatePlayerInformationMessagePackerTest.class,
		KnowledgeChangedMessagePackerTest.class,

		// dataSource
		AdventureRecordTest.class,
		// AdventuresForTest.class,
		// AdventureStatesForTest.class,
		AdventureStateTableDataGatewayMockTest.class, AdventureStateTableDataGatewayRDSTest.class,
		// AdventureStateTableDataGatewayTest.class,
		AdventureTableDataGatewayMockTest.class, AdventureTableDataGatewayRDSTest.class,
		// AdventureTableDataGateway.class
		// LevelsForTest.class
		LevelTableDataGatewayMockTest.class, LevelTableDataGatewayRDSTest.class,
		// LevelTableDataGatewayTest.class,
		// MapAreasForTest.class
		NPCQuestionRowDataGatewayMockTest.class, NPCQuestionRowDataGatewayRDSTest.class,
		// NPCQuestionRowDataGatewayTest.class,
		// NPCQuestionsForTest.class,
		NPCRowDataGatewayMockTest.class, NPCRowDataGatewayRDSTest.class,
		// NPCRowDataGatewayTest.class,
		// NPCsForTest.class,
		PlayerRowDataGatewayMockTest.class, PlayerRowDataGatewayRDSTest.class,
		// PlayerRowDataGatewayTest.class,
		QuestRowDataGatewayMockTest.class, QuestRowDataGatewayRDSTest.class,
		// QuestRowDataGatewayTest.class,
		// QuestsForTest
		// QuestStatesForTest.class
		QuestStateTableDataGatewayMockTest.class, QuestStateTableDataGatewayRDSTest.class,
		// QuestStateTableDataGatewayTest.class,

		// model
		AdventureStateTest.class, ChatManagerTest.class, ChatMessageReceivedCommandTest.class,
		CommandAddPlayerTest.class, CommandAdventureNotificationCompleteTest.class,
		CommandKeyInputMessageReceivedTest.class, CommandMovePlayerSilentlyTest.class, CommandMovePlayerTest.class,
		CommandPersistPlayerTest.class, CommandRemovePlayerTest.class, LevelManagerTest.class, NPCMapperTest.class,
		NPCQuestionTest.class, NPCTest.class, OptionsManagerTest.class, PlayerManagerTest.class, PlayerMapperTest.class,
		PlayerTest.class, QuestManagerTest.class, QuestStateTest.class, QuestTest.class, QuizBotBehaviorTest.class,

		// model.reports
		AdventureStateChangeReportTest.class, ExperienceChangedReportTest.class, KeyInputRecievedReportTest.class,
		PlayerConnectionReportTest.class, PlayerMovedReportTest.class, QuestStateChangeReportTest.class,
		SendChatMessageReportTest.class, TeleportOnQuestCompletionReportTest.class,
		UpdatePlayerInformationReportTest.class, KnowledgeChangeReportTest.class, })

public class AllServerTests
{
	/**
	 * Make sure we default all of the PINs at the beginning of running the
	 * tests so that none will be expired
	 */
	@ClassRule
	public static ExternalResource testRule = new ExternalResource()
	{
		@Override
		protected void before() throws Throwable
		{
			PlayerConnectionTest.defaultAllPins();
		};

		@Override
		protected void after()
		{

		};
	};
}
