package communication.packers;

import model.QualifiedObservableReport;
import communication.messages.ChatMessage;
import communication.messages.Message;
import edu.ship.shipsim.areaserver.model.reports.SendChatMessageReport;

/**
 * @author Dave
 * 
 * Packs up information from the ChatManager into a ChatMessage to be sent to the server.
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
		
		if(object.getClass() != SendChatMessageReport.class)
		{
			throw new IllegalArgumentException(
					"ChatMessagePacker cannot pack messages of type "
							+ object.getClass());
		}
		
		SendChatMessageReport report = (SendChatMessageReport)object;
		ChatMessage msg = new ChatMessage(report.getSenderName(), report.getMessage(),
				report.getPosition(), report.getType());
		
		return msg;
	}

	/**
	 * This packer listens for SendChatMessageReports
	 */
	@Override
	public Class<? extends QualifiedObservableReport> getReportTypeWePack()
	{
		return SendChatMessageReport.class;
	}

}
