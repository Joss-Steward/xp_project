package datasource;

import datatypes.QuestStateEnum;

public class QuestStateRecord
{

	private QuestStateEnum questState;
	private int experiencePoints;

	public QuestStateRecord(int playerID, int questID, 
			QuestStateEnum questState, int experiencePoints)
	{
		this.questState = questState;
		this.experiencePoints = experiencePoints;
	}

	public QuestStateEnum getState()
	{
		return questState;
	}

	public int getExperiencePoints()
	{		
		return experiencePoints;
	}

}
