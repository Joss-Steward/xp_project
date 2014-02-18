package communication.packers;

import java.io.IOException;

import model.QualifiedObservableReport;
import model.reports.PlayerConnectionReport;
import communication.messages.MapFileMessage;
import communication.messages.Message;

/**
 * @author Merlin
 *
 */
public class MapFileMessagePacker extends MessagePacker
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
			try
			{
				int playerID = report.getPlayerID();
				if (this.getAccumulator().getPlayerID() == playerID)
				{
					MapFileMessage msg = new MapFileMessage("maps/current.tmx");
					return msg;
				}
			} catch (IOException e)
			{
				e.printStackTrace();
				return null;
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
