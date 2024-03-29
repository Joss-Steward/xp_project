package communication.packers;

import java.util.ArrayList;

import model.QualifiedObservableReport;
import model.reports.ChangeMapReport;
import communication.messages.Message;
import communication.messages.TeleportationInitiationMessage;

/**
 * @author Matthew Kujawski and Frank
 * Listens for the chang map report.
 * Creates and packs a teleportation initiation message.
 */
public class TeleportationInitiationMessagePacker extends MessagePacker
{

	/** 
	 * @param object A ChangeMapReport to be translated into a TeleportationInitiationMessage
	 * @return A TeleportationInitiationMessage based on the ChangeMapReport that was given.
	 */
	@Override
	public Message pack(QualifiedObservableReport object) 
	{
		if(object.getClass() != ChangeMapReport.class)
		{
			throw new IllegalArgumentException(
					"TeleportationInitiationMessagePacker cannot pack messages of type "
							+ object.getClass());
		}
		
		ChangeMapReport report = (ChangeMapReport)object;
		TeleportationInitiationMessage msg = new TeleportationInitiationMessage(report.getPlayerID(),
				report.getMapName(), report.getPosition());
		
		return msg;
	}

	/** 
	 * The packer listens for ChangeMapReport.
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack() 
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = 
				new ArrayList<Class<? extends QualifiedObservableReport>>();
		result.add( ChangeMapReport.class);
		return result;
	}

}
