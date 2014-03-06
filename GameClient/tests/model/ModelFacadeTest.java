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
		ModelFacade.resetSingleton(true, true);
		MapManager.resetSingleton();
	}
	
	/**
	 * make sure it is a singleton
	 */
	@Test
	public void isResetableSingleton()
	{
		ModelFacade facade1 = ModelFacade.getSingleton(true, true);
		ModelFacade facade2 = ModelFacade.getSingleton(true, true);
		assertSame(facade1, facade2);
		ModelFacade.resetSingleton(true, true);
		assertNotSame(facade1, ModelFacade.getSingleton(true, true));
	}

}
