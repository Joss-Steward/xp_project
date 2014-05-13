import model.CommandAddPlayerTest;
import model.CharacterIDGeneratorTest;
import model.ChatManagerTest;
import model.ChatMessageReceivedCommandTest;
import model.MapToServerMappingTest;
import model.CommandMovePlayerTest;
import model.CommandMovePlayerSilentlyTest;
import model.PlayerManagerTest;
import model.PlayerPinTest;
import model.PlayerTest;
import model.reports.PlayerConnectionReportTest;
import model.reports.PlayerMovedReportTest;
import model.reports.SendChatMessageReportTest;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.handlers.ChatMessageHandlerTest;
import communication.handlers.ConnectMessageHandlerTest;
import communication.handlers.MovementMessageHandlerTest;
import communication.handlers.TeleportationInitiationHandlerTest;
import communication.packers.MapFileMessagePackerTest;
import communication.packers.MovementMessagePackerTest;
import communication.packers.PlayerJoinedMessagePackerTest;

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
		ConnectMessageHandlerTest.class,
		MovementMessageHandlerTest.class,
		TeleportationInitiationHandlerTest.class,
		ChatMessageHandlerTest.class,

		// communication.packers
		MapFileMessagePackerTest.class,
		MovementMessagePackerTest.class, 
		PlayerJoinedMessagePackerTest.class,

		// model
		CommandAddPlayerTest.class, 
		MapToServerMappingTest.class,
		CommandMovePlayerTest.class, 
		CommandMovePlayerSilentlyTest.class,
		PlayerManagerTest.class,
		PlayerTest.class,
		ChatManagerTest.class,
		ChatMessageReceivedCommandTest.class,
		CharacterIDGeneratorTest.class,
		
		// model.reports
		PlayerConnectionReportTest.class, 
		PlayerMovedReportTest.class, 
		SendChatMessageReportTest.class,
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
			PlayerPinTest.defaultAllPins();
		};

		@Override
		protected void after()
		{

		};
	};
}
