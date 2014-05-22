package communication.packers;

import model.QualifiedObservableReport;
import communication.messages.Message;
import communication.messages.QuestScreenMessage;
import edu.ship.shipsim.client.model.PlayerManager;
import edu.ship.shipsim.client.model.reports.QuestScreenReport;

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
		Message msg = new QuestScreenMessage(report.getLoadState(), PlayerManager
				.getSingleton().getThisClientsPlayer().getID());
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
