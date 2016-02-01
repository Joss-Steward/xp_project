package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.NPCQuestion;

import org.junit.Test;

import datasource.DatabaseException;
import datasource.DatabaseTest;

/**
 * Test the NPCQuestion
 * 
 * @author Steve
 */
public class NPCQuestionTest extends DatabaseTest
{

	/**
	 * Want to make sure that we get different questions back from the DB. Don't care what they are
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testGetRandomQuestion() throws DatabaseException 
	{	
		ArrayList<NPCQuestion> list = new ArrayList<NPCQuestion>();
		for(int i = 0; i < 5; i++)
		{
			NPCQuestion q = NPCQuestion.getRandomQuestion();
			assertNotNull(q);
			if(!list.contains(q))
			{
				list.add(q);
			}
		}
		
		assertTrue(list.size() > 1);
	}
}
