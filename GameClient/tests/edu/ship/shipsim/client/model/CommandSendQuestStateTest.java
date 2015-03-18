package edu.ship.shipsim.client.model;

import java.util.ArrayList;
import java.util.Observer;

import model.ClientPlayerQuest;
import model.ClientPlayerQuestTest;

import org.easymock.EasyMock;
import org.junit.Test;

import edu.ship.shipsim.client.model.reports.QuestStateReport;

public class CommandSendQuestStateTest
{

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
