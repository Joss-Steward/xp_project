import model.PlayerManagerTest;
import model.PlayerTest;
import model.reports.PlayerConnectionReportTest;
import model.reports.PlayerMovedReportTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.handlers.ConnectMessageHandlerTest;
import communication.handlers.MovementMessageHandlerTest;
import communication.packers.MapFileMessagePackerTest;
import communication.packers.PlayerJoinedMessagePackerTest;

/**
 * All of the tests for the area servers code. Notice that the packages, and classes
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
	//communication.handlers
	ConnectMessageHandlerTest.class,
	MovementMessageHandlerTest.class,
	
	//communication.packers
	MapFileMessagePackerTest.class,
	PlayerJoinedMessagePackerTest.class,
	
	// model
	PlayerManagerTest.class,
	PlayerTest.class,
	
	// model.reports
	PlayerConnectionReportTest.class,
	PlayerMovedReportTest.class,
})

public class AllServerTests
{

}

