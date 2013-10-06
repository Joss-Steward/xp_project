package model;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Merlin
 *
 */
public class ModelFacadeTest
{
	
	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setup()
	{
		ModelFacade.resetSingleton(false);
		MapManager.resetSingleton();
		ModelFacade.getSingleton(false).setHeadless(true);
	}
	
	/**
	 * make sure it is a singleton
	 */
	@Test
	public void isResetableSingleton()
	{
		ModelFacade facade1 = ModelFacade.getSingleton(false);
		ModelFacade facade2 = ModelFacade.getSingleton(false);
		assertSame(facade1, facade2);
		ModelFacade.resetSingleton(false);
		assertNotSame(facade1, ModelFacade.getSingleton(false));
	}

}
