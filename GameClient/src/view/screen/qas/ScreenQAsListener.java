package view.screen.qas;

import java.util.ArrayList;
import java.util.Observable;

import view.screen.ScreenListener;
import model.QualifiedObservableReport;

@SuppressWarnings("javadoc")
public class ScreenQAsListener extends ScreenListener
{
	public ScreenQAsListener()
	{
		super.setUpListening();
	}
	
	public void update(Observable arg0, Object arg1) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes() 
	{
		ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = new ArrayList<Class<? extends QualifiedObservableReport>>();
		return reportTypes;
	}

	@Override
	public void receiveReport(QualifiedObservableReport report) 
	{
		// TODO Auto-generated method stub
		
	}

}
