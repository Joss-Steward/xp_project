package edu.ship.shipsim.client.model.reports;

import java.util.ArrayList;

import model.ClientPlayerQuest;
import model.QualifiedObservableReport;

public class QuestStateReport implements QualifiedObservableReport
{

	private ArrayList<ClientPlayerQuest> data;

	public QuestStateReport(ArrayList<ClientPlayerQuest> data)
	{
		this.data = data;
	}

	public ArrayList<ClientPlayerQuest> getClientPlayerQuestList()
	{
		return data;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

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
