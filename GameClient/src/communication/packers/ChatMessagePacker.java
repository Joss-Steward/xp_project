package communication.packers;

import java.util.ArrayList;

import model.QualifiedObservableReport;
import model.reports.ChatSentReport;
import communication.messages.ChatMessage;
import communication.messages.Message;

/**
 * @author Dave
 * 
 * Packs up information from the ChatManager into a ChatMessage to be sent to the server.
 */
public class ChatMessagePacker extends MessagePacker
{

	/** 
	 * @param object A ChatSentReport to be translated into a ChatMessage
	 * @return A ChatMessage based on the ChatSentReport that was given.
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		
		if(object.getClass() != ChatSentReport.class)
		{
			throw new IllegalArgumentException(
					"ChatMessagePacker cannot pack messages of type "
							+ object.getClass());
		}
		
		ChatSentReport report = (ChatSentReport)object;
		ChatMessage msg = new ChatMessage(report.getSenderName(), report.getMessage(),
				report.getPosition(), report.getType());
		
		return msg;
	}

	/**
	 * This packer listens for ChatSentReports
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = 
				new ArrayList<Class<? extends QualifiedObservableReport>>();
		result.add( ChatSentReport.class);
		return result;
	}

}
