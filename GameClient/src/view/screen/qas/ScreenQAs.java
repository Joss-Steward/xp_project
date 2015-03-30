package view.screen.qas;


import java.util.ArrayList;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import datasource.AdventureStateEnum;
import datasource.QuestStateEnum;
import edu.ship.shipsim.client.model.CommandSendQuestState;
import edu.ship.shipsim.client.model.ModelFacade;
import edu.ship.shipsim.client.model.ThisClientsPlayer;
import edu.ship.shipsim.client.model.reports.QuestStateReport;

/**
 * A basic screen that displays the quests and adventure states.
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
	
	private final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	
	SpriteBatch batch;
	ThisClientsPlayer myPlayer;
	
	boolean showing = true;
	
	private ArrayList<ClientPlayerQuest> questList = new ArrayList<ClientPlayerQuest>();
	
	/**
	 * Basic constructor. will call show() to initialize all the data in the tables.
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
		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
		cm.registerObserver(this, QuestStateReport.class);
	}
	
	/**
	 * Is the quest table on the screen
	 * @return showing ; is there quests currently displaying on the screen
	 */
	public boolean isShowing() {
		return showing;
	}
	
	/**
	 * Toggle the invisibility of the quest list
	 */
	public void toggleVisible() {
		if (isShowing()) {
			showing = false;
			this.addAction(Actions.moveTo(-this.getWidth(), 0, .3f));
		} else {
			showing = true;
			this.addAction(Actions.moveTo(0, 0, .3f));
			
			CommandSendQuestState cmd = new CommandSendQuestState();
			
			ModelFacade.getSingleton().queueCommand(cmd);
		}
	}

	/**
	 * initializes table contents and displays the quests and 
	 * adventures table
	 */
	public void show()
	{	
		this.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		initializeQuestTableContents();
		initializeAdventureTableContents();

		this.addActor(questTable);
		this.addActor(adventureTable);
		
		toggleVisible();
	} 

	private void initializeAdventureTableContents() 
	{
		//Table Setup
		adventureTable = new Table(); 
		adventureTable.setFillParent(true);
		adventureTable.center().top();
		clearAdventureTable();
	}


	/**
	 * TODO right comment
	 * @param quests the list of ClientPlayerQuests quest from ThisClientPlayer
	 */
	public void updateTable(ArrayList<ClientPlayerQuest> quests) 
	{
		questTable.clearChildren();
		questTable.add(q_header).colspan(2).center();
		questTable.row();
		
		int numAvailable = 0;
		
		for(ClientPlayerQuest q : quests) 
		{
			if(q.getQuestState().equals(QuestStateEnum.TRIGGERED))
			{
				buildQuestRow(q);
			}
			else if(!q.getQuestState().equals(QuestStateEnum.AVAILABLE))
			{
				buildQuestRow(q);
			}
			else
			{
				//Increment the number of quests available to find
				numAvailable++;
			}
		}		
		
		//Show how many quests are available to be found
		questTable.add(new Label(""+numAvailable,skin));
		questTable.add(new Label("?????", skin));	
		//Set the Legend at the bottom of the Quests Table
		questTable.row();
		questTable.add(new Image(legend)).colspan(2).center();
	}
	
	private void initializeQuestTableContents()
	{
		
		//available = new Texture("img/available.png");
		triggered = new Texture("img/triggered.png");
		checkmark = new Texture("img/check.png");
		complete = new Texture("img/complete.png");
		legend = new Texture("img/legend.png");	
		q_header = new Label("Quests",skin);		
		
		int numAvailable = 0;
		//Table Setup
		questTable = new Table();
		questTable.setFillParent(true);	
		questTable.top().left();
		questTable.add(q_header).colspan(2).center();
		questTable.row();
	
		//Show how many quests are available to be found
		questTable.add(new Label(""+numAvailable,skin));
		questTable.add(new Label("?????", skin));	
		//Set the Legend at the bottom of the Quests Table
		questTable.row();
		questTable.add(new Image(legend)).colspan(2).center();
		
		clearQuestTable();
	}

	private void buildAdvRow(Texture state, String desc)
	{
		adventureTable.add(new Image(state));
		adventureTable.add(new Label(desc,skin));
		adventureTable.row();	
	}

	private void buildQuestRow(final ClientPlayerQuest quest)
	{
		QuestStateEnum state = quest.getQuestState();
		
		switch(state) {
			case TRIGGERED:
				questTable.add(new Image(triggered));
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
		
		TextButton button = new TextButton(quest.getQuestDescription(),skin);
		questTable.add(button);
		
		button.addListener(new ClickListener(){
			@Override 
			public void clicked(InputEvent event, float x, float y) 
			{
				clearAdventureTable();
				for(ClientPlayerAdventure a : quest.getAdventureList()) 
				{
					if(a.getAdventuretState().equals(AdventureStateEnum.PENDING))
					{
						buildAdvRow(triggered, a.getAdventureDescription());
					}

					else
					{
						buildAdvRow(complete, a.getAdventureDescription());
					}
				}
			}
		});
		
		questTable.row();
	}
	
	private void clearAdventureTable() 
	{
		Label header = new Label("Adventures", skin);	
		adventureTable.clearChildren();
		//Set Header
		adventureTable.add(header).colspan(2).center();
		adventureTable.row();
	}
	
	private void clearQuestTable() 
	{
		Label header = new Label("Quests", skin);	
		questTable.clearChildren();
		//Set Header
		questTable.add(header).colspan(2).center();
		questTable.row();
	}

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report) 
	{
		if(report.getClass().equals(QuestStateReport.class))
		{
			QuestStateReport r = (QuestStateReport) report;
			questList = r.getClientPlayerQuestList();
			updateTable(questList);
		}
		
	}

	/**
	 * Set the visibility of the QAScreen to the given boolean
	 * @param statement boolean given for showing
	 */
	public void setVisibility(boolean statement)
	{
		showing = statement;
	}
}