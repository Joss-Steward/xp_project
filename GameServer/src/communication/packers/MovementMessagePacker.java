package communication.packers;

import model.QualifiedObservableReport;
import communication.messages.Message;
import communication.messages.MovementMessage;
import communication.packers.MessagePacker;
import edu.ship.shipsim.areaserver.model.reports.PlayerMovedReport;

/**
 * Takes the information given to us when MovementNotifier updates and
 * translates it to the appropriate MovementMessage. It turns out this is pretty
 * trivial since the MovementNotifier pushes a MovementMessage
 * 
 * @author merlin
 * 
 */
public class MovementMessagePacker extends MessagePacker
{

	/**
	 * Generates a MovementMessage for a PlayerMovedReport that not associated
	 * with the player in the accumulator.
	 * 
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass().equals(PlayerMovedReport.class))
		{
			PlayerMovedReport report = (PlayerMovedReport) object;
			int playerID = report.getPlayerID();
			if (this.getAccumulator().getPlayerID() != playerID)
			{
				MovementMessage msg = new MovementMessage(playerID,
						report.getNewPosition());
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
		return PlayerMovedReport.class;
	}

}
