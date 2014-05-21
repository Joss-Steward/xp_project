package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;

import java.util.Observer;

import model.QualifiedObservableConnector;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.Position;
import edu.ship.shipsim.areaserver.model.CommandMovePlayer;
import edu.ship.shipsim.areaserver.model.CommandMovePlayerSilently;
import edu.ship.shipsim.areaserver.model.Player;
import edu.ship.shipsim.areaserver.model.PlayerManager;
import edu.ship.shipsim.areaserver.model.PlayerNotFoundException;
import edu.ship.shipsim.areaserver.model.reports.PlayerMovedReport;

/**
 * 
 * @author Merlin
 * 
 */
public class CommandMovePlayerSilentlyTest
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
		
		CommandMovePlayerSilently cmd = new CommandMovePlayerSilently(1, newPosition);
		cmd.execute();

		assertEquals(newPosition, p.getPlayerPosition());
	}

	/**
	 * Make sure anyone who is observing for movement reports doesn't hear about this one
	 */
	@Test
	public void doesntNotifyObservers()
	{
		PlayerManager.getSingleton().addPlayer(1);
		Observer obs = EasyMock.createMock(Observer.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, PlayerMovedReport.class);
		EasyMock.replay(obs);
		
		CommandMovePlayerSilently cmd = new CommandMovePlayerSilently(1, new Position(4,3));
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

		CommandMovePlayer cmd = new CommandMovePlayer(-1, newPosition);
		assertFalse(cmd.execute());
	}

}
