package communication.packers;

import java.util.ArrayList;

import model.ClientPlayerManager;
import model.QualifiedObservableReport;
import model.reports.ThisClientsPlayerMovedReport;
import communication.messages.Message;
import communication.messages.PlayerMovedMessage;
import communication.packers.MessagePacker;

/**
 * Takes the information given to us when MovementNotifier updates and
 * translates it to the appropriate MovementMessage. It turns out this is pretty
 * trivial since the MovementNotifier pushes a MovementMessage
 * 
 * @author merlin
 * 
 */
public class PlayerMovedMessagePacker extends MessagePacker
{

	/**
	 * 
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		ThisClientsPlayerMovedReport movementReport = (ThisClientsPlayerMovedReport) object;
		int playerID = movementReport.getID();
		if (ClientPlayerManager.getSingleton().getThisClientsPlayer().getID() == playerID)
		{
			Message msg = new PlayerMovedMessage(playerID, movementReport.getNewPosition());
			return msg;
		}
		return null;
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = 
				new ArrayList<Class<? extends QualifiedObservableReport>>();
		result.add( ThisClientsPlayerMovedReport.class);
		return result;
	}

}
