import model.PlayerManagerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.handlers.MovementMessageHandlerTest;

/**
 * All of the tests for the area servers code. Notice that the packages, and classes
 * within them, are in order by the way they show in the package explorer. This
 * is to make it easy to see if a class is missing from this list. Helper
 * classes that do not contain tests are included in the list, but commented out
 * 
 * @author Merlin
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{ 
	//communication.handlers
	MovementMessageHandlerTest.class,
	
	// model
	PlayerManagerTest.class
})

public class AllServerTests
{

}

