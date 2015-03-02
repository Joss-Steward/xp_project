import model.MapToServerMappingTest;
import model.PlayerLoginTest;
import model.PlayerConnectionTest;
import model.QualifiedObservableTest;
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
import communication.messages.ChatMessageTest;
import communication.messages.ConnectionMessageTest;
import communication.messages.DisconnectionMessageTest;
import communication.messages.TeleportationInitiationMessageTest;
import communication.messages.TeleportationContinuationMessageTest;
import communication.messages.LoginMessageTest;
import communication.messages.LoginResponseMessageTest;
import communication.messages.MapFileMessageTest;
import communication.messages.MessageStructureVerifier;
import communication.messages.MovementMessageTest;
import communication.messages.PlayerJoinedMessageTest;
import communication.packers.MessagePackerSetTest;
import data.PositionTest;
import datasource.PlayerConnectionRowDataGatewayMockTest;
import datasource.PlayerConnectionRowDataGatewayRDSTest;
import datasource.PlayerLoginRowDataGatewayMockTest;
import datasource.PlayerLoginRowDataGatewayRDSTest;
import datasource.ServerRowDataGatewayMockTest;
import datasource.ServerRowDataGatewayRDSTest;

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
		ChatMessageTest.class,
		ConnectionMessageTest.class,
		DisconnectionMessageTest.class,
		TeleportationContinuationMessageTest.class,
		LoginMessageTest.class,
		TeleportationContinuationMessageTest.class,
		LoginResponseMessageTest.class,
		MapFileMessageTest.class,
		MessageStructureVerifier.class, 
		MovementMessageTest.class,
		PlayerJoinedMessageTest.class,
		// StubMessage1.class,
		// StubMessage2.class,
		TeleportationInitiationMessageTest.class,
		

		// communicatiaon.packers
		MessagePackerSetTest.class,
		// StubMessagePacker1,
		// StubMessagePacker2,
		// StubMessagePacker2a,

		// data
		PositionTest.class,
		
		//datasource
		PlayerConnectionRowDataGatewayMockTest.class,
		PlayerConnectionRowDataGatewayRDSTest.class,
		//PlayerConnectionRowDataGateway,
		PlayerLoginRowDataGatewayMockTest.class,
		PlayerLoginRowDataGatewayRDSTest.class,
		//PlayerLoginDataBehaviorTest.class,
		//PlayersForTest
		ServerRowDataGatewayMockTest.class,
		ServerRowDataGatewayRDSTest.class,
		//ServerDataBehaviorTest,
		//ServersInDB,

		// model
		//Tests for Adventure and Quest Classes
		// DatabaseTest.class
		MapToServerMappingTest.class,
		PlayerLoginTest.class, 
		PlayerConnectionTest.class, 
		// PlayersInDB
		QualifiedObservableTest.class,
		// QualifiedObservableTestInherited,
		QualifiedObserverConnectorTest.class,
		// ServersInDB

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
