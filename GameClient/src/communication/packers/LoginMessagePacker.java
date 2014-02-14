package communication.packers;

import model.QualifiedObservableReport;
import model.reports.LoginInitiatedReport;
import communication.messages.LoginMessage;
import communication.messages.Message;
import communication.packers.MessagePacker;

/**
 * Takes the information given to us when MovementNotifier updates and
 * translates it to the appropriate MovementMessage. It turns out this is pretty
 * trivial since the MovementNotifier pushes a MovementMessage
 * 
 * @author merlin
 * 
 */
public class LoginMessagePacker extends MessagePacker
{
	/**
	 * 
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass() != LoginInitiatedReport.class)
		{
			throw new IllegalArgumentException("LoginMessagePacker cannot pack messages of type "
					+ object.getClass());
		}
		LoginInitiatedReport report = (LoginInitiatedReport) object;
		Message msg = new LoginMessage(report.getPlayerName(), report.getPassword());
		return msg;
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypeWePack()
	 */
	@Override
	public Class<? extends QualifiedObservableReport> getReportTypeWePack()
	{
		return LoginInitiatedReport.class;
	}

}
