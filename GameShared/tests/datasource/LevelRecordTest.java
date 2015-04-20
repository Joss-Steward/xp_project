package datasource;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test for LevelRecord
 * @author Merlin
 *
 */
public class LevelRecordTest
{

	/**
	 * Test the compare to method in Level Record because
	 * it implements Comparable<LevelRecord>
	 */
	@Test
	public void testCompareTo()
	{
		LevelRecord a = new LevelRecord("a", 34);
		LevelRecord b = new LevelRecord("b", 35);
		LevelRecord c = new LevelRecord("b", 35);
		
		assertTrue(a.compareTo(b) < 0);
		assertTrue(b.compareTo(c) == 0);
		assertTrue(b.compareTo(a) > 0);
	}

}
