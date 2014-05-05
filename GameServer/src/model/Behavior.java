package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observer;

/**
 * Contains the logic for what an NPC should do
 * 
 * @author Steve
 *
 */
public abstract class Behavior implements Serializable, Observer
{
	private static final long serialVersionUID = -1535370359851281459L;
	
	/*
	 * Defaults to 1s
	 */
	protected int pollingInterval = 1000;
	
	/**
	 * @return how often this behavior wants to be performed
	 */
	public int getPollingInterval() 
	{
		return this.pollingInterval;
	}
	
	
	/**
	 * EVERY subclass should call this method in its constructor!!!!!!
	 */
	public void setUpListening()
	{
		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
		ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = getReportTypes();
		for (Class<? extends QualifiedObservableReport> reportType : reportTypes)
		{
			cm.registerObserver(this, reportType);
		}
	}
	
	
	/**
	 * Execute the timed event
	 */
	public abstract void doTimedEvent();
	
	/**
	 * @return the report types this listener should pay attention to
	 */
	public abstract ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes();
}
