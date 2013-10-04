package view;

import java.util.ArrayList;
import java.util.Observer;

import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;

/**
 * There is one of these for each screen and it is responsible for listening for
 * reports from the model and handling them
 * 
 * @author Merlin
 * 
 */
public abstract class ScreenListener implements Observer
{

	/**
	 * @return the report types this listener should pay attention to
	 */
	public abstract ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes();
	
	/**
	 * EVERY subclass should call this method in its constructor!!!!!!
	 */
	public void setUpListening()
	{
		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
		ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = getReportTypes();
		for (Class<? extends QualifiedObservableReport> reportType:reportTypes)
		{
			cm.registerObserver(this, reportType);
		}
	}

}
