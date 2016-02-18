package communication.packers;

import java.util.ArrayList;

import model.QualifiedObservableReport;
import model.reports.AdventureStateChangeReport;
import communication.messages.AdventureStateChangeMessage;
import communication.messages.Message;

/**
 * @author Ryan
 *
 */
public class AdventureStateChangeMessagePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object) 
	{
		AdventureStateChangeReport rpt = (AdventureStateChangeReport) object;
		AdventureStateChangeMessage msg = null;
		int playerID = rpt.getPlayerID();
		if (this.getAccumulator().getPlayerID() == playerID)
		{
			msg = new AdventureStateChangeMessage(rpt.getPlayerID(), rpt.getQuestID(), rpt.getAdventureID(),
					rpt.getAdventureDescription(), rpt.getNewState());
		}
		return msg;
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack() 
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = 
				new ArrayList<Class<? extends QualifiedObservableReport>>();
		result.add( AdventureStateChangeReport.class);
		return result;
	}


}
