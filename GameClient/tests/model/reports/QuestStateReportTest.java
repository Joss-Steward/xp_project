package model.reports;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.reports.QuestStateReport;

import org.junit.Test;

import data.ClientPlayerQuestState;
import datatypes.QuestStateEnum;

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
		ArrayList<ClientPlayerQuestState> data = new ArrayList<ClientPlayerQuestState>();
		ClientPlayerQuestState q = new ClientPlayerQuestState(4, "title", "silly", QuestStateEnum.TRIGGERED, 42, 13, true, null);
		data.add(q);
		QuestStateReport report = new QuestStateReport(data);
		assertEquals(data, report.getClientPlayerQuestList());
		
	}

}
