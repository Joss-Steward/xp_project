package communication.handlers;

import static org.junit.Assert.assertEquals;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import model.ClientModelFacade;
import model.ClientPlayerManager;
import model.CommandOverwriteExperience;

import org.junit.Before;
import org.junit.Test;

import testData.PlayersForTest;

import communication.messages.ExperienceChangedMessage;

import datasource.LevelRecord;

/**
 * @author Ryan
 *
 */
public class ExperienceChangedMessageHandlerTest
{

	/**
	 * Reset the ModelFacade
	 * 
	 * @throws NotBoundException
	 *             shouldn't
	 * @throws AlreadyBoundException
	 *             shouldn't
	 */
	@Before
	public void reset() throws AlreadyBoundException, NotBoundException
	{
		ClientModelFacade.resetSingleton();
		ClientModelFacade.getSingleton(true, false);
		ClientPlayerManager.getSingleton().initiateLogin("john", "pw");
		ClientPlayerManager.getSingleton().finishLogin(PlayersForTest.JOHN.getPlayerID());
	}

	/**
	 * Tests that getTypeWeHandle method returns correct type.
	 */
	@Test
	public void testTypeWeHandle()
	{
		ExperienceChangedMessageHandler h = new ExperienceChangedMessageHandler();
		assertEquals(ExperienceChangedMessage.class, h.getMessageTypeWeHandle());
	}

	/**
	 * Testing to see if a command is queued after receiving a message
	 * 
	 * @throws InterruptedException
	 *             shouldn't
	 * 
	 */
	@Test
	public void handleExperienceChangedMessage() throws InterruptedException
	{
		LevelRecord record = new LevelRecord("Serf", 15, 10, 7);

		ExperienceChangedMessage msg = new ExperienceChangedMessage(
				PlayersForTest.JOHN.getPlayerID(),
				PlayersForTest.JOHN.getExperiencePoints() + 2, record);
		ExperienceChangedMessageHandler h = new ExperienceChangedMessageHandler();
		h.process(msg);

		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
		CommandOverwriteExperience x = (CommandOverwriteExperience) ClientModelFacade
				.getSingleton().getNextCommand();
		assertEquals(PlayersForTest.JOHN.getExperiencePoints() + 2,
				x.getExperiencePoints());
		assertEquals(record, x.getLevelRecord());
		ClientModelFacade.getSingleton().emptyQueue();
	}
}
