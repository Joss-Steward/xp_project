import model.CommandExecutorTest;
import model.QualifiedObservableTest;
import model.QualifiedObserverConnectorTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.ConnectionIncomingTest;
import communication.ConnectionManagerTest;
import communication.MessageHandlerSetTest;
import communication.MessagePackerSetTest;
import communication.StateAccumulatorTest;
import communication.messages.LoginMessageTest;
import communication.messages.LoginResponseMessageTest;
import communication.messages.MovementMessageTest;
import data.PositionTest;
/**
 * @author Merlin
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{ 
	// communication
	ConnectionIncomingTest.class,
	ConnectionManagerTest.class,
	MessageHandlerSetTest.class,
	MessagePackerSetTest.class,
	StateAccumulatorTest.class,
	
	//communication.messages
	LoginMessageTest.class,
	LoginResponseMessageTest.class,
	MovementMessageTest.class,
	
	// data
	PositionTest.class,
	
	//model
	CommandExecutorTest.class,
	QualifiedObservableTest.class,
	QualifiedObserverConnectorTest.class
})

public class AllSharedTests
{

}

