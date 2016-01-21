package communication.packers;

import model.ClientPlayerManager;
import model.QualifiedObservableReport;
import model.reports.PlayerMovedReport;
import communication.messages.Message;
import communication.messages.MovementMessage;
import communication.packers.MessagePacker;

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
	 * 
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		PlayerMovedReport movementReport = (PlayerMovedReport) object;
		int playerID = movementReport.getID();
		if (ClientPlayerManager.getSingleton().getThisClientsPlayer().getID() == playerID)
		{
			Message msg = new MovementMessage(playerID, movementReport.getNewPosition());
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
		return PlayerMovedReport.class;
	}

}
