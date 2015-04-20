package edu.ship.shipsim.client.model;

import java.util.ArrayList;

import model.ClientPlayerQuest;
import model.ClientPlayerQuestTest;
import model.QualifiedObservableConnector;
import model.QualifiedObserver;

import org.easymock.EasyMock;
import org.junit.Test;

import edu.ship.shipsim.client.model.reports.QuestStateReport;

/**
 * Test the Command to send quest state to the view
 * @author Merlin
 *
 */
public class CommandSendQuestStateTest
{

	/**
	 * Test that the command executes successfully 
	 * and does what it is supposed to do
	 */
	@Test
	public void executeTest()
	{
		ThisClientsPlayer cp = ThisClientsPlayerTest.setUpThisClientsPlayerAsNumberOne();
		ClientPlayerQuest q = ClientPlayerQuestTest.createOneQuestWithTwoAdventures();
		cp.addQuest(q);
		ArrayList<ClientPlayerQuest> expected = new ArrayList<ClientPlayerQuest>() ;
		expected.add(q);
		
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, QuestStateReport.class);
		QuestStateReport report = new QuestStateReport(expected);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);
	
		CommandSendQuestState cmd = new CommandSendQuestState();
		cmd.execute();
	
		EasyMock.verify(obs);
	}
}
