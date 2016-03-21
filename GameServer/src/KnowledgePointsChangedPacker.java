import java.util.ArrayList;

import model.QualifiedObservableReport;
import model.reports.KnowledgePointsChangeReport;
import communication.messages.KnowledgeChangedMessage;
import communication.messages.Message;
import communication.packers.MessagePacker;


public class KnowledgePointsChangedPacker extends MessagePacker
{

	@Override
	public Message pack(QualifiedObservableReport object) 
	{
		if(object.getClass() != KnowledgePointsChangeReport.class)
		{
			throw new IllegalArgumentException(
					"ExperienceChangedMessagePacker cannot pack messages of type "
							+ object.getClass());
		}

		KnowledgePointsChangeReport report = (KnowledgePointsChangeReport)object;

		if (this.getAccumulator().getPlayerID() == report.getPlayerID())
		{
			KnowledgeChangedMessage msg = new KnowledgeChangedMessage(report.getPlayerID(), report.getRecord(), report.getKnowledgePoints());
			return msg;
		}
		return null;
	}

	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack() {
		// TODO Auto-generated method stub
		return null;
	}

}
