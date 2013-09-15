import model.CommandLoginTest;
import model.PlayerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.LoginMessagePackerTest;
import communication.StateAccumulatorConnectorClientTest;


/**
 * @author Merlin
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{ 
	LoginMessagePackerTest.class,
	StateAccumulatorConnectorClientTest.class,
	
	CommandLoginTest.class,
	PlayerTest.class,
	
})

public class AllClientTests
{

}

