package communication.packers;

import model.QualifiedObservableReport;
import model.reports.PlayerConnectionReport;
import communication.messages.Message;
import communication.messages.PlayerJoinedMessage;
import communication.packers.MessagePacker;

/**
 * Packs a message telling clients that a new player has joined this area server
 * 
 * @author Merlin
 * 
 */
public class PlayerJoinedMessagePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass().equals(PlayerConnectionReport.class))
		{
			PlayerConnectionReport report = (PlayerConnectionReport) object;
			int userID = report.getUserID();
			if (this.getAccumulator().getPlayerUserID() != userID)
			{
				PlayerJoinedMessage msg = new PlayerJoinedMessage(report.getUserName());
				return msg;
			}

		}
		return null;
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypeWePack()
	 */
	@Override
	public Class<? extends QualifiedObservableReport> getReportTypeWePack()
	{
		return PlayerConnectionReport.class;
	}

}
