package edu.ship.shipsim.client.model;

import java.util.ArrayList;
import java.util.Observer;

import model.ClientPlayerQuest;
import model.ClientPlayerQuestTest;

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
		
		Observer obs = EasyMock.createMock(Observer.class);
		QuestStateReport report = new QuestStateReport(expected);
		obs.update(EasyMock.anyObject(ThisClientsPlayer.class), EasyMock.eq(report));
		EasyMock.replay(obs);
	
		cp.addObserver(obs, QuestStateReport.class);
		CommandSendQuestState cmd = new CommandSendQuestState();
		cmd.execute();
	
		EasyMock.verify(obs);
	}
}
