package model.reports;

import java.util.ArrayList;

import Quest.Quest;
import Quest.Task;
import Quest.Trigger;

import model.QualifiedObservableReport;

/**
 * @author Joshua Report given to update quest information
 */
public class QuestUpdateReport implements QualifiedObservableReport
{
	ArrayList<Quest> quests;
	ArrayList<Task> tasks;
	ArrayList<Trigger> triggers;

	/**
	 * Creates the message with quest data being passed
	 * 
	 * @param quests
	 *            All of the new quests
	 * @param tasks
	 *            All of the new tasks
	 * @param triggers
	 *            All of the new triggers
	 */
	public QuestUpdateReport(ArrayList<Quest> quests, ArrayList<Task> tasks,
			ArrayList<Trigger> triggers)
	{
		this.quests = quests;
		this.tasks = tasks;
		this.triggers = triggers;
	}

}
