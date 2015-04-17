package communication.packers;

import model.QualifiedObservableReport;
import communication.messages.Message;
import communication.messages.PlayerLeaveMessage;
import communication.packers.MessagePacker;
import edu.ship.shipsim.areaserver.model.reports.PlayerLeaveReport;

/**
 * Packs a message telling clients that a player has left this area server
 * 
 * @author Merlin
 * 
 */
public class PlayerLeaveMessagePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass().equals(PlayerLeaveReport.class))
		{
			PlayerLeaveReport report = (PlayerLeaveReport) object;

			PlayerLeaveMessage msg = new PlayerLeaveMessage(
					report.getPlayerID());
			return msg;
		}
		return null;
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypeWePack()
	 */
	@Override
	public Class<? extends QualifiedObservableReport> getReportTypeWePack()
	{
		return PlayerLeaveReport.class;
	}

}
