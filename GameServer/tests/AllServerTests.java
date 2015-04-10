
import model.PlayerConnectionTest;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.handlers.ChatMessageHandlerTest;
import communication.handlers.ConnectMessageHandlerTest;
import communication.handlers.DisconnectMessageHandlerTest;
import communication.handlers.MovementMessageHandlerTest;
import communication.handlers.TeleportationInitiationHandlerTest;
import communication.packers.ChatMessagePackerTest;
import communication.packers.UpdatePlayerInformationMessagePackerTest;
import communication.packers.MapFileMessagePackerTest;
import communication.packers.MovementMessagePackerTest;
import communication.packers.PlayerJoinedMessagePackerTest;
import edu.ship.shipsim.areaserver.datasource.AdventureRecordTest;
import edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGatewayMockTest;
import edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGatewayRDSTest;
import edu.ship.shipsim.areaserver.datasource.AdventureTableDataGatewayMockTest;
import edu.ship.shipsim.areaserver.datasource.AdventureTableDataGatewayRDSTest;
import edu.ship.shipsim.areaserver.datasource.LevelTableDataGatewayMockTest;
import edu.ship.shipsim.areaserver.datasource.LevelTableDataGatewayRDSTest;
import edu.ship.shipsim.areaserver.datasource.NPCQuestionRowDataGatewayMockTest;
import edu.ship.shipsim.areaserver.datasource.NPCQuestionRowDataGatewayRDSTest;
import edu.ship.shipsim.areaserver.datasource.NPCRowDataGatewayMockTest;
import edu.ship.shipsim.areaserver.datasource.NPCRowDataGatewayRDSTest;
import edu.ship.shipsim.areaserver.datasource.PlayerRowDataGatewayMockTest;
import edu.ship.shipsim.areaserver.datasource.PlayerRowDataGatewayRDSTest;
import edu.ship.shipsim.areaserver.datasource.QuestRowDataGatewayMockTest;
import edu.ship.shipsim.areaserver.datasource.QuestRowDataGatewayRDSTest;
import edu.ship.shipsim.areaserver.datasource.QuestStateTableDataGatewayMockTest;
import edu.ship.shipsim.areaserver.datasource.QuestStateTableDataGatewayRDSTest;
import edu.ship.shipsim.areaserver.model.AdventureStateTest;
import edu.ship.shipsim.areaserver.model.ChatManagerTest;
import edu.ship.shipsim.areaserver.model.ChatMessageReceivedCommandTest;
import edu.ship.shipsim.areaserver.model.CommandAddPlayerTest;
import edu.ship.shipsim.areaserver.model.CommandMovePlayerSilentlyTest;
import edu.ship.shipsim.areaserver.model.CommandMovePlayerTest;
import edu.ship.shipsim.areaserver.model.CommandPersistPlayerTest;
import edu.ship.shipsim.areaserver.model.CommandRemovePlayerTest;
import edu.ship.shipsim.areaserver.model.LevelManagerTest;
import edu.ship.shipsim.areaserver.model.NPCMapperTest;
import edu.ship.shipsim.areaserver.model.NPCQuestionTest;
import edu.ship.shipsim.areaserver.model.NPCTest;
import edu.ship.shipsim.areaserver.model.OptionsManagerTest;
import edu.ship.shipsim.areaserver.model.PlayerManagerTest;
import edu.ship.shipsim.areaserver.model.PlayerMapperTest;
import edu.ship.shipsim.areaserver.model.PlayerTest;
import edu.ship.shipsim.areaserver.model.QuestManagerTest;
import edu.ship.shipsim.areaserver.model.QuestTest;
import edu.ship.shipsim.areaserver.model.QuestStateTest;
import edu.ship.shipsim.areaserver.model.QuizBotBehaviorTest;
import edu.ship.shipsim.areaserver.model.reports.UpdatePlayerInformationReportTest;
import edu.ship.shipsim.areaserver.model.reports.PlayerConnectionReportTest;
import edu.ship.shipsim.areaserver.model.reports.PlayerMovedReportTest;
import edu.ship.shipsim.areaserver.model.reports.SendChatMessageReportTest;

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
		ChatMessageHandlerTest.class,
		ConnectMessageHandlerTest.class,
		DisconnectMessageHandlerTest.class,
		MovementMessageHandlerTest.class,
		TeleportationInitiationHandlerTest.class,
		

		// communication.packers
		ChatMessagePackerTest.class,
		MapFileMessagePackerTest.class,
		MovementMessagePackerTest.class, 
		PlayerJoinedMessagePackerTest.class,
		UpdatePlayerInformationMessagePackerTest.class,
		
		//dataSource
		AdventureRecordTest.class,
		//AdventuresForTest.class,
		//AdventureStatesForTest.class,
		AdventureStateTableDataGatewayMockTest.class,
		AdventureStateTableDataGatewayRDSTest.class,
		//AdventureStateTableDataGatewayTest.class,
		AdventureTableDataGatewayMockTest.class,
		AdventureTableDataGatewayRDSTest.class,
		//AdventureTableDataGateway.class
		//LevelsForTest.class
		LevelTableDataGatewayMockTest.class,
		LevelTableDataGatewayRDSTest.class,
		//LevelTableDataGatewayTest.class,
		//MapAreasForTest.class
		NPCQuestionRowDataGatewayMockTest.class,
		NPCQuestionRowDataGatewayRDSTest.class,
		//NPCQuestionRowDataGatewayTest.class,
		//NPCQuestionsForTest.class,
		NPCRowDataGatewayMockTest.class,
		NPCRowDataGatewayRDSTest.class,
		//NPCRowDataGatewayTest.class,
		//NPCsForTest.class,
		PlayerRowDataGatewayMockTest.class,
		PlayerRowDataGatewayRDSTest.class,
		//PlayerRowDataGatewayTest.class,
		QuestRowDataGatewayMockTest.class,
		QuestRowDataGatewayRDSTest.class,
		//QuestRowDataGatewayTest.class,
		//QuestsForTest
		//QuestStatesForTest.class
		QuestStateTableDataGatewayMockTest.class,
		QuestStateTableDataGatewayRDSTest.class,
		//QuestStateTableDataGatewayTest.class,

		// model
		AdventureStateTest.class,
		ChatManagerTest.class,
		ChatMessageReceivedCommandTest.class,
		CommandAddPlayerTest.class, 
		CommandMovePlayerSilentlyTest.class,
		CommandMovePlayerTest.class, 
		CommandPersistPlayerTest.class,
		CommandRemovePlayerTest.class,
		LevelManagerTest.class,
		NPCMapperTest.class,
		NPCQuestionTest.class,
		NPCTest.class,
		OptionsManagerTest.class,
		PlayerManagerTest.class,
		PlayerMapperTest.class,
		PlayerTest.class,
		QuestManagerTest.class,
		QuestTest.class,
		QuestStateTest.class,
		QuizBotBehaviorTest.class,
		
		
		// model.reports
		PlayerConnectionReportTest.class, 
		PlayerMovedReportTest.class, 
		SendChatMessageReportTest.class,
		UpdatePlayerInformationReportTest.class,
		})

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
