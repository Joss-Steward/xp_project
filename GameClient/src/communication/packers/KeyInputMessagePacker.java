package communication.packers;

import java.util.ArrayList;

import model.QualifiedObservableReport;
import model.reports.KeyInputSentReport;

import communication.messages.KeyInputMessage;
import communication.messages.Message;

/**
 * Tests functionality for a key input message packer
 * @author Ian Keefer & TJ Renninger
 */
public class KeyInputMessagePacker extends MessagePacker {

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object) {
		if(object.getClass() != KeyInputSentReport.class)
		{
			throw new IllegalArgumentException("KeyInputPacker cannot pack messages of type " + object.getClass());
		}
		
		KeyInputSentReport report = (KeyInputSentReport) object;
		KeyInputMessage msg = new KeyInputMessage(report.getInput());
		
		return msg;
	}

	/**
	 * 
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack() {
		ArrayList<Class<? extends QualifiedObservableReport>> result = 
				new ArrayList<Class<? extends QualifiedObservableReport>>();
		result.add(KeyInputSentReport.class);
		return result;
	}

}
