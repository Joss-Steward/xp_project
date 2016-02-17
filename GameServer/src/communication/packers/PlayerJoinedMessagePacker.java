package communication.packers;

import java.util.ArrayList;

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

			PlayerJoinedMessage msg = new PlayerJoinedMessage(report.getPlayerID(),
					report.getPlayerName(), report.getAppearanceType(),
					report.getPosition());
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
		result.add( PlayerConnectionReport.class);
		return result;
	}

}
