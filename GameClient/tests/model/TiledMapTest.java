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
public class TiledMapTest
{
	
	/**
	 * reset the singleton
	 */
	@Before
	public void setup()
	{
		TiledMap.resetSingleton();
	}
	
	/**
	 * make sure it is a good singleton
	 */
	@Test
	public void isResetableSingleton()
	{
		TiledMap facade1 = TiledMap.getSingleton();
		TiledMap facade2 = TiledMap.getSingleton();
		assertSame(facade1, facade2);
		TiledMap.resetSingleton();
		assertNotSame(facade1, TiledMap.getSingleton());
	}
	
	/**
	 * make sure we can tell it to use a given file
	 */
	@Test
	public void canSetMapTitle()
	{
		TiledMap.getSingleton().changeToNewFile("maps/current.tmx");
		assertEquals("maps/current.tmx", TiledMap.getSingleton().getMapFileTitle());
		
	}

}
