package model.reports;

import model.QualifiedObservableReport;


/**
 * @author Joshua Tells the model to change the screen to quests
 */
public class QuestScreenReport implements QualifiedObservableReport
{

	private boolean loadState;

	/**
	 * Constructor
	 * 
	 * @param b
	 *            whether the screen is opening or closing
	 */
	public QuestScreenReport(boolean b)
	{
		loadState = b;
	}

	/**
	 * getter for loadState
	 * 
	 * @return boolean loadState
	 */
	public boolean getLoadState()
	{
		return loadState;
	}

	/**
	 * Whether the quest screen is being shown, or dissovled
	 * 
	 * @param state
	 *            Boolean true for being loaded, false to switch to map
	 */
	public void setLoadState(boolean state)
	{
		loadState = state;
	}

}

