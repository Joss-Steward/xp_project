package communication;

import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.reports.LoginSuccessfulReport;
import communication.messages.LoginResponseMessage;
import communication.messages.Message;

/**
 * @author Merlin
 *
 */
public class LoginResponseMessagePacker implements MessagePacker
{

	/**
	 * @param accumulator the accumulator that will listen for the messages we will pack
	 */
	public LoginResponseMessagePacker(StateAccumulator accumulator)
	{
		QualifiedObservableConnector.getSingleton().registerObserver(accumulator, LoginSuccessfulReport.class);
	}

	/**
	 * @see communication.MessagePacker#pack(model.QualifiedObservableReport)
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

}
