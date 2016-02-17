package communication.packers;

import java.util.ArrayList;

import model.QualifiedObservableReport;
import model.reports.AdventureNotifcationCompleteReport;
import communication.messages.AdventureNotificationCompleteMessage;
import communication.messages.Message;

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
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack() 
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = 
				new ArrayList<Class<? extends QualifiedObservableReport>>();
		result.add( AdventureNotifcationCompleteReport.class);
		return result;
	}

}
