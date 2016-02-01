package model.reports;

import static org.junit.Assert.*;
import model.LevelManager;
import model.OptionsManager;
import model.Player;
import model.PlayerManager;
import model.QuestManager;
import model.reports.ExperienceChangedReport;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datasource.LevelRecord;

/**
 * Tests the ExperienceChangedReport class functionality
 * @author Olivia
 * @author LaVonne
 */
public class ExperienceChangedReportTest 
{
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
	 * Tests that we can create a ExperienceChangedReport
	 * and get its experience points and playerID
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testCreateReport() throws DatabaseException 
	{
		Player john = PlayerManager.getSingleton().addPlayer(1);
		LevelRecord expected = LevelManager.getSingleton().getLevelForPoints(john.getExperiencePoints());
		ExperienceChangedReport report = new ExperienceChangedReport(john.getPlayerID(), john.getExperiencePoints(), expected);
		assertEquals(john.getExperiencePoints(), report.getExperiencePoints());
		assertEquals(expected, report.getRecord());
	}	
	
	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(ExperienceChangedReport.class).verify();
	}
}
