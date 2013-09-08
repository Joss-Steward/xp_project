package model;
import static org.junit.Assert.*;

import java.util.Observer;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.Position;

/**
 * Tests the player 
 * @author merlin
 *
 */
public class PlayerTest
{

	@Before
	public void reset()
	{
		Player.resetSingleton();
	}
	/**
	 * There should be only one player
	 */
	@Test
	public void testSingleton()
	{
		assertSame(Player.getSingleton(),Player.getSingleton());
	}

	/**
	 * 
	 */
	@Test
	public void notifiesOnMove()
	{
		Observer obs = EasyMock.createMock(Observer.class);
		obs.update(Player.getSingleton(), new Position(3,4));
		EasyMock.replay(obs);
		
		Player.getSingleton().addObserver(obs);
		Player.getSingleton().move(new Position(3,4));
		EasyMock.verify(obs);
	}
	
	/**
	 * 
	 */
	@Test
	public void notifiesOnLogin()
	{
		Observer obs = EasyMock.createMock(Observer.class);
		obs.update(Player.getSingleton(), new String("fred"));
		EasyMock.replay(obs);
		
		Player.getSingleton().addObserver(obs);
		Player.getSingleton().setName("fred");
		EasyMock.verify(obs);
	}
}
