package sequenceTests;

import communication.messages.Message;

public class MessageFlow
{

	private Message message;

	SequenceTestRunner.ServerType source;

	SequenceTestRunner.ServerType destination;

	public MessageFlow(SequenceTestRunner.ServerType source,
			SequenceTestRunner.ServerType destination, Message message)
	{
		super();
		this.message = message;
		this.source = source;
		this.destination = destination;
	}

	public SequenceTestRunner.ServerType getDestination()
	{
		return destination;
	}
	public Message getMessage()
	{
		return message;
	}
	public SequenceTestRunner.ServerType getSource()
	{
		return source;
	}

}
