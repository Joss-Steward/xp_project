package communication;

import model.QualifiedObservableReport;
import model.reports.LoginInitiatedReport;
import communication.MessagePacker;
import communication.messages.LoginMessage;
import communication.messages.Message;

/**
 * Takes the information given to us when MovementNotifier updates and
 * translates it to the appropriate MovementMessage. It turns out this is pretty
 * trivial since the MovementNotifier pushes a MovementMessage
 * 
 * @author merlin
 * 
 */
public class LoginMessagePacker implements MessagePacker
{

	/**
	 * @param accumulator the accumulator which will send the messages we are building
	 */
	public LoginMessagePacker(StateAccumulator accumulator)
	{
		// TODO Auto-generated constructor stub
	}

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
		Message msg = new LoginMessage(report.getName(), report.getPassword());
		return msg;
	}

}
