package communication;

import model.QualifiedObservableReport;
import communication.MessagePacker;
import communication.messages.Message;

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
	 * @see communication.MessagePacker#getReportWePack()
	 */
	@Override
	public Class<?> getReportWePack()
	{
		// TODO When we re-implement movement
		return null;
	}

}
