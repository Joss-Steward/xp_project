package view;

import java.util.ArrayList;

import model.CommandQuestScreenClose;
import model.ModelFacade;
import Quest.Quest;
import Quest.QuestManager;
import Quest.QuestSystemLargeTest;
import Quest.Task;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import view.ScreenBasic;

/**
 * QuestScreen Renders a table that holds the current quests and tasks with
 * their descriptions
 * 
 * @author Joshua
 * 
 */
public class ScreenQuest extends ScreenBasic
{

	private final Color COMPLETED_COLOR = Color.GRAY;
	private final Color ENABLED_COLOR = Color.GREEN;
	private final Color UNCOMPLETED_COLOR = Color.RED;
	// instance variables
	int width, height;
	private Image background;
	private Image logo;
	private Table questTable;
	final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	private QuestManager qm = new QuestManager();
	private Quest selectedQuest;
	private Task selectedTask;

	/**
	 * Constructor that builds the screen
	 */
	public ScreenQuest()
	{
		getQuestForDebug();
		initializeScreen();
	}

	/**
	 * initializes all of the variables and builds the screen for the first time
	 */
	private void initializeScreen()
	{
		// initialize variables
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		stage = new Stage();
		stage.setCamera(new OrthographicCamera());
		stage.getCamera().update();
		// builds the quest screen background
		Texture texture = new Texture("data/journal.png");
		background = new Image(new TextureRegion(texture, 0, 0,
				texture.getWidth(), texture.getHeight()));
		stage.addActor(background);

		Texture texture2 = new Texture("data/QuestLogo.png");
		logo = new Image(new TextureRegion(texture2, 0, 0, texture2.getWidth(),
				texture2.getHeight()));
		stage.setViewport(texture.getWidth(), texture.getHeight(), false);

		// adds quest journal logo
		logo.setX((float) (stage.getWidth() / 2 - (logo.getWidth() / 2)));
		logo.setY(stage.getHeight() - logo.getHeight());

		stage.addActor(logo);

		stage.getCamera().update();
		// builds the questTable

		// chooses first quest for default selection
		selectedQuest = qm.getQuestList().get(0);
		selectedTask = qm.getQuestList().get(0).getTask(0);
		builldQuestDisplayTable(selectedQuest, selectedTask);
		stage.addActor(questTable);

	}

	/**
	 * TODO: remove when not needed gets the quest system already built for
	 * testing purposes
	 */
	private void getQuestForDebug()
	{
		qm.setQuestList(QuestSystemLargeTest.buildQuestSystem());
	}

	/**
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta)
	{
		// checks for resizing the screen
		if (width != Gdx.graphics.getWidth()
				|| height != Gdx.graphics.getHeight())
		{
			initializeScreen();
		}

		Gdx.graphics.getGL10().glClearColor(0, 0, 0, 1);
		Gdx.graphics.getGL10().glClear(
				GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		stage.act();
		stage.draw();
		// Table.drawDebug(stage);

		// q key is touched and closes the quest screen
		if (Gdx.input.isKeyPressed(Keys.Q))
		{
			System.out.println("\n Return to map button is pressed \n");
			CommandQuestScreenClose lc = new CommandQuestScreenClose();
			ModelFacade.getSingleton(false, false).queueCommand(lc);
		}

		// translates the click into stage input, not just on the screen
		if (Gdx.input.isButtonPressed(Buttons.LEFT))
		{
			// System.out.println(Gdx.input.getX() + "," + Gdx.input.getY());
			stage.touchDown(Gdx.input.getX(), Gdx.input.getY(), 0, 0);
		}

		// for testing purposes only!!!
		// TODO DELETE!
		// if (Gdx.input.isButtonPressed(Keys.E))
		// {
		// if (selectedQuest != null)
		// {
		// qm.setQuestActive(selectedQuest);
		// }
		// if (selectedTask != null)
		// {
		//
		// selectedQuest.completeCurrentTask();
		// // if (selectedTask.isActive() == true)
		// // {
		// // qm.setTaskCompleted(selectedTask, true);
		// // }
		// // selectedTask.activateTask(true);

	}

	/**
	 * setter for quests
	 * 
	 * @param quests
	 *            ArrayList<Quest> the quests
	 */
	public void setQuests(ArrayList<Quest> quests)
	{
		// don't nullify all quests
		if (quests != null)
		{
			qm.setQuestList(quests);
		}
	}

