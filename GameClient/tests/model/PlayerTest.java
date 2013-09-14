package model;
import static org.junit.Assert.*;

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
	 * 
	 */
	@Test
	public void notifiesOnMove()
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		ThisPlayerMovedReport report = new ThisPlayerMovedReport(new Position(3,4));
		obs.update(EasyMock.eq(Player.getSingleton()), EasyMock.eq(report));
		EasyMock.replay(obs);
		
		Player.getSingleton().addObserver(obs, ThisPlayerMovedReport.class);
		Player.getSingleton().move(new Position(3,4));
		EasyMock.verify(obs);
	}
	
}
