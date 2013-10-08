package view;

import java.util.ArrayList;
import java.util.Observable;

import model.QualifiedObservableReport;

/**
 * Not much for now!
 * @author Merlin
 *
 */
public class ScreenMapListener extends ScreenListener
{

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @see view.ScreenListener#getReportTypes()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
