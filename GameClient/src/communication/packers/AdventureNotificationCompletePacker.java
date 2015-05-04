package communication.packers;

import model.QualifiedObservableReport;

import communication.messages.AdventureNotificationCompleteMessage;
import communication.messages.Message;

import edu.ship.shipsim.client.model.reports.AdventureNotifcationCompleteReport;

/**
 * @author Ryan
 *
 */
public class AdventureNotificationCompletePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object) 
	{
		AdventureNotifcationCompleteReport r = (AdventureNotifcationCompleteReport) object;
		return new AdventureNotificationCompleteMessage(r.getPlayerID(), r.getQuestID(), r.getAdventureID());
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypeWePack()
	 */
	@Override
	public Class<? extends QualifiedObservableReport> getReportTypeWePack() 
	{
		return AdventureNotifcationCompleteReport.class;
	}

}
