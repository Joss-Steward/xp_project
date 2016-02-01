package communication.packers;

import model.QualifiedObservableReport;
import model.reports.PinFailedReport;
import communication.messages.Message;
import communication.messages.PinFailedMessage;
import communication.packers.MessagePacker;

/**
 * Takes the information given to us and
 * translates it to the appropriate PinFailedMessage. 
 * 
 * @author Matt and Andy
 */
public class PinFailedMessagePacker extends MessagePacker
{

	/**
	 * Generates a PinFailedMessage for a PinFailedReport.
	 * 
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass().equals(PinFailedReport.class))
		{
			PinFailedReport report = (PinFailedReport) object;
			String err = report.toString();
			PinFailedMessage msg = new PinFailedMessage(err);
			return msg;	
		}
		return null;
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypeWePack()
	 */
	@Override
	public Class<? extends QualifiedObservableReport> getReportTypeWePack()
	{
		return PinFailedReport.class;
	}

}
