import model.PlayerManagerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.handlers.LoginMessageHandlerTest;
import communication.packers.LoginResponseMessagePackerTest;
/**
 * @author Merlin
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{ 
	//communication.handlers
	LoginMessageHandlerTest.class,
	
	//communication.packers
	LoginResponseMessagePackerTest.class,
	
	//model
	PlayerManagerTest.class
})

public class AllLoginServerTests
{

}

