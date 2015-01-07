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
		ServerDataBehaviorMock x = new ServerDataBehaviorMock();
		return x;
	}
}
