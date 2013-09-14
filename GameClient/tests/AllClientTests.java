import model.PlayerTest;
import model.QualifiedObservableTest;
import model.QualifiedObserverConnectorTest;

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
	
	PlayerTest.class,
	QualifiedObservableTest.class,
	QualifiedObserverConnectorTest.class
})

public class AllClientTests
{

}

