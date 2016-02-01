package communication.packers;

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
	 * @see communication.packers.MessagePacker#getReportTypeWePack()
	 */
	public Class<? extends QualifiedObservableReport> getReportTypeWePack()
	{
		return UpdatePlayerInformationReport.class;
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
			return new InitializeThisClientsPlayerMessage(
					x.getClientPlayerQuestList(), x.getExperiencePts(), x.getLevel());
		}
		return null;
	}

}
