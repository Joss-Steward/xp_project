package model.reports;

import static org.junit.Assert.assertEquals;
import model.LevelManager;
import model.OptionsManager;
import model.Player;
import model.PlayerManager;
import model.QuestManager;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datasource.LevelRecord;

/**
 * @author Matthew Croft
 *
 */
public class KnowledgeChangeReportTest {
	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setTestMode(true);
		QuestManager.resetSingleton();
	}
	
	/**
	 * Tests that we can create a KnowledgePointsChangeReport
	 * and get its knowledge points and playerID
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testCreateReport() throws DatabaseException 
	{
		Player john = PlayerManager.getSingleton().addPlayer(1);
		LevelRecord expected = LevelManager.getSingleton().getLevelForPoints(john.getKnowledgePoints());
		KnowledgePointsChangeReport report = new KnowledgePointsChangeReport(john.getPlayerID(), john.getKnowledgePoints(), expected);
		assertEquals(john.getKnowledgePoints(), report.getKnowledgePoints());
		assertEquals(expected, report.getRecord());
	}	
	
	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(KnowledgePointsChangeReport.class).verify();
	}
}
