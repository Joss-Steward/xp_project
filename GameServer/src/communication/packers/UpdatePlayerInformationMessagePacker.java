package communication.packers;

import model.QualifiedObservableReport;
import communication.messages.InitializeThisClientsPlayerMessage;
import communication.messages.Message;
import edu.ship.shipsim.areaserver.model.reports.UpdatePlayerInformationReport;

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
