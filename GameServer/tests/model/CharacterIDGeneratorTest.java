package model;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.j256.ormlite.dao.Dao;

/**
 * Test the the id sequence of CharacterID works like expected
 * 
 * @author Steve
 *
 */
public class CharacterIDGeneratorTest 
{
	/**
	 * Testing that the id sequence works like we expect
	 * @throws SQLException shouldn't
	 */
	@Test
	public void testIDSequence() throws SQLException 
	{
		int id1 = CharacterIDGenerator.getNextId();
		int id2 = CharacterIDGenerator.getNextId();
		
		assertTrue(id2 > id1);
	}
	
	/**
	 * There shouldn't be any rows in CharacterID once we get an id
	 * @throws SQLException shouldn't
	 */
	@Test
	public void testNoLatentRows() throws SQLException
	{
		CharacterIDGenerator.getNextId();
		Dao<CharacterIDGenerator, Integer> dao = CharacterIDGenerator.getDao();
		assertEquals(0, dao.countOf());
	}

}
