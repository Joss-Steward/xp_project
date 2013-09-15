import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.LoginMessageHandlerTest;
import communication.MovementMessageHandlerTest;

/**
 * @author Merlin
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{ 
	LoginMessageHandlerTest.class,
	MovementMessageHandlerTest.class,
})

public class AllServerTests
{

}

