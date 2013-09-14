package model;
import static org.junit.Assert.*;

import java.util.Observer;

import model.reports.ThisPlayerMovedReport;

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

	/**
	 * Always start with a new singleton
	 */
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
	 * Make sure that observers get an appropriate notification when the current player moves
	 */
	@Test
	public void notifiesOnMove()
	{
		Observer obs = EasyMock.createMock(Observer.class);
		ThisPlayerMovedReport report = new ThisPlayerMovedReport(new Position(3,4));
		obs.update(EasyMock.eq(Player.getSingleton()), EasyMock.eq(report));
		EasyMock.replay(obs);
		
		Player.getSingleton().addObserver(obs, ThisPlayerMovedReport.class);
		Player.getSingleton().move(new Position(3,4));
		EasyMock.verify(obs);
	}
	
}
