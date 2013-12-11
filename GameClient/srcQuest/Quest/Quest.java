package Quest;

import java.util.ArrayList;
import data.Position;

/**
 * 
 * @author joshua Goal that is reached whenever every task is met
 */
public class Quest
{
	private String name;
	private String description;
	private Position position;
	private boolean active;
	private boolean completed;

	private ArrayList<Task> taskList;
	private Task currentTask;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            String that holds the name given to the quest
	 */
	public Quest(String name)
	{
		this.name = name;
		this.taskList = new ArrayList<Task>();
		this.active = false;
	}

	/**
	 * getter for name
	 * 
	 * @return String: name of the Quest
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * changes the name of the Quest
	 * 
	 * @param name
	 *            String the changed name
	 */
	public void changeName(String name)
	{
		this.name = name;
	}

	/**
	 * adds a task into the list of tasks to be completed for this quest
	 * 
	 * @param task
	 *            the task given to add
	 */
	public void addTask(Task task)
	{
		if (task != null)
		{
			this.taskList.add(task);
		}

		if (currentTask == null)
		{
			currentTask = task;
		}
	}

	/**
	 * getter for number of tasks
	 * 
	 * @return int the count of the tasks
	 */
	public int getTaskCount()
	{
		return this.taskList.size();
	}

	/**
	 * removes a task in the position specified
	 * 
	 * @param i
	 *            int the position to remove
	 */
	public void removeTask(int i)
	{
		if (i < this.taskList.size())
		{
			this.taskList.remove(i);
		}

	}

	/**
	 * removes a task in using the name of the task
	 * 
	 * @param taskName
	 *            string the name of the task
	 */
	public void removeTaskByName(String taskName)
	{
		for (int i = 0; i < this.taskList.size(); i++)
		{
			Task temp = this.taskList.get(i);
			if (temp.getName().equals(taskName))
			{
				this.taskList.remove(i);
				return;
			}
		}
	}

	/**
	 * gets the task associated with the position
	 * 
	 * @param i
	 *            int the position of the task
	 * @return Task the task at the position
	 */
	public Task getTask(int i)
	{
		if (i < this.taskList.size())
		{
			return this.taskList.get(i);
		}
		return null;
	}

	/**
	 * gets the task associated with the given name
	 * 
	 * @param taskName
	 *            String the name
	 * @return Task the task
	 */
	public Task getTaskByName(String taskName)
	{

		for (int i = 0; i < this.taskList.size(); i++)
		{
			if (this.taskList.get(i).getName().equals(taskName))
			{
				return this.taskList.get(i);

			}
		}
		return null;
	}

	/**
	 * Gets whether
	 * 
	 * @return boolean active
	 */
	public boolean isActive()
	{
		return this.active;
	}

	/**
	 * changes the status of an active quest/tasks deactivates all tasks when
	 * quest is deactivated -- activates first task when quest is activated
	 * 
	 * @param active
	 *            boolean to activate/deactivate quest
	 */
	public void activateQuest(boolean active)
	{
		this.active = active;

		if (!active)
		{
			// walks through tasks deactivating them
			for (int i = 0; i < this.taskList.size(); i++)
			{
				if (this.taskList.get(i) != null)
				{
					this.taskList.get(i).activateTask(false);

				}
			}
		} else
		{
			// activates the first task
			if (taskList != null && taskList.size() > 0)
			{
				Task temp = this.taskList.get(0);
				if (temp != null)
				{
					// cannot activate quest is the task has already been
					// completed
					// should never happen, but it is possible
					if (this.taskList.get(0).isCompleted() == false)
					{
						temp.activateTask(true);
					}
				}
			}
		}
	}

	/**
	 * checks whether quest is completed
	 * 
	 * @return boolean completeness
	 */
	public boolean checkCompleted()
	{

		for (int i = 0; i < this.taskList.size(); i++)
		{
			if (this.taskList.get(i).isCompleted() == false)
			{
				completed = false;
				return false;

			}
		}
		completed = true;
		return true;
	}

	/**
	 * getter for position
	 * 
	 * @return Position
	 */
	public Position getPosition()
	{
		return position;
	}

	/**
	 * setter for position
	 * 
	 * @param position
	 *            the xy coordinate
	 */
	public void setPosition(Position position)
	{
		this.position = position;
	}

	/**
	 * Checks if the tasks are all completed
	 */
	public void checkTasks()
	{
		completed = checkCompleted();

	}

	/**
	 * getter for whether the Quest is completed
	 * 
	 * @return boolean completed
	 */
	public boolean isCompleted()
	{
		return completed;
	}

	/**
	 * setter for the completed factor
	 * 
	 * @param completed
	 *            boolean completed or uncompleted
	 */
	public void setCompleted(boolean completed)
	{
		this.completed = completed;
	}

	/**
	 * getter for description
	 * 
	 * @return String the description of the quest
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * setter for description
	 * 
	 * @param description
	 *            String description of the quest
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * competed the current task and enabled the next
	 */
	public void completeCurrentTask()
	{
		if (currentTask != null && currentTask.isActive())
		{
			currentTask.setCompleted(true);
			int pos = taskList.lastIndexOf(currentTask);

			if (pos != -1 && pos < taskList.size() - 1)
			{
				currentTask = taskList.get(pos + 1);
				if (currentTask.isActive() == false)
				{
					currentTask.activateTask(true);
				}
			}

		}
	}

	/**
	 * testing purposes
	 * 
	 * @param selectedTask
	 */
	public void setSelectedTask(Task selectedTask)
	{
		this.currentTask = selectedTask;
	}
}
