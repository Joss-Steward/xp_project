package communication.packers;

import model.QualifiedObservableReport;
import model.reports.StubQualifiedObservableReport1;
import communication.messages.StubMessage1;
import communication.messages.Message;
import communication.packers.MessagePacker;

/**
 * A relatively empty packer that the tests for MessagePackerSet will detect.  This will
 * let us test that it detects the MessagePackers that are in its package.  This is also used
 * by the StateAccumulator tests.
 * @author Merlin
 *
 */
public class StubMessagePacker1 extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		return new StubMessage1();
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypeWePack()
	 */
	@Override
	public Class<? extends QualifiedObservableReport> getReportTypeWePack()
	{
		return StubQualifiedObservableReport1.class;
	}

}
