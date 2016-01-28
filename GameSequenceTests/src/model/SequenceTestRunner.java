package model;

import java.io.IOException;
import java.util.ArrayList;

import communication.CommunicationException;
import communication.StateAccumulator;
import communication.handlers.MessageHandlerSet;
import communication.messages.Message;
import communication.packers.MessagePackerSet;

/**
 * Can simulate the behavior of a series of messages and test to ensure that
 * incoming messages cause the correct outgoing messages to be sent.
 * Essentially, this tests to ensure that all of the participants in a operation
 * obey the message protocol associated with that operation
 * 
 * @author Merlin
 *
 */
public class SequenceTestRunner
{
	private SequenceTest testcase;
	private StateAccumulator stateAccumulator;
	private MessageHandlerSet messageHandlerSet;
	private MessagePackerSet messagePackerSet;
	private StateAccumulator secondStateAccumulator;
//	private MessageHandlerSet secondMessageHandlerSet;
	private MessagePackerSet secondMessagePackerSet;

	/**
	 * @param test
	 *            the description of the message protocol for a given situation
	 * @throws IOException
	 *             shouldn't
	 */
	public SequenceTestRunner(SequenceTest test) throws IOException
	{
		this.testcase = test;
		OptionsManager.getSingleton().setTestMode(true);
		messagePackerSet = new MessagePackerSet();
		stateAccumulator = new StateAccumulator(messagePackerSet);
		stateAccumulator.setPlayerId(test.getInitiatingPlayerID());
		messageHandlerSet = new MessageHandlerSet(stateAccumulator);
		secondMessagePackerSet = new MessagePackerSet();
		secondStateAccumulator = new StateAccumulator(secondMessagePackerSet);
//		secondMessageHandlerSet = new MessageHandlerSet(secondStateAccumulator);

	}

	/**
	 * @param args
	 *            the name of the class describing the protocol and an integer
	 *            picking which type of server we are testing
	 * @throws IOException
	 *             shouldn't
	 * @throws CommunicationException
	 *             shouldn't
	 * @throws IllegalAccessException
	 *             shouldn't
	 * @throws InstantiationException
	 *             shouldn't
	 */
	public static void main(String[] args) throws IOException, CommunicationException,
			InstantiationException, IllegalAccessException
	{
		Class<?> testClass = null;
		try
		{
			testClass = Class.forName("model." + args[0]);
		} catch (ClassNotFoundException e)
		{
			System.out.println("Can't find a class with that name");
			System.exit(1);
		}
		SequenceTest testcase = (SequenceTest) testClass.newInstance();
		SequenceTestRunner runner = new SequenceTestRunner(testcase);
		ServerType serverToTest = (ServerType.values())[Integer.parseInt(args[1])];
		System.out.println(runner.run(serverToTest));
		ClientModelFacade.killThreads();
	}

	private String run(ServerType sType) throws CommunicationException
	{
		if (sType == testcase.getInitiatingServerType())
		{
			testcase.getInitiatingCommand().execute();
		}
		MessageFlow[] messages = testcase.getMessageSequence();
		for (MessageFlow msgFlow : messages)
		{
			Message message = msgFlow.getMessage();
			if (msgFlow.getSource().equals(sType))
			{
				System.out.println("Checking that I sourced " + msgFlow.getMessage());
				Message msgInAccumulator;
				if (msgFlow.getDestination().equals(ServerType.OTHER_CLIENT))
				{
					msgInAccumulator = secondStateAccumulator.getFirstMessage();

				} else
				{
					msgInAccumulator = stateAccumulator.getFirstMessage();
				}
				if (!msgInAccumulator.equals(message))
				{
					return sType.name() + " didn't send out " + message;
				}

			}
			if (msgFlow.getDestination().equals(sType))
			{
				System.out.println("I am receiving " + msgFlow.getMessage());

				messageHandlerSet.process(message);
				if (sType == ServerType.AREA_SERVER)
				{
					waitForCommandComplete();
				}

			}
		}
		return "Success!!";
	}

	private void waitForCommandComplete()
	{
		while (ModelFacade.getSingleton().hasCommandsPending())
		{
			try
			{
				Thread.sleep(100);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
