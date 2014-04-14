package communication;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Simple tests for the LocalPortMapper
 * 
 * @author Steve
 *
 */
public class LocalPortMapperTest 
{

	/**
	 * Really simple test that the mapping returns a port
	 */
	@Test
	public void testGetMapping() 
	{
		LocalPortMapper mapper = new LocalPortMapper();
		assertEquals(1872, mapper.getPortForMapName("current.tmx"));
	}

}
