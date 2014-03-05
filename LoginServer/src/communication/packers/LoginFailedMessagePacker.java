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
		LoginFailedMessage msg = new LoginFailedMessage();
		return msg;
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypeWePack()
	 */
	@Override
	public Class<? extends QualifiedObservableReport> getReportTypeWePack()
	{
		return LoginFailedReport.class;
	}

}
