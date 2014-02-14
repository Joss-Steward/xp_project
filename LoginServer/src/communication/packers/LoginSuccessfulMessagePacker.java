package communication.packers;

import model.QualifiedObservableReport;
import model.reports.LoginSuccessfulReport;
import communication.messages.LoginSuccessfulMessage;
import communication.messages.Message;
import communication.packers.MessagePacker;

/**
 * @author Merlin
 *
 */
public class LoginSuccessfulMessagePacker extends MessagePacker
{
	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass().equals(LoginSuccessfulReport.class))
		{
			LoginSuccessfulReport report = (LoginSuccessfulReport)object;
			LoginSuccessfulMessage msg = new LoginSuccessfulMessage(report.getPlayerID(), report.getHostname(), report.getPort(), report.getPin());
			return msg;
		}
		return null;
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypeWePack()
	 */
	@Override
	public Class<? extends QualifiedObservableReport> getReportTypeWePack()
	{
		return LoginSuccessfulReport.class;
	}

}