	/**
	 * Builds the table to hold the quest and task data
	 * 
	 * @param selectedTask
	 */
	private void builldQuestDisplayTable(Quest selectedQuest, Task selectedTask)
	{
		Table questTable = new Table();
		questTable.debug();
		questTable.setFillParent(true);

		System.out.println("gdxheight " + Gdx.graphics.getHeight());
		System.out.println("gdxwidth " + Gdx.graphics.getWidth());

		questTable.debug(); // Used to view table grid lines

		// puts quest names into the table
		Label nameLabel = new Label("Quests", skin);
		nameLabel.setName(nameLabel.getText().toString());
		nameLabel.setColor(Color.BLACK);
		questTable.add(nameLabel).colspan(2).pad(2);

		nameLabel = new Label("Description", skin);
		nameLabel.setName(nameLabel.getText().toString());
		questTable.add(nameLabel).colspan(2).pad(2);

		nameLabel = new Label("Tasks", skin);
		nameLabel.setName(nameLabel.getText().toString());
		nameLabel.setColor(Color.BLACK);
		questTable.add(nameLabel).colspan(2).pad(2);

		nameLabel = new Label("Description", skin);
		nameLabel.setName(nameLabel.getText().toString());
		questTable.add(nameLabel).colspan(2).pad(2);

		if (qm.getQuestList() != null)
		{
			// adds all quests
			for (int i = 0; i < qm.getQuestList().size(); i++)
			{
				// adds name
				questTable.row();
				buildQuestRow(qm.getQuestList().get(i), questTable);

				// add tasks to list if it is the selected quests
				if (qm.getQuestList().get(i) == selectedQuest)
				{
					for (int j = 0; j < qm.getQuestList().get(i).getTaskCount(); j++)
					{
						buildTaskRow(qm.getQuestList().get(i).getTask(j),
								questTable);
					}

					// adds a description of the task
					if (selectedQuest != null && selectedTask != null)
					{
						if (selectedQuest.getTaskByName(selectedTask.getName()) != null)
						{
							nameLabel = new Label(
									selectedTask.getDescription(), skin);
							taskButtonListener(nameLabel);
							questTable.add(nameLabel).colspan(2).pad(2)
									.padRight(5);
						}
					}
				}
			}
		}
		// reassigns the quest table after everything has been changed
		this.questTable = questTable;
	}

	/**
	 * adds a quest to the table with corresponding colors and sizing
	 * 
	 * @param q
	 *            Quest the quest to add to the table
	 * @param questTable
	 */
	private void buildQuestRow(Quest q, Table questTable)
	{
		if (questTable != null && q != null)
		{
			Label nameLabel = new Label(q.getName(), skin);
			nameLabel.setName(q.getName());

			questButtonListener(nameLabel);
			if (q.isActive() && q.isCompleted() == false)
			{
				nameLabel.setColor(ENABLED_COLOR);
				// quest is disabled
			} else if (q.isCompleted() == true)
			{
				nameLabel.setColor(COMPLETED_COLOR);
			} else
			{
				nameLabel.setColor(UNCOMPLETED_COLOR);
			}
			questTable.add(nameLabel).colspan(2).pad(2).padRight(5);
			// adds description
			nameLabel = new Label(q.getDescription(), skin);
			nameLabel.setName(q.getDescription().toString());
			questTable.add(nameLabel).colspan(2).pad(2);
		}
	}

