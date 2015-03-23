package view;

import java.util.ArrayList;
import java.util.Observable;

import model.QualifiedObservableReport;

@SuppressWarnings("javadoc")
public class ScreenQAsListener extends ScreenListener
{
	public ScreenQAsListener()
	{
		super.setUpListening();
	}
	
	@Override
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

}
