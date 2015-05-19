package edu.ship.shipsim.client.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ship.shipsim.client.model.MapManager;
import edu.ship.shipsim.client.model.ModelFacade;

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
		ModelFacade.resetSingleton();
		assertNotSame(facade1, ModelFacade.getSingleton(true, true));
	}

	/**
	 * Make sure that we get an exception if we ask for a facade in a different
	 * mode than the current one without resetting it first
	 */
	@Test
	public void cantChangeModes()
	{
		boolean sawException = false;
		ModelFacade.getSingleton(true, true);
		try
		{
			ModelFacade.getSingleton(true, false);
		} catch (IllegalArgumentException e)
		{
			sawException = true;
		}
		assertTrue(sawException);

		ModelFacade.resetSingleton();
		ModelFacade.getSingleton(true, true);

		try
		{
			ModelFacade.getSingleton(false, true);
		} catch (IllegalArgumentException e)
		{
			sawException = true;
		}
		assertTrue(sawException);
	}

}
