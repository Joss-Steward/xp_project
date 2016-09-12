package communication.packers;

import java.util.ArrayList;

import model.QualifiedObservableReport;
import model.reports.StubQualifiedObservableReport1;
import communication.messages.StubMessage1;
import communication.messages.Message;
import communication.packers.MessagePacker;

/**
 * A relatively empty packer that the tests for MessagePackerSet will detect.
 * This will let us test that it detects the MessagePackers that are in its
 * package. This is also used by the StateAccumulator tests.
 * 
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
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = 
				new ArrayList<Class<? extends QualifiedObservableReport>>();
		result.add(StubQualifiedObservableReport1.class);
		return result;
	}

}
