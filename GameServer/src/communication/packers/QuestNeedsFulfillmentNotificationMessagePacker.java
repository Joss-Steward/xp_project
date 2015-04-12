package communication.packers;

import model.QualifiedObservableReport;
import communication.messages.Message;
import communication.messages.QuestNeedsFulfillmentNotificationMessage;
import edu.ship.shipsim.areaserver.model.reports.QuestNeedsFulfillmentNotificationReport;

/**
 * If our player has fulfilled a quest, we need to send a message so that the
 * client can tell the player of their success
 * 
 * @author Merlin
 *
 */
public class QuestNeedsFulfillmentNotificationMessagePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		QuestNeedsFulfillmentNotificationReport rpt = (QuestNeedsFulfillmentNotificationReport) object;
		QuestNeedsFulfillmentNotificationMessage msg = null;
		int playerID = rpt.getPlayerID();
		if (this.getAccumulator().getPlayerID() == playerID)
		{
			msg = new QuestNeedsFulfillmentNotificationMessage(rpt.getQuestID(),
					rpt.getQuestDescription());
		}
		return msg;

	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypeWePack()
	 */
	@Override
	public Class<? extends QualifiedObservableReport> getReportTypeWePack()
	{
		return QuestNeedsFulfillmentNotificationReport.class;
	}

}
