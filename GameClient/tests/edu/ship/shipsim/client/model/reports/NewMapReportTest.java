package edu.ship.shipsim.client.model.reports;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.utils.Array;

import edu.ship.shipsim.client.model.reports.NewMapReport;

/**
 * @author Merlin
 * 
 */
public class NewMapReportTest
{

	/**
	 * make sure it gets built correctly
	 */
	@Test
	public void creation()
	{
		// NewMapReport report = new NewMapReport(new
		// TmxMapLoader().load("testMaps/simle.tmx"));
		// assertNotNull(report.getTiledMap());
		// TODO we cant really be sure it is built correctly until we can run
		// libgdx headless
	}

	/**
	 * Make sure the equals contract is obeyed. In this case, we had to do some
	 * magic to suppress some troubles in the TiledMap object we are
	 * transporting. In particular, its structure is recursive (twice), so we
	 * had to use .withPrefabValues to stop the recursion and it contains
	 * mutable fields so we had to suppress that warning. These things shouldn't
	 * cause problems in this environment
	 */
	@Test
	public void equalsContract()
	{
		Array<Integer> array1 = new Array<Integer>();
		array1.add(3);
		MapProperties p1 = new MapProperties();
		p1.put("stupid", 42);
		EqualsVerifier.forClass(NewMapReport.class)
				.withPrefabValues(Array.class, array1, new Array<Integer>())
				.withPrefabValues(MapProperties.class, p1, new MapProperties())
				.suppress(Warning.NONFINAL_FIELDS).verify();
	}
}
