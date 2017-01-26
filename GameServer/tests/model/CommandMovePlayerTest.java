package model;

import static org.junit.Assert.*;
import model.CommandMovePlayer;
import model.OptionsManager;
import model.Player;
import model.PlayerManager;
import model.PlayerNotFoundException;
import model.QualifiedObservableConnector;
import model.QualifiedObserver;
import model.reports.PlayerMovedReport;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import datatypes.Position;

/**
 * 
 * @author Merlin
 * 
 */
public class CommandMovePlayerTest
{

	/**
	 * Reset PlayerManager
	 */
	@Before
	public void setup()
	{
		PlayerManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();

		OptionsManager.getSingleton().setTestMode(true);
	}

	/**
	 * Update a player's position from id
	 * 
	 * @throws PlayerNotFoundException shouldn't
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

		CommandMovePlayer cmd = new CommandMovePlayer(1, newPosition);
		cmd.execute();

		assertEquals(newPosition, p.getPlayerPosition());
	}

	/**
	 * Make sure anyone who is observing for movement reports hears about this
	 * one
	 */
	@Test
	public void notifyObservers()
	{
		PlayerManager.getSingleton().addPlayer(1);
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, PlayerMovedReport.class);
		obs.receiveReport(EasyMock.anyObject(PlayerMovedReport.class));
		EasyMock.replay(obs);

		CommandMovePlayer cmd = new CommandMovePlayer(1, new Position(4, 3));
		cmd.execute();

		EasyMock.verify(obs);
	}

	/**
	 * Update a player's position from id
	 * 
	 * @throws PlayerNotFoundException shouldn't
	 */
	@Test
	public void testNoPlayer() throws PlayerNotFoundException
	{
		Position newPosition = new Position(10, 10);

		CommandMovePlayer cmd = new CommandMovePlayer(-1, newPosition);
		assertFalse(cmd.execute());
	}

}
