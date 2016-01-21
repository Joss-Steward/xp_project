package communication.packers;

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
	 * @see communication.packers.MessagePacker#getReportTypeWePack()
	 */
	@Override
	public Class<? extends QualifiedObservableReport> getReportTypeWePack()
	{
		return AreaCollisionReport.class;
	}

}
