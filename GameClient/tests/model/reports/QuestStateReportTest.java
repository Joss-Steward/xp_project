package model.reports;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.ClientPlayerQuest;
import model.reports.QuestStateReport;

import org.junit.Test;

import data.QuestStateEnum;

/**
 * Test the QuestStateReport
 * @author Merlin
 *
 */
public class QuestStateReportTest
{

	/**
	 * Test that a QuestStateReport is initialized correctly
	 */
	@Test
	public void test()
	{
		ArrayList<ClientPlayerQuest> data = new ArrayList<ClientPlayerQuest>();
		ClientPlayerQuest q = new ClientPlayerQuest(4, "title", "silly", QuestStateEnum.TRIGGERED, 42, 13, true);
		data.add(q);
		QuestStateReport report = new QuestStateReport(data);
		assertEquals(data, report.getClientPlayerQuestList());
		
	}

}
