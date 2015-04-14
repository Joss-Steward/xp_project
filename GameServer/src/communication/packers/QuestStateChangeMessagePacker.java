package communication.packers;

import model.QualifiedObservableReport;
import communication.messages.Message;
import communication.messages.QuestStateChangeMessage;
import datasource.QuestStateEnum;
import edu.ship.shipsim.areaserver.model.reports.QuestStateChangeReport;

/**
 * If our player has fulfilled a quest, we need to send a message so that the
 * client can tell the player of their success
 * 
 * @author Merlin
 *
 */
public class QuestStateChangeMessagePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		QuestStateChangeReport rpt = (QuestStateChangeReport) object;
		QuestStateChangeMessage msg = null;
		int playerID = rpt.getPlayerID();
		if (this.getAccumulator().getPlayerID() == playerID)
		{
			msg = new QuestStateChangeMessage(rpt.getQuestID(),
					rpt.getQuestDescription(), QuestStateEnum.NEED_FULFILLED_NOTIFICATION);
		}
		return msg;

	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypeWePack()
	 */
	@Override
	public Class<? extends QualifiedObservableReport> getReportTypeWePack()
	{
		return QuestStateChangeReport.class;
	}

}
