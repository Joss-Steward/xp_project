package model.reports;

import java.util.ArrayList;

import data.ClientPlayerQuestState;
import model.QualifiedObservableReport;

/**
 * The report that handles sending a QuestStateReport
 * 
 * @author Merlin
 *
 */
public class QuestStateReport implements QualifiedObservableReport
{

	private ArrayList<ClientPlayerQuestState> data;

	/**
	 * Creates a QuestStateReport
	 * @param data ClientPlayerQuest list to send
	 */
	public QuestStateReport(ArrayList<ClientPlayerQuestState> data)
	{
		this.data = data;
	}

	/**
	 * Returns data an array list of ClientPlayerQuests
	 * @return data 
	 */
	public ArrayList<ClientPlayerQuestState> getClientPlayerQuestList()
	{
		return data;
	}

	/**
	 * Auto-generated
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	/**
	 * Auto-generated
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestStateReport other = (QuestStateReport) obj;
		if (data == null)
		{
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

}
