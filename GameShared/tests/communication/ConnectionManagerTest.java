package communication;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Merlin
 *
 */
public class ConnectionManagerTest
{

	/**
	 * Make sure it is a resetable singleton
	 */
	@Test
	public void isSingleton()
	{
		ConnectionManager cm1 =  ConnectionManager.getSingleton();
		ConnectionManager cm2 =  ConnectionManager.getSingleton();
		assertSame(cm1, cm2);
		ConnectionManager.resetSingleton();
		cm2 = ConnectionManager.getSingleton();
		assertNotSame(cm1, cm2);
	}

}
