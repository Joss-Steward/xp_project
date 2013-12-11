import model.CommandLoginTest;
import model.CommandNewMapTest;
import model.MapManagerTest;
import model.ModelFacadeTest;
import model.PlayerTest;
import model.reports.LoginInitiatedReportTest;
import model.reports.NewMapReportTest;
import model.reports.ThisPlayerMovedReportTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import view.ScreenListenerTest;
import communication.handlers.MapFileMessageHandlerTest;
import communication.packers.LoginMessagePackerTest;

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
@Suite.SuiteClasses({
		// communication.handlers
		MapFileMessageHandlerTest.class,

		// communication.packers
		LoginMessagePackerTest.class,

		// model
		CommandLoginTest.class, CommandNewMapTest.class, MapManagerTest.class,
		ModelFacadeTest.class, PlayerTest.class,

		// model.reports
		LoginInitiatedReportTest.class, NewMapReportTest.class,
		ThisPlayerMovedReportTest.class,

		// view
		ScreenListenerTest.class,

})
/**
 * Runs all client tests
 * @author Merlin
 *
 */
public class AllClientTests {
	// empty block
}
