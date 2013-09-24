import model.CommandLoginTest;
import model.PlayerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.LoginMessagePackerTest;


/**
 * @author Merlin
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{ 
	LoginMessagePackerTest.class,
	
	CommandLoginTest.class,
	PlayerTest.class,
	
})

public class AllClientTests
{

}

