package model;

import static org.junit.Assert.assertSame;

import org.easymock.EasyMock;
import org.junit.Test;

/**
 * @author merlin
 *
 */
public class CommandExecutorTest
{
	
	/**
	 * 
	 */
	@Test
	public void isSingleton()
	{
		CommandExecutor e = CommandExecutor.getSingleton();
		assertSame(e, CommandExecutor.getSingleton());
	}
	
	/**
	 * 
	 */
	@Test
	public void singleExecution()
	{
		Command c = EasyMock.createMock(Command.class);
		EasyMock.expect(c.execute()).andReturn(true);
		EasyMock.replay(c);
		
		CommandExecutor.getSingleton().executeCommand(c);
		EasyMock.verify(c);
	}

}

