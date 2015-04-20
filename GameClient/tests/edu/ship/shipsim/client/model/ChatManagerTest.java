package edu.ship.shipsim.client.model;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import model.QualifiedObservableConnector;
import model.QualifiedObserver;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.ChatType;
import data.Position;
import edu.ship.shipsim.client.model.ChatManager;
import edu.ship.shipsim.client.model.Player;
import edu.ship.shipsim.client.model.PlayerManager;
import edu.ship.shipsim.client.model.reports.ChatReceivedReport;
import edu.ship.shipsim.client.model.reports.ChatSentReport;

/**
 * Tests the functionality of the ChatManager
 * 
 * @author Steve
 *
 */
public class ChatManagerTest 
{
	/**
	 * Always start with a new singleton
	 */
	@Before
	public void reset()
	{
		PlayerManager.resetSingleton();
		ChatManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}
	

	/**
	 * There should be only one manager
	 */
	@Test
	public void testSingleton()
	{
		ChatManager cm1 = ChatManager.getSingleton();
		assertSame(cm1, ChatManager.getSingleton());
		ChatManager.resetSingleton();
		assertNotSame(cm1, ChatManager.getSingleton());
	}
	
	/**
	 * When an area chat is received, a report will always be sent out
	 */
	@Test
	public void notifiesOnAreaChatReceived()
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		ChatReceivedReport report = new ChatReceivedReport("message", "sender", ChatType.Zone);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				ChatReceivedReport.class);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		ChatManager.getSingleton().sendChatToUI("message", "sender", new Position(0,0), ChatType.Zone);

		EasyMock.verify(obs);
	}
	
	/**
	 * When a local chat is received, a report will always be sent out
	 * @throws NotBoundException shouldn't
	 * @throws AlreadyBoundException shouldn't
	 */
	@Test
	public void notifiesOnLocalChatReceived() throws AlreadyBoundException, NotBoundException
	{
		PlayerManager.getSingleton().initiateLogin("X", "X");
		Player p = PlayerManager.getSingleton().finishLogin(1);
		p.setPosition(new Position(5,5));
		
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		ChatReceivedReport report = new ChatReceivedReport("message", "sender", ChatType.Local);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				ChatReceivedReport.class);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		ChatManager.getSingleton().sendChatToUI("message", "sender", new Position(0,0), ChatType.Local);

		EasyMock.verify(obs);
	}
	
	/**
	 * Conditions for when a local message should be received
	 * @throws AlreadyBoundException shouldn't
	 * @throws NotBoundException shouldn't
	 */
	@Test
	public void canReceiveLocalMessageValid() throws AlreadyBoundException, NotBoundException
	{
		PlayerManager.getSingleton().initiateLogin("X", "X");
		Player p = PlayerManager.getSingleton().finishLogin(1);
		p.setPosition(new Position(5,5));
		
		assertTrue(ChatManager.getSingleton().canReceiveLocalMessage(new Position(5,5)));
		assertTrue(ChatManager.getSingleton().canReceiveLocalMessage(new Position(0,5)));
		assertTrue(ChatManager.getSingleton().canReceiveLocalMessage(new Position(5,0)));
		assertTrue(ChatManager.getSingleton().canReceiveLocalMessage(new Position(0,0)));
		assertTrue(ChatManager.getSingleton().canReceiveLocalMessage(new Position(6,6)));
		assertTrue(ChatManager.getSingleton().canReceiveLocalMessage(new Position(10,10)));
		assertTrue(ChatManager.getSingleton().canReceiveLocalMessage(new Position(0,10)));
		assertTrue(ChatManager.getSingleton().canReceiveLocalMessage(new Position(10,0)));
	}
	
	/**
	 * Conditions for when a local message should not be received
	 * @throws AlreadyBoundException shouldn't
	 * @throws NotBoundException shouldn't
	 */
	@Test
	public void canReceiveLocalMessageInvalid() throws AlreadyBoundException, NotBoundException
	{
		PlayerManager.getSingleton().initiateLogin("X", "X");
		Player p = PlayerManager.getSingleton().finishLogin(1);
		p.setPosition(new Position(5,5));
		
		assertFalse(ChatManager.getSingleton().canReceiveLocalMessage(new Position(0,-1)));
		assertFalse(ChatManager.getSingleton().canReceiveLocalMessage(new Position(-1,0)));
		assertFalse(ChatManager.getSingleton().canReceiveLocalMessage(new Position(11,0)));
		assertFalse(ChatManager.getSingleton().canReceiveLocalMessage(new Position(0,11)));
		assertFalse(ChatManager.getSingleton().canReceiveLocalMessage(new Position(5,11)));
		assertFalse(ChatManager.getSingleton().canReceiveLocalMessage(new Position(11,5)));
	}
	
	/**
	 * Properly sends a report for a message going to the server
	 * @throws NotBoundException shouldn't
	 * @throws AlreadyBoundException shouldn't
	 */
	@Test
	public void notifiesOnSendChatToServer() throws AlreadyBoundException, NotBoundException
	{
		PlayerManager.getSingleton().initiateLogin("X", "X");
		Player p = PlayerManager.getSingleton().finishLogin(1);
		p.setPosition(new Position(5,5));
		p.setName("my name");
		
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		ChatSentReport report = new ChatSentReport("message", "my name", p.getPosition(), ChatType.Local);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				ChatSentReport.class);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		ChatManager.getSingleton().sendChatToServer("message", ChatType.Local);

		EasyMock.verify(obs);
	}
}
