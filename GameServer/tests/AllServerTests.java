import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.MovementMessageHandlerTest;
import communication.MovementNotifierTest;
/**
 * @author Merlin
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{ 
	MovementMessageHandlerTest.class,
	MovementNotifierTest.class
})

public class AllServerTests
{

}

