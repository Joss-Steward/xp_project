package sequenceTests;

import java.io.IOException;

public class SequenceTestRunner
{

	public enum ServerType
	{
		THIS_PLAYER_CLIENT, OTHER_CLIENT, LOGIN_SERVER, AREA_SERVER, MANAGER
	}
	private ServerType sType;
	
	public SequenceTestRunner(SequenceTest test)
	{
		this.sType = sType;
	}
	
	public static void main(String[] args) throws IOException
	{
		SequenceTestRunner runner = new SequenceTestRunner(new LoginSequenceTest());
		System.out.println(runner.run(THIS_PLAYER_CLIENT));
	}
}
