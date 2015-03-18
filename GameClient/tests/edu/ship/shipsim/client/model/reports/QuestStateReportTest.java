package edu.ship.shipsim.client.model.reports;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.ClientPlayerQuest;

import org.junit.Test;

import datasource.QuestStateEnum;

public class QuestStateReportTest
{

	@Test
	public void test()
	{
		ArrayList<ClientPlayerQuest> data = new ArrayList<ClientPlayerQuest>();
		ClientPlayerQuest q = new ClientPlayerQuest(4, "silly", QuestStateEnum.TRIGGERED);
		data.add(q);
		QuestStateReport report = new QuestStateReport(data);
		assertSame(data, report.getClientPlayerQuestList());
		
	}

}
