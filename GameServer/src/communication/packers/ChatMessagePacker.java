package communication.packers;

import java.util.ArrayList;

import model.QualifiedObservableReport;
import model.reports.SendChatMessageReport;
import communication.messages.ChatMessage;
import communication.messages.Message;

/**
 * @author Dave
 * 
 *         Packs up information from the ChatManager into a ChatMessage to be
 *         sent to the server.
 */
public class ChatMessagePacker extends MessagePacker
{

	/**
	 * @param object A SendChatMessageReport to be translated into a ChatMessage
	 * @return A ChatMessage based on the SendChatMessageReport that was given.
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{

		if (object.getClass() != SendChatMessageReport.class)
		{
			throw new IllegalArgumentException("ChatMessagePacker cannot pack messages of type " + object.getClass());
		}

		SendChatMessageReport report = (SendChatMessageReport) object;
		ChatMessage msg = new ChatMessage(report.getSenderName(), report.getMessage(), report.getPosition(),
				report.getType());

		return msg;
	}

	/**
	 * This packer listens for SendChatMessageReports
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<Class<? extends QualifiedObservableReport>>();
		result.add(SendChatMessageReport.class);
		return result;
	}

}
