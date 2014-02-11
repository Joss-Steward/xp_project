package Quest;

import java.util.ArrayList;

import model.QualifiedObservable;
import model.QualifiedObservableConnector;
import model.reports.QuestScreenReport;
import model.reports.ThisPlayerMovedReport;
import data.Position;

/**
 * Class look watches movement of player and updates the quests
 * 
 * @author joshua
 * 
 */
public class QuestManager extends QualifiedObservable
{
	private ArrayList<Quest> questList;
	private Position playerPosition;

	/**
	 * Constructor
	 */
	public QuestManager()
	{
		questList = new ArrayList<Quest>();
		QualifiedObservableConnector.getSingleton()
				.registerQualifiedObservable(this, ThisPlayerMovedReport.class);

		QualifiedObservableConnector.getSingleton()
				.registerQualifiedObservable(this, QuestScreenReport.class);
	}

	/**
	 * checks whether the players position fulfills the quest's parameters
	 * 
	 * @param position
	 *            the xy coordinate of the quest
	 */
	public void checkQuests(Position position)
	{
		// checks quests for updates
		for (int i = 0; i < questList.size(); i++)
		{
			Quest q = questList.get(i);
			if (q != null && q.isActive())
			{
				q.setPosition(position);
				q.checkTasks();

			}
		}

	}

	/**
	 * activates the quest
	 * 
	 * @param q
	 *            Quest that needs activating
	 */
	public void setQuestActive(Quest q)
	{
		for (int i = 0; i < questList.size(); i++)
		{
			q.activateQuest(true);
		}
	}

	/**
	 * getter for the questList
	 * 
	 * @return ArrayList<Quest> the list of quests
	 */
	public ArrayList<Quest> getQuestList()
	{
		return questList;
	}

	/**
	 * setter for all of the quests
	 * 
	 * @param questList
	 *            ArrayList<Quest> the list of quests
	 */
	public void setQuestList(ArrayList<Quest> questList)
	{
		this.questList = questList;
	}

	/**
	 * adds a quest to the questlist as long as it doesnt already belong to the
	 * list
	 * 
	 * @param quest
	 *            the Quest to be added
	 */
	public void addQuest(Quest quest)
	{

		if (!this.questList.contains(quest))
		{
			this.questList.add(quest);
		}

	}

	/**
	 * getter for the player's position
	 * 
	 * @return Position the coordinates of the player
	 */
	public Position getPlayerPosition()
	{
		return playerPosition;
	}

	/**
	 * setter for position
	 * 
	 * @param playerPosition
	 *            the xy coordinate
	 */
	public void setPlayerPosition(Position playerPosition)
	{
		this.playerPosition = playerPosition;
	}

	/**
	 * notifies on quest and movement information
	 */
	@Override
	public boolean notifiesOn(Class<?> reportType)
	{
		if (reportType.equals(ThisPlayerMovedReport.class)
				|| reportType.equals(ThisPlayerMovedReport.class)
				|| reportType.equals(QuestScreenReport.class))
		{
			return true;
		}
		return false;
	}

	/**
	 * gets the triggers from the map layer
	 */
	public void getTriggersFromMap()
	{
		QuestLayerReader qr = new QuestLayerReader();
		qr.getQuestLayer();
		qr.getTriggers();
		qr.setQuests(questList);
		qr.buildRelationships();

		// TODO start here
	}
}
