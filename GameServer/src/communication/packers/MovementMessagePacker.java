package communication.packers;
import model.QualifiedObservableReport;
import communication.messages.Message;
import communication.messages.MovementMessage;
import communication.packers.MessagePacker;

/**
 * Takes the information given to us when MovementNotifier updates and translates it to the appropriate MovementMessage.
 * It turns out this is pretty trivial since the MovementNotifier pushes a MovementMessage
 * @author merlin
 *
 */
public class MovementMessagePacker implements MessagePacker
{

	/**
	 * 
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		return (MovementMessage)object;
	}

	/**
	 * 
	 * @see communication.packers.MessagePacker#getReportTypeWePack()
	 */
	@Override
	public Class<? extends QualifiedObservableReport> getReportTypeWePack()
	{
		// TODO when we re-implement movement . . .
		return null;
	}

}
