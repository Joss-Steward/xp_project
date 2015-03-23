package communication.packers;

import model.QualifiedObservableReport;
import communication.messages.CurrentQuestStateMessage;
import communication.messages.Message;
import edu.ship.shipsim.areaserver.model.reports.CurrentQuestStateReport;

/**
 * @author Merlin
 *
 */
public class CurrentQuestStateMessagePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#getReportTypeWePack()
	 */
	public  Class<? extends QualifiedObservableReport> getReportTypeWePack()
	{
		return CurrentQuestStateReport.class;
	}

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		CurrentQuestStateReport x = (CurrentQuestStateReport) object;
		CurrentQuestStateMessage m = new CurrentQuestStateMessage(x.getClientPlayerQuestList());
		return m;
	}

}
