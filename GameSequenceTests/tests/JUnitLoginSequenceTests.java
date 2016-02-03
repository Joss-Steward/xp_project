import static org.junit.Assert.assertEquals;

import java.io.IOException;

import model.ClientModelFacade;
import model.ModelFacade;
import model.SequenceTestRunner;
import model.ServerType;

import org.junit.Test;

import communication.CommunicationException;

import datasource.DatabaseException;

/**
 * Runs all of the login sequence tests as JUnit tests
 * 
 * @author Merlin
 *
 */
public class JUnitLoginSequenceTests
{

	/**
	 * Runs the successful sequence
	 * 
	 * @throws IOException
	 *             shouldn't
	 * @throws CommunicationException
	 *             shouldn't
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testSuccess() throws IOException, CommunicationException,
			DatabaseException
	{
		SequenceTestRunner testToRun = new SequenceTestRunner(
				new LoginSuccessSequenceTest());
		for (int i = 0; i < 4; i++)
		{
			System.out.println(i);
			assertEquals(SequenceTestRunner.SUCCESS_MSG,
					testToRun.run((ServerType.values())[i]));
		}
		ClientModelFacade.killThreads();
		ModelFacade.killThreads();
	}

	/**
	 * Runs the sequences for when the user enters a bad password. This only has
	 * to run on the initiating player's client and the login server
	 * 
	 * @throws IOException
	 *             shouldn't
	 * @throws CommunicationException
	 *             shouldn't
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testBadPW() throws IOException, CommunicationException, DatabaseException
	{
		SequenceTestRunner testToRun = new SequenceTestRunner(
				new LoginBadPWSequenceTest());
		assertEquals(SequenceTestRunner.SUCCESS_MSG,
				testToRun.run(ServerType.THIS_PLAYER_CLIENT));
		assertEquals(SequenceTestRunner.SUCCESS_MSG,
				testToRun.run(ServerType.LOGIN_SERVER));
		ClientModelFacade.killThreads();
		ModelFacade.killThreads();
	}

	/**
	 * Runs the sequences for when the user enters an unknown player name. This
	 * only has to run on the initiating player's client and the login server
	 * 
	 * @throws IOException
	 *             shouldn't
	 * @throws CommunicationException
	 *             shouldn't
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testBadPlayerName() throws IOException, CommunicationException,
			DatabaseException
	{
		SequenceTestRunner testToRun = new SequenceTestRunner(
				new LoginBadPlayerNameSequenceTest());
		assertEquals(SequenceTestRunner.SUCCESS_MSG,
				testToRun.run(ServerType.THIS_PLAYER_CLIENT));
		assertEquals(SequenceTestRunner.SUCCESS_MSG,
				testToRun.run(ServerType.LOGIN_SERVER));
		ClientModelFacade.killThreads();
		ModelFacade.killThreads();
	}
	
	/**
	 * Runs the sequences for when the user enters an unknown player name. This
	 * only has to run on the initiating player's client and the login server
	 * 
	 * @throws IOException
	 *             shouldn't
	 * @throws CommunicationException
	 *             shouldn't
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testBadPinName() throws IOException, CommunicationException,
			DatabaseException
	{
		SequenceTestRunner testToRun = new SequenceTestRunner(
				new LoginBadPinSequenceTest());
		assertEquals(SequenceTestRunner.SUCCESS_MSG,
				testToRun.run(ServerType.THIS_PLAYER_CLIENT));
		
		assertEquals(SequenceTestRunner.SUCCESS_MSG,
				testToRun.run(ServerType.AREA_SERVER));
		ClientModelFacade.killThreads();
		ModelFacade.killThreads();
	}

	

}