	/**
	 * adds the task list to the table with the corresponding colors and sizing
	 * 
	 * @param t
	 *            Task the task to add
	 */
	private void buildTaskRow(Task t, Table questTable)
	{
		if (t != null && questTable != null)
		{
			Label nameLabel = new Label(t.getName(), skin);
			nameLabel.setName(t.getName());
			taskButtonListener(nameLabel);
			// quest is enabled
			if (t.isActive() && t.isCompleted() == false)
			{
				nameLabel.setColor(ENABLED_COLOR);
				// quest is disabled
			} else if (t.isCompleted() == true)
			{
				nameLabel.setColor(COMPLETED_COLOR);
			} else
			{
				nameLabel.setColor(UNCOMPLETED_COLOR);
			}
			questTable.add(nameLabel).colspan(2).pad(2).padRight(5);
		}
	}

	/**
	 * gets the specified quest the list
	 * 
	 * @param questName
	 *            String the name of the quest
	 * @return Quest the quest from the list
	 */
	private Quest getQuestFromList(String questName)
	{
		Quest selectedQuest = null;
		// finds the quest
		if (qm.getQuestList() != null)
		{
			for (int i = 0; i < qm.getQuestList().size(); i++)
			{
				if (qm.getQuestList().get(i).getName().equals(questName))
				{
					selectedQuest = qm.getQuestList().get(i);
					break;
				}
			}
		}
		return selectedQuest;
	}

	/**
	 * attaches a listener to the quest label
	 * 
	 * @param questLabel
	 *            the Actor that we attach the listener
	 */
	private void questButtonListener(Actor questLabel)
	{
		questLabel.addListener(new ClickListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button)
			{

				System.out.println("Touched quest"
						+ event.getListenerActor().getName());
				Label questLbl = (Label) event.getListenerActor();
				String name = questLbl.getName();
				// TODO testing purposes
				qm.setQuestActive(selectedQuest);
				updateQuestTableFromClick(name);
				return true;
			}

		});
	}

	/**
	 * attaches a listener to the quest label
	 * 
	 * @param questLabel
	 *            the Actor that we attach the listener
	 */
	private void taskButtonListener(Actor taskLabel)
	{
		taskLabel.addListener(new ClickListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button)
			{

				System.out.println("Touched task"
						+ event.getListenerActor().getName());
				Label taskLbl = (Label) event.getListenerActor();
				String name = taskLbl.getName();

				selectedTask = selectedQuest.getTaskByName(name);
				selectedQuest.setSelectedTask(selectedTask);
				//TODO delete after show
				if (selectedTask.isActive() == true)
				{
					selectedQuest.completeCurrentTask();
				}
				selectedTask.activateTask(true);
				updateQuestTableFromTaskClick(name);
				return true;
			}
		});
	}

	/**
	 * Updates the table to show the tasks of the quest selected (this method is
	 * not private so it can be accessed by the listener)
	 * 
	 * @param name
	 *            String that holds the name of the quest selected
	 */
	void updateQuestTableFromClick(String name)
	{
		selectedQuest = getQuestFromList(name);
		Gdx.app.log("Selected quest is " + name, "down");
		builldQuestDisplayTable(selectedQuest, selectedTask);
		stage.clear();
		stage.addActor(background);
		logo.setX((float) (stage.getWidth() / 2 - (logo.getWidth() / 2)));
		logo.setY(stage.getHeight() - logo.getHeight());
		stage.addActor(logo);
		stage.addActor(questTable);
	}

	/**
	 * Updates the table to show the tasks of the quest selected (this method is
	 * not private so it can be accessed by the listener)
	 * 
	 * @param name
	 *            String that holds the name of the quest selected
	 */
	void updateQuestTableFromTaskClick(String name)
	{
		Gdx.app.log("Selected tasks is " + name, "down");
		builldQuestDisplayTable(selectedQuest, selectedTask);
		stage.clear();
		stage.addActor(background);
		logo.setX((float) (stage.getWidth() / 2 - (logo.getWidth() / 2)));
		logo.setY(stage.getHeight() - logo.getHeight());
		stage.addActor(logo);
		stage.addActor(questTable);
	}


	/**
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height)
	{

	}

	/**
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume()
	{
	}

	/**
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show()
	{

	}

	/**
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose()
	{
	}

	/**
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide()
	{
	}

	/**
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause()
	{

	}
}