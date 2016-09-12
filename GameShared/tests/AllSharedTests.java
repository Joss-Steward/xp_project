import model.ClientPlayerAdventureTest;
import model.ClientPlayerQuestTest;
import model.MapToServerMappingTest;
import model.PlayerConnectionTest;
import model.PlayerLoginTest;
import model.QualifiedObserverConnectorTest;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tmxfiles.TMXMapReaderTest;
import communication.ConnectionIncomingTest;
import communication.ConnectionManagerTest;
import communication.StateAccumulatorTest;
import communication.handlers.MessageHandlerSetTest;
import communication.messages.AdventureNotificationCompleteMessageTest;
import communication.messages.AdventureStateChangeMessageTest;
import communication.messages.AreaCollisionMessageTest;
import communication.messages.ChatMessageTest;
import communication.messages.ConnectionMessageTest;
import communication.messages.DisconnectionMessageTest;
import communication.messages.ExperienceChangedMessageTest;
import communication.messages.InitializeThisClientsPlayerMessageTest;
import communication.messages.KeyInputMessageTest;
import communication.messages.KnowledgeChangedMessageTest;
import communication.messages.LoginMessageTest;
import communication.messages.LoginResponseMessageTest;
import communication.messages.MapFileMessageTest;
import communication.messages.MessageStructureVerifier;
import communication.messages.MovementMessageTest;
import communication.messages.PlayerJoinedMessageTest;
import communication.messages.PlayerLeaveMessageTest;
import communication.messages.TeleportationContinuationMessageTest;
import communication.messages.TeleportationInitiationMessageTest;
import communication.packers.MessagePackerSetTest;
import datasource.LevelRecordTest;
import datasource.PlayerConnectionRowDataGatewayMockTest;
import datasource.PlayerConnectionRowDataGatewayRDSTest;
import datasource.PlayerLoginRowDataGatewayMockTest;
import datasource.PlayerLoginRowDataGatewayRDSTest;
import datasource.PlayerScoreRecordTest;
import datasource.ServerRowDataGatewayMockTest;
import datasource.ServerRowDataGatewayRDSTest;
import datatypes.PositionTest;

/**
 * All of the tests for the shared code. Notice that the packages, and classes
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
		// communication
		ConnectionIncomingTest.class,
		ConnectionManagerTest.class,
		StateAccumulatorTest.class,
		
		// communication.handlers
		MessageHandlerSetTest.class,
		// StubMessageHandler1.class,
		// StubMessageHandler2.class,

		// communication.messages
		KnowledgeChangedMessageTest.class,
		AdventureNotificationCompleteMessageTest.class,
		AdventureStateChangeMessageTest.class,
		AreaCollisionMessageTest.class,
		ChatMessageTest.class,
		ConnectionMessageTest.class,
		DisconnectionMessageTest.class,
		ExperienceChangedMessageTest.class,
		InitializeThisClientsPlayerMessageTest.class,
		KeyInputMessageTest.class,
		LoginMessageTest.class,
		LoginResponseMessageTest.class,
		MapFileMessageTest.class,
		MessageStructureVerifier.class, 
		MovementMessageTest.class,
		PlayerJoinedMessageTest.class,
		PlayerLeaveMessageTest.class,
		// StubMessage1.class,
		// StubMessage2.class,
		TeleportationContinuationMessageTest.class,
		TeleportationInitiationMessageTest.class,
		

		// communicatiaon.packers
		MessagePackerSetTest.class,
		// StubMessagePacker1,
		// StubMessagePacker2,
		// StubMessagePacker2a,

		// data
		PositionTest.class,
		
		//datasource
		//DatabaseTest.class,
		LevelRecordTest.class,
		PlayerConnectionRowDataGatewayMockTest.class,
		PlayerConnectionRowDataGatewayRDSTest.class,
		//PlayerConnectionRowDataGateway,
		PlayerLoginRowDataGatewayMockTest.class,
		PlayerLoginRowDataGatewayRDSTest.class,
		//PlayerLoginDataBehaviorTest.class,
		PlayerScoreRecordTest.class,
		//PlayersForTest
		ServerRowDataGatewayMockTest.class,
		ServerRowDataGatewayRDSTest.class,
		//ServerRowDataGatewayTest.class,
		//ServersForTest

		// model
		ClientPlayerAdventureTest.class,
		ClientPlayerQuestTest.class,
		MapToServerMappingTest.class,
		PlayerConnectionTest.class, 
		PlayerLoginTest.class, 
		QualifiedObserverConnectorTest.class,

		// model.reports
		// StubQualifiedObservableReport1.class,
		// StubQualifiedObservableReport2.class

		// tmxfiles
		TMXMapReaderTest.class, })
public class AllSharedTests
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
