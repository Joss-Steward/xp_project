package model;

import static org.junit.Assert.*;

import java.util.Observer;

import model.reports.SendChatMessageReport;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.ChatType;
import data.Position;

/**
 * @author Dave
 *
 * Make sure the ChatManager behaves properly.
 */
public class ChatManagerTest 
{
	/**
	 * Start fresh for each test
	 */
	@Before
	public void reset()
	{
		ChatManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}
	
	/**
	 * Make sure that ChatManger behaves as a singleton
	 */
	@Test
	public void testSingleton()
	{
		ChatManager cm1 = ChatManager.getSingleton();
		ChatManager cm2 = ChatManager.getSingleton();
		
		assertTrue(cm1.equals(cm2));
	}
	
	/**
	 * Make sure that ChatManager notifies its observers when it creates a SendChatMessage report.
	 */
	@Test
	public void testNotifiesObservers()
	{
		Observer obs = EasyMock.createMock(Observer.class);
		
		SendChatMessageReport report = new SendChatMessageReport("message", "sender", new Position(1,1), ChatType.Local);
		
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				SendChatMessageReport.class);
		
		obs.update(EasyMock.eq(ChatManager.getSingleton()), EasyMock.eq(report));
		
		EasyMock.replay(obs);
		
		ChatManager.getSingleton().sendChatToClients("message", "sender", new Position(1,1), ChatType.Local);
		
		EasyMock.verify(obs);
	}
}