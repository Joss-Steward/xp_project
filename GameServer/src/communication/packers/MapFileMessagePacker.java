package communication.packers;

import java.io.IOException;

import model.PlayerManager;
import model.PlayerNotFoundException;
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
				int userID = PlayerManager.getSingleton().getUserIDFromUserName(
						report.getUserName());
				if (this.getAccumulator().getPlayerUserID() == userID)
				{
					MapFileMessage msg = new MapFileMessage("maps/simple.tmx");
					return msg;
				}
			} catch (PlayerNotFoundException e)
			{
				return null;
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
	public Class<?> getReportTypeWePack()
	{
		return PlayerConnectionReport.class;
	}

}
