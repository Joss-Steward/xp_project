package communication.packers;

import model.QualifiedObservableReport;
import model.reports.HighScoreRequestReport;
import communication.messages.HighScoreRequestMessage;
import communication.messages.Message;

/**
 * Packer for the high score request message
 * @author Ryan
 *
 */
public class HighScoreRequestPacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object) 
	{
//		HighScoreRequestReport r = (HighScoreRequestReport) object;
		return new HighScoreRequestMessage();
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypeWePack()
	 */
	@Override
	public Class<? extends QualifiedObservableReport> getReportTypeWePack() 
	{
		return HighScoreRequestReport.class;
	}

}
