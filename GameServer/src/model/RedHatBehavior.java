package model;

import java.util.ArrayList;

/**
 * Defines the behavior of the red hat
 * @author merlin
 *
 */
public class RedHatBehavior extends NPCBehavior
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		// we aren't getting any reports, yet
	}

	/**
	 * @see model.NPCBehavior#doTimedEvent()
	 */
	@Override
	public void doTimedEvent()
	{
		// we don't do anything at regular intervals
	}

	/**
	 * @see model.NPCBehavior#getReportTypes()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
	{
		// we don't get any reports now
		return null;
	}

}
