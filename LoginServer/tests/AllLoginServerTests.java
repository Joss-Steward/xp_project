import model.PlayerManagerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.LoginMessageHandlerTest;
import communication.LoginResponseMessagePackerTest;
/**
 * @author Merlin
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{ 
	//communication
	LoginMessageHandlerTest.class,
	LoginResponseMessagePackerTest.class,
	
	//model
	PlayerManagerTest.class
})

public class AllLoginServerTests
{

}

