import model.PlayerLoginTest;
import model.PlayerManagerTest;
import model.reports.LoginSuccessfulReportTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.handlers.LoginMessageHandlerTest;
import communication.packers.LoginFailedMessagePackerTest;
import communication.packers.LoginSuccessfulMessagePackerTest;

/**
 * @author Merlin
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{
// communication.handlers
		LoginMessageHandlerTest.class,

		// communication.packers
		LoginFailedMessagePackerTest.class, LoginSuccessfulMessagePackerTest.class,

		// model
		PlayerLoginTest.class, PlayerManagerTest.class,

		// model.reports
		LoginSuccessfulReportTest.class, })
public class AllLoginServerTests
{

}
