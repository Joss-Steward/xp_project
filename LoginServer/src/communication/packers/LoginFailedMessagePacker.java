package communication.packers;

import model.QualifiedObservableReport;
import model.reports.LoginFailedReport;
import communication.messages.LoginFailedMessage;
import communication.messages.Message;
import communication.packers.MessagePacker;

/**
 * @author Merlin
 *
 */
public class LoginFailedMessagePacker extends MessagePacker
{
	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass().equals(LoginFailedReport.class))
		{
			LoginFailedMessage msg = new LoginFailedMessage();
			return msg;
		}
		return null;
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypeWePack()
	 */
	@Override
	public Class<?> getReportTypeWePack()
	{
		return LoginFailedReport.class;
	}

}
