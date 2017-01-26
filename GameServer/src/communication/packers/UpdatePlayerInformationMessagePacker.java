package communication.packers;

import java.util.ArrayList;

import model.QualifiedObservableReport;
import model.reports.UpdatePlayerInformationReport;
import communication.messages.InitializeThisClientsPlayerMessage;
import communication.messages.Message;

/**
 * @author Merlin
 *
 */
public class UpdatePlayerInformationMessagePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<Class<? extends QualifiedObservableReport>>();
		result.add(UpdatePlayerInformationReport.class);
		return result;
	}

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		UpdatePlayerInformationReport x = (UpdatePlayerInformationReport) object;
		if (this.getAccumulator().getPlayerID() == x.getPlayerID())
		{
			return new InitializeThisClientsPlayerMessage(x.getClientPlayerQuestList(), x.getExperiencePts(),
					x.getLevel());
		}
		return null;
	}

}
