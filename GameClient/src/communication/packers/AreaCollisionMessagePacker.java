package communication.packers;

import java.util.ArrayList;

import model.QualifiedObservableReport;
import model.reports.AreaCollisionReport;
import communication.messages.AreaCollisionMessage;
import communication.messages.Message;

/**
 * 
 * @author nhydock
 *
 */
public class AreaCollisionMessagePacker extends MessagePacker
{
	/**
	 * 
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass() != AreaCollisionReport.class)
		{
			throw new IllegalArgumentException(
					"AreaCollisionPacker cannot pack messages of type "
							+ object.getClass());
		}
		AreaCollisionReport report = (AreaCollisionReport) object;
		Message msg = new AreaCollisionMessage(report.getPlayerID(), report.getAreaName());
		return msg;
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<Class<? extends QualifiedObservableReport>>();
		result.add(AreaCollisionReport.class);
		return result;
	}

}
