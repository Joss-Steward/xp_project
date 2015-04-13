package edu.ship.shipsim.areaserver.model.reports;

import static org.junit.Assert.*;
import model.OptionsManager;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

import edu.ship.shipsim.areaserver.model.Player;
import edu.ship.shipsim.areaserver.model.PlayerManager;
import edu.ship.shipsim.areaserver.model.QuestManager;

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
		OptionsManager.getSingleton(true);
		QuestManager.resetSingleton();
	}
	
	/**
	 * Tests that we can create a ExperienceChangedReport
	 * and get its experience points and playerID
	 */
	@Test
	public void testCreateReport() 
	{
		Player john = PlayerManager.getSingleton().addPlayer(1);
		ExperienceChangedReport report = new ExperienceChangedReport(john.getExperiencePoints(), john.getPlayerID());
		assertEquals(john.getExperiencePoints(), report.getExperiencePoints());
		assertEquals(john.getPlayerID(), report.getPlayerID());
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
