package communication.packers;

import model.QualifiedObservableReport;
import communication.messages.Message;
import communication.messages.PlayerJoinedMessage;
import communication.packers.MessagePacker;
import edu.ship.shipsim.areaserver.model.reports.PlayerConnectionReport;

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

			PlayerJoinedMessage msg = new PlayerJoinedMessage(report.getPlayerID(),
					report.getPlayerName(), report.getAppearanceType(),
					report.getPosition());
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
		return PlayerConnectionReport.class;
	}

}
