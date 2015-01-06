package datasource;

/**
 * Test our mock behavior
 * @author Merlin
 *
 */
public class ServerDataBehaviorMockTest extends ServerDataBehaviorTest
{

	ServerDataBehavior createBehavior()
	{
		return new ServerDataBehaviorMock();
	}
}
