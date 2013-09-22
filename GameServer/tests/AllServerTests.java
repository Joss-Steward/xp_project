import model.PlayerManagerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.LoginMessageHandlerTest;
import communication.LoginResponseMessagePackerTest;
import communication.MovementMessageHandlerTest;
import communication.StateAccumulatorConnectorServerTest;

/**
 * @author Merlin
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{ 
	LoginMessageHandlerTest.class,
	LoginResponseMessagePackerTest.class,
	MovementMessageHandlerTest.class,
	StateAccumulatorConnectorServerTest.class,
	
	// model
	PlayerManagerTest.class
})

public class AllServerTests
{

}

