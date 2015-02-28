package edu.ship.shipsim.areaserver.datasource;

public interface QuestStateRowDataGateway
{

	void resetData();

	int getQuestID();

	int getPlayerID();

	QuestState getQuestState();

}
