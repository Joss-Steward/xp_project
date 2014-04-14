package model;

import static org.junit.Assert.*;

import java.util.Observer;

import model.reports.PlayerMovedReport;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.Position;

/**
 * 
 * @author Merlin
 * 
 */
public class MovePlayerCommandTest
{

	/**
	 * Reset PlayerManager
	 */
	@Before
	public void setup()
	{
		PlayerManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}

	/**
	 * Update a player's position from id
	 * 
	 * @throws PlayerNotFoundException
	 *             shouldn't
	 */
	@Test
	public void testValidPlayer() throws PlayerNotFoundException
	{
		Position startPosition = new Position(0, 0);
		Position newPosition = new Position(10, 10);

		PlayerManager.getSingleton().addPlayer(1);
		Player p = PlayerManager.getSingleton().getPlayerFromID(1);
		p.setPlayerPosition(startPosition);

		assertEquals(startPosition, p.getPlayerPosition());

		MovePlayerCommand cmd = new MovePlayerCommand(1, newPosition);
		cmd.execute();

		assertEquals(newPosition, p.getPlayerPosition());
	}

	/**
	 * Make sure anyone who is observing for movement reports hears about this one
	 */
	@Test
	public void notifyObservers()
	{
		PlayerManager.getSingleton().addPlayer(1);
		Observer obs = EasyMock.createMock(Observer.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, PlayerMovedReport.class);
		obs.update(EasyMock.anyObject(Player.class), EasyMock.anyObject(PlayerMovedReport.class));
		EasyMock.replay(obs);
		
		MovePlayerCommand cmd = new MovePlayerCommand(1, new Position(4,3));
		cmd.execute();
		
		EasyMock.verify(obs);
	}
	/**
	 * Update a player's position from id
	 * 
	 * @throws PlayerNotFoundException
	 *             shouldn't
	 */
	@Test
	public void testNoPlayer() throws PlayerNotFoundException
	{
		Position newPosition = new Position(10, 10);

		MovePlayerCommand cmd = new MovePlayerCommand(-1, newPosition);
		assertFalse(cmd.execute());
	}

}
