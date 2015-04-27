package datasource;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Merlin
 *
 */
public class PlayerScoreRecordTest
{

	/**
	 * It is just a data transfer object. Make sure it stores the stuff it is
	 * supposed to store
	 */
	@Test
	public void test()
	{
		PlayerScoreRecord actual = new PlayerScoreRecord("Me", 1492);
		assertEquals("Me", actual.getPlayerName());
		assertEquals(1492, actual.getExperiencePoints());
	}

}
