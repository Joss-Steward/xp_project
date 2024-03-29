package communication.packers;

import static org.junit.Assert.*;
import model.reports.ChangeMapReport;

import org.junit.Test;

import communication.messages.TeleportationInitiationMessage;
import datatypes.Position;

/**
 * @author Matt
 * Make sure that the TeleportationInitiationMessagePacker behaves properly.
 */
public class TeleportationInitiationMessagePackerTest 
{

	/**
	 * Make sure that the report is properly translated into the message.
	 */
	@Test
	public void testPacking() 
	{
		int playerId = 7;
		String mapName = "Wally World";
		Position position = new Position(4,8);
		
		ChangeMapReport report = new ChangeMapReport(playerId, position, mapName);
		TeleportationInitiationMessagePacker packer = new TeleportationInitiationMessagePacker();
		TeleportationInitiationMessage msg = (TeleportationInitiationMessage) packer.pack(report);
		
		assertEquals(playerId, msg.getPlayerId());
		assertEquals(mapName, msg.getMapName());
		assertEquals(position, msg.getPosition());
	}

}
