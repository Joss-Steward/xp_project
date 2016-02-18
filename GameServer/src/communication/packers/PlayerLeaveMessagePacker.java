package communication.packers;

import java.util.ArrayList;

import model.QualifiedObservableReport;
import model.reports.PlayerLeaveReport;
import communication.messages.Message;
import communication.messages.PlayerLeaveMessage;
import communication.packers.MessagePacker;

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
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = 
				new ArrayList<Class<? extends QualifiedObservableReport>>();
		result.add( PlayerLeaveReport.class);
		return result;
	}

}
