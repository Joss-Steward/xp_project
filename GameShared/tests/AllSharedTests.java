import model.CommandExecutorTest;
import model.QualifiedObservableTest;
import model.QualifiedObserverConnectorTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.ConnectionIncomingTest;
import communication.ConnectionManagerTest;
import communication.MessageHandlerSetTest;
import communication.StateAccumulatorTest;
import communication.messages.ConnectionMessageTest;
import communication.messages.LoginMessageTest;
import communication.messages.LoginResponseMessageTest;
import communication.messages.MovementMessageTest;
import communication.messages.MessageStructureVerifier;
import communication.messages.PlayerJoinedMessageTest;
import communication.packers.MessagePackerSetTest;
import data.PositionTest;

/**
 * All of the tests for the shared code. Notice that the packages, and classes
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
		// communication
		ConnectionIncomingTest.class, ConnectionManagerTest.class, MessageHandlerSetTest.class,
		MessagePackerSetTest.class,
		StateAccumulatorTest.class,

		// communication.messages
		ConnectionMessageTest.class, LoginMessageTest.class, LoginResponseMessageTest.class,
		MessageStructureVerifier.class,
		MovementMessageTest.class,
		PlayerJoinedMessageTest.class,

		// communicatiaon.packers
		MessagePackerSetTest.class,
		// StubMessagePacker1.class,
		// StubMessagePacker2.class,

		// data
		PositionTest.class,

		// model
		CommandExecutorTest.class, QualifiedObservableTest.class,
		QualifiedObserverConnectorTest.class,

		// model.reports
		// StubQualifiedObservableReport1.class,
		// StubQualifiedObservableReport2.class

})
public class AllSharedTests
{

}
