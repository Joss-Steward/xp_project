package communication.packers;

import model.QualifiedObservableReport;
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
public class MovementMessagePacker implements MessagePacker
{

	/**
	 * 
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		// TODO When we re-implement movement
		// Player player = (Player)obs;
		// Message msg = new MovementMessage(player.getID(), (Position)object);
		return null;
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypeWePack()
	 */
	@Override
	public Class<?> getReportTypeWePack()
	{
		// TODO When we re-implement movement
		return null;
	}

}
