package view.screen.qas;

import java.util.ArrayList;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import datasource.AdventureStateEnum;
import datasource.QuestStateEnum;
import edu.ship.shipsim.client.model.CommandSendQuestState;
import edu.ship.shipsim.client.model.ModelFacade;
import edu.ship.shipsim.client.model.reports.QuestStateReport;

/**
 * A basic screen that displays the quests and adventure states.
 * 
 * @author ck4124
 *
 */
public class ScreenQAs extends Group implements QualifiedObserver
{
	private Table questTable;
	private Table adventureTable;

	private Texture triggered;
	private Texture checkmark;
	private Texture complete;
	private Texture legend;
	
	private Label q_header;
	private Label adv_header;
	
	private float fontScale = (float) 1.25;

	
	private final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));

	boolean showing = true;

	private ArrayList<ClientPlayerQuest> questList = new ArrayList<ClientPlayerQuest>();

	/**
	 * Basic constructor. will call show() to initialize all the data in the
	 * tables.
	 */
	public ScreenQAs()
	{
		this.show();
		setUpListening();
	}

	/**
	 * Sets up the QualifiedObserver for QuestStateReport
	 */
	public void setUpListening()
	{
		QualifiedObservableConnector cm = QualifiedObservableConnector
				.getSingleton();
		cm.registerObserver(this, QuestStateReport.class);
	}

	/**
	 * Is the quest table on the screen
	 * 
	 * @return showing ; is there quests currently displaying on the screen
	 */
	public boolean isShowing()
	{
		return showing;
	}

	/**
	 * Toggle the invisibility of the quest list
	 */
	public void toggleVisible()
	{
		if (isShowing())
		{
			showing = false;
			this.addAction(Actions.moveTo(-this.getWidth(), 0, .3f));
		} else
		{
			showing = true;
			this.addAction(Actions.moveTo(0, 0, .3f));

			CommandSendQuestState cmd = new CommandSendQuestState();

			ModelFacade.getSingleton().queueCommand(cmd);
		}
	}

	/**
	 * initializes table contents and displays the quests and adventures table
	 */
	public void show()
	{
		this.setSize(Gdx.graphics.getWidth()*.75f, Gdx.graphics.getHeight());
		

		initializeQuestTableContents();
		initializeAdventureTableContents();
		
		this.addActor(questTable);
		this.addActor(adventureTable);
		
		toggleVisible();
	}

	/**
	 * initializes the table contents for the adventures within the selected
	 * quest
	 */
	private void initializeAdventureTableContents()
	{
		// Table Setup
		adventureTable = new Table();
		adventureTable.setFillParent(true);
		adv_header = new Label("", skin);
		adv_header.setFontScale(fontScale);
		adventureTable.right().top();
		clearAdventureTable();
	}

	/**
	 * Update the quest table with whatever we retrieve from the model regarding
	 * this client player's quests and their states.
	 * 
	 * @param quests
	 *            the list of ClientPlayerQuests quest from ThisClientPlayer
	 */
	public void updateTable(ArrayList<ClientPlayerQuest> quests)
	{
		questTable.setBackground(new NinePatchDrawable(getNinePatch("data/background.9.png")));
		questTable.clearChildren();
		questTable.add(q_header).colspan(2).center();		
		questTable.row();

		int numAvailable = 0;

		for (ClientPlayerQuest q : quests)
		{
			if (q.getQuestState().equals(QuestStateEnum.TRIGGERED))
			{
				buildQuestRow(q);
			} else if (!q.getQuestState().equals(QuestStateEnum.AVAILABLE))
			{
				buildQuestRow(q);
			} else
			{
				// Increment the number of quests available to find
				numAvailable++;
			}
		}

		// Show how many quests are available to be found
		Label q_avail = new Label(""+ numAvailable, skin);
		q_avail.setFontScale(fontScale);
		questTable.add(q_avail);
		Label huh = new Label("?????", skin);
		huh.setFontScale(fontScale);
		questTable.add(huh);
		// Set the Legend at the bottom of the Quests Table
		questTable.row();
		questTable.add(new Image(legend)).colspan(2).center();
	}

	/**
	 * initializes the table contents for the QA screen
	 */
	private void initializeQuestTableContents()
	{
		triggered = new Texture("img/triggered.png");
		checkmark = new Texture("img/check.png");
		complete = new Texture("img/complete.png");
		legend = new Texture("img/legend.png");
		q_header = new Label("Quests", skin);
		q_header.setFontScale(fontScale);
		
		int numAvailable = 0;
		// Table Setup
		questTable = new Table();
		questTable.setFillParent(true);
		
		questTable.top().left();
		questTable.add(q_header).colspan(2).center();
		questTable.row();

		// Show how many quests are available to be found
		questTable.add(new Label("" + numAvailable, skin));
		questTable.add(new Label("?????", skin));
		// Set the Legend at the bottom of the Quests Table
		questTable.row();
		questTable.add(new Image(legend)).colspan(2).center();

		clearQuestTable();
	}

	/**
	 * builds an individual row with the appropriate radio button and its
	 * description
	 * 
	 * @param state
	 *            the state of which the adventure is in
	 * @param description
	 *            of the adventure
	 */
	private void buildAdvRow(Texture state, String desc, Integer reward)
	{
		Label t_reward = new Label("XP: "+reward+" ",skin);
		Label temp = new Label(desc+"  ",skin);
		t_reward.setColor(Color.GREEN);
		t_reward.setScale((float)1.1);
		adventureTable.add(new Image(state));
		adventureTable.add(temp);
		adventureTable.add(t_reward);
		adventureTable.row();
	}

	/**
	 * sets the image for the quest (dependent on state) and its description
	 * 
	 * @param quest
	 *            of the client
	 */
	private void buildQuestRow(final ClientPlayerQuest quest)
	{
		QuestStateEnum state = quest.getQuestState();

		switch (state)
		{
		case TRIGGERED:
			questTable.add(new Image(new NinePatch(triggered)));
			break;
		case FULFILLED:
			questTable.add(new Image(checkmark));
			break;
		case FINISHED:
			questTable.add(new Image(complete));
			break;
		default:
			// Available quests don't have an image in this column.
			// Hidden quests aren't available for the client to see.
			break;
		}

		TextButton button = new TextButton(quest.getQuestDescription(), skin);
		button.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent evt, float x, float y)
			{
				int num_left = quest.getAdventuresToFulfillment();
				clearAdventureTable();
				
				for (ClientPlayerAdventure a : quest.getAdventureList())
				{
					if (a.getAdventureState().equals(
							AdventureStateEnum.PENDING))
					{
						buildAdvRow(triggered, a.getAdventureDescription(),a.getAdventureXP());
					} else
					{
						buildAdvRow(complete, a.getAdventureDescription(),a.getAdventureXP());
						num_left--;
					}
				}
				q_header.setText(quest.getQuestDescription()+" XP: "+quest.getExperiencePointsGained());
				adv_header.setText(""+num_left+ " to Fulfillment");
			}
		});

		questTable.add(button);
		questTable.row();
	}

	/**
	 * clears all contents in adventure table and reinitializes
	 */
	private void clearAdventureTable()
	{
		adventureTable.clearChildren();
		// Set Header
		adventureTable.add(adv_header).colspan(2).center();
		adventureTable.row();
	}

	/**
	 * clears all contents in quest table and reinitializes
	 */
	private void clearQuestTable()
	{
		questTable.clearChildren();
		// Set Header
		questTable.add(q_header).colspan(2).center();
		questTable.row();
	}

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		if (report.getClass().equals(QuestStateReport.class))
		{
			QuestStateReport r = (QuestStateReport) report;
			questList = r.getClientPlayerQuestList();
			updateTable(questList);
		}

	}

	/**
	 * Set the visibility of the QAScreen to the given boolean
	 * 
	 * @param statement
	 *            boolean given for showing
	 */
	public void setVisibility(boolean statement)
	{
		showing = statement;
	}

	private NinePatch getNinePatch(String fileName)
	{
		// get the image
		final Texture t = new Texture(Gdx.files.internal(fileName));
		return new NinePatch(new TextureRegion(t));
	}
}
