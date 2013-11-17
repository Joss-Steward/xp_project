package communication.packers;

import model.QualifiedObservableReport;
import model.ThisClientsPlayer;
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
		Message msg = new QuestScreenMessage(report.getLoadState(), ThisClientsPlayer
				.getSingleton().getID());
		return msg;
	}

	/**
	 * 
	 */
	@Override
	public Class<?> getReportTypeWePack()
	{
		return QuestScreenReport.class;
	}

}
