package communication.packers;

import model.QualifiedObservableReport;
import communication.messages.CurrentQuestStateMessage;
import communication.messages.Message;
import edu.ship.shipsim.areaserver.model.reports.CurrentQuestStateReport;

public class CurrentQuestStateMessagePacker extends MessagePacker
{

	public  Class<? extends QualifiedObservableReport> getReportTypeWePack()
	{
		return CurrentQuestStateReport.class;
	}

	@Override
	public Message pack(QualifiedObservableReport object)
	{
		CurrentQuestStateReport x = (CurrentQuestStateReport) object;
		CurrentQuestStateMessage m = new CurrentQuestStateMessage(x.getClientPlayerQuestList());
		return m;
	}

}
