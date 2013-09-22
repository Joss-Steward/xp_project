import model.PlayerManagerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.MovementMessageHandlerTest;
import communication.StateAccumulatorConnectorServerTest;

/**
 * @author Merlin
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{ 
	MovementMessageHandlerTest.class,
	StateAccumulatorConnectorServerTest.class,
	
	// model
	PlayerManagerTest.class
})

public class AllServerTests
{

}

