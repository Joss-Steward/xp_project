package communication.packers;

import model.PlayerManager;
import model.QualifiedObservableReport;
import model.reports.QuestScreenReport;
import communication.messages.Message;
import communication.messages.QuestScreenMessage;

/**
 * 
 * @author Joshua Packs the quest screen change message
 */
public class QuestScreenMessagePacker extends MessagePacker
{

	/**
	 * 
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{

		if (object.getClass() != QuestScreenReport.class)
		{
			throw new IllegalArgumentException(
					"QuestScreenMessagePacker cannot pack messages of type "
							+ object.getClass());
		}
		QuestScreenReport report = (QuestScreenReport) object;
		Message msg = new QuestScreenMessage(report.getLoadState(), PlayerManager.getSingleton().getThisClientsPlayer().getID());
		return msg;
	}

	/**
	 * 
	 */
	@Override
	public Class<? extends QualifiedObservableReport> getReportTypeWePack()
	{
		return QuestScreenReport.class;
	}

}
