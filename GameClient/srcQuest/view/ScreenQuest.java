package view;

import java.util.ArrayList;
import java.util.List;

import model.CommandQuestScreenClose;
import model.ModelFacade;
import Quest.Quest;
import Quest.QuestSystemLargeTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.esotericsoftware.tablelayout.Cell;

import view.ScreenBasic;

/**
 * QuestScreen
 * 
 * @author Joshua
 * 
 */
public class ScreenQuest extends ScreenBasic
{

	int width;
	int height;
	private Stage stage;
	private Texture texture;
	private TextureRegion region;
	private Image background;
	private OrthographicCamera camera;
	private Table questTable;
	final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	private ArrayList<Quest> quests;

	/**
	 * 
	 */
	public ScreenQuest()
	{
		// initialize variables
		quests = new ArrayList<Quest>();

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		// builds stage and camera
		stage = new Stage();
		camera = new OrthographicCamera();
		camera.setToOrtho(true, width, height);
		camera.update();
		stage.setCamera(camera);
		// builds the quest screen background
		texture = new Texture("data/journal.png");
		region = new TextureRegion(texture, 0, 0, width, height);
		background = new Image(region);
		stage.addActor(background);
		// builds the questTable
		getQuestForDebug();
		builldQuestDisplayTable();
		stage.addActor(questTable);

	}

	/**
	 * gets the quest system already built for testing purposes
	 */
	private void getQuestForDebug()
	{
		this.quests = QuestSystemLargeTest.buildQuestSystem();

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

	/**
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta)
	{

		Gdx.graphics.getGL10().glClearColor(0, 0, 0, 1);
		Gdx.graphics.getGL10().glClear(
				GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		stage.act();
		stage.draw();

		// q key is touched and closes the quest screen
		if (Gdx.input.isKeyPressed(Keys.Q))
		{
			System.out.println("\n Return to map button is pressed \n");
			CommandQuestScreenClose lc = new CommandQuestScreenClose();
			ModelFacade.getSingleton(false).queueCommand(lc);
		}

		// clicked on one of the labels
		if (Gdx.input.isButtonPressed(Buttons.LEFT))
		{
			Actor temp = questTable.hit(Gdx.input.getX(), Gdx.input.getY(),
					true);
			if (temp != null)
				System.out.println("Touched" + temp.getName());

			if (temp != null && temp.getClass() == Label.class)
			{
				// testing removing the thing that was clicked
				Label questLabel = (Label) temp;
				addTasksToTable(questLabel.getText().toString(), questLabel);
			}
		}
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
			this.quests = quests;
		}
	}

	/**
	 * Builds the table to hold the quest and task data
	 */
	private void builldQuestDisplayTable()
	{
		questTable = new Table();
		questTable.setFillParent(true);
		questTable.debug(); // Used to view table grid lines
		// puts quest names into the table
		if (quests != null)
		{
			for (int i = 0; i < quests.size(); i++)
			{
				questTable.row();
				Label nameLabel = new Label(quests.get(i).getName(), skin);
				nameLabel.setName(nameLabel.getText().toString());
				questTable.add(nameLabel);
			}
		}
	}

	/**
	 * adds rows to the table where
	 * 
	 * @param questName
	 * @param questLabel
	 */
	private void addTasksToTable(String questName, Actor questLabel)
	{
		Cell<?> startingCell = null;
		List<?> cells = questTable.getCells();
		// walks through the cells looking for the label that was touched
		if (questTable.findActor(questName) == questLabel)
		{
			startingCell = questTable.getCell(questLabel);
		}

		Quest selectedQuest = null;

		// finds the quest
		if (quests != null)
		{
			for (int i = 0; i < quests.size(); i++)
			{
				if (quests.get(i).getName().equals(questName))
				{
					selectedQuest = quests.get(i);
					break;
				}
			}
		}
		// walks through each task
		if (selectedQuest != null)
		{
			int start = cells.indexOf(startingCell);
			for (int i = 0; i < selectedQuest.getTaskCount(); i++)
			{
				Label nameLabel = new Label(selectedQuest.getTask(i).getName(),
						skin);
				nameLabel.setName(nameLabel.getText().toString());
				questTable.addActorAt(i + start, nameLabel);
				// fills in blank spaces
				if (i > cells.size())
				{
					questTable.row();
					questTable.add(new Actor());
				}

			}
		}
	}
}
