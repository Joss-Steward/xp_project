package model;

import static org.junit.Assert.assertEquals;
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
		ModelFacade.resetSingleton();
		MapManager.resetSingleton();
		ModelFacade.getSingleton().setHeadless(true);
	}
	
	/**
	 * make sure it is a singleton
	 */
	@Test
	public void isResetableSingleton()
	{
		ModelFacade facade1 = ModelFacade.getSingleton();
		ModelFacade facade2 = ModelFacade.getSingleton();
		assertSame(facade1, facade2);
		ModelFacade.resetSingleton();
		assertNotSame(facade1, ModelFacade.getSingleton());
	}
	
	/**
	 * Make sure we can tell it to change the file the model is using
	 */
	@Test
	public void canSetMapTitle()
	{
		ModelFacade.getSingleton().setMapFile("maps/current.tmx");
		MapManager map = MapManager.getSingleton();
		assertEquals("maps/current.tmx", map.getMapFileTitle());
		
	}

}
