
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
import communication.packers.MapFileMessagePackerTest;
import communication.packers.MovementMessagePackerTest;
import communication.packers.PlayerJoinedMessagePackerTest;
import edu.ship.shipsim.areaserver.model.CharacterIDGeneratorTest;
import edu.ship.shipsim.areaserver.model.ChatManagerTest;
import edu.ship.shipsim.areaserver.model.ChatMessageReceivedCommandTest;
import edu.ship.shipsim.areaserver.model.CommandAddPlayerTest;
import edu.ship.shipsim.areaserver.model.CommandMovePlayerSilentlyTest;
import edu.ship.shipsim.areaserver.model.CommandMovePlayerTest;
import edu.ship.shipsim.areaserver.model.PlayerManagerTest;
import edu.ship.shipsim.areaserver.model.PlayerTest;
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
		ConnectMessageHandlerTest.class,
		DisconnectMessageHandlerTest.class,
		MovementMessageHandlerTest.class,
		TeleportationInitiationHandlerTest.class,
		ChatMessageHandlerTest.class,

		// communication.packers
		MapFileMessagePackerTest.class,
		MovementMessagePackerTest.class, 
		PlayerJoinedMessagePackerTest.class,

		// model
		CommandAddPlayerTest.class, 
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
			PlayerConnectionTest.defaultAllPins();
		};

		@Override
		protected void after()
		{

		};
	};
}
