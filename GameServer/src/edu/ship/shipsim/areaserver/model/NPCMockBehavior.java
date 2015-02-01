package edu.ship.shipsim.areaserver.model;

import java.util.ArrayList;
import java.util.Observable;

import model.QualifiedObservableReport;

/**
 */
public class NPCMockBehavior extends NPCBehavior
{
	private static final long serialVersionUID = -1879830711372276973L;
	
	public NPCMockBehavior()
	{
		pollingInterval = 50;
	}
	
	public int count = 0;
	public void doTimedEvent() 
	{	
		this.count++;
	}

	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes() {
		return null;
	}

	@Override
	public void update(Observable o, Object arg) {
	}		
}
