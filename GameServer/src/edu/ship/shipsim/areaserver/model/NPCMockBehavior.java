package edu.ship.shipsim.areaserver.model;

import java.util.ArrayList;

import model.QualifiedObservableReport;

/**
 * Just for testing how behaviors work
 */
public class NPCMockBehavior extends NPCBehavior
{
	private static final long serialVersionUID = -1879830711372276973L;

	
	private int count = 0;

	/**
	 * @return the number of times we have done our timed event
	 */
	public int getCount()
	{
		return count;
	}

	/**
	 * 
	 */
	public NPCMockBehavior()
	{
		pollingInterval = 50;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.model.NPCBehavior#doTimedEvent()
	 */
	public void doTimedEvent()
	{
		this.count++;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.model.NPCBehavior#getReportTypes()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
	{
		return null;
	}

	
	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport arg)
	{
	}
}
