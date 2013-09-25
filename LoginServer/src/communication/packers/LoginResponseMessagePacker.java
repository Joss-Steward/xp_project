package communication.packers;

import model.QualifiedObservableReport;
import model.reports.LoginSuccessfulReport;
import communication.messages.LoginResponseMessage;
import communication.messages.Message;
import communication.packers.MessagePacker;

/**
 * @author Merlin
 *
 */
public class LoginResponseMessagePacker implements MessagePacker
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
			LoginResponseMessage msg = new LoginResponseMessage(report.getUserID(), report.getHostname(), report.getPort(), report.getPin());
			return msg;
		}
		return null;
	}

	/**
	 * @see communication.packers.MessagePacker#getReportWePack()
	 */
	@Override
	public Class<?> getReportWePack()
	{
		return LoginSuccessfulReport.class;
	}

}
