package view.screen.qas;


import java.util.ArrayList;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;

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
import edu.ship.shipsim.client.model.PlayerManager;
import edu.ship.shipsim.client.model.ThisClientsPlayer;

/**
 * A basic screen that displays the quests and adventure states.
 * @author ck4124
 *
 */
public class ScreenQAs extends Group
{
	private Table questTable;
	private Table adventureTable;
	
	private Texture triggered;
	private Texture checkmark;
	private Texture complete;
	
	private Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	
	SpriteBatch batch;
	ThisClientsPlayer myPlayer;
	
	boolean showing = true;
	
	/**
	 * Basic constructor. will call show() to initialize all the data in the tables.
	 */
	public ScreenQAs()
	{
		this.show();
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
		}
	}

	@SuppressWarnings("javadoc")
	public void show()
	{	
		this.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		initializeQuestTableContents(skin);
		initializeAdventureTableContents(skin);

		adventureTable.setFillParent(true);
		this.addActor(questTable);
		this.addActor(adventureTable);
		
		toggleVisible();
	} 

	private void initializeAdventureTableContents(Skin skin) 
	{
		//Table Setup
		adventureTable = new Table(); 
		adventureTable.center().top();
		adventureTable.setFillParent(true);	
		clearAdventureTable(skin);
	}

	private void initializeQuestTableContents(Skin skin)
	{
		// Place Holder
		//ArrayList<ClientPlayerQuest> quests = new ArrayList<ClientPlayerQuest>(PlayerManager.getSingleton().getThisClientsPlayer().getQuests());
		
		//available = new Texture("img/available.png");
		triggered = new Texture("img/triggered.png");
		checkmark = new Texture("img/check.png");
		complete = new Texture("img/complete.png");
		Texture legend = new Texture("img/legend.png");	
		Label q_header = new Label("Quests",skin);		
		int num_Avail = 0;
		
		//Table Setup
		questTable = new Table();
		questTable.setFillParent(true);	
		questTable.top().left();
		questTable.add(q_header).colspan(2).center();
		questTable.row();
		
		/*for(ClientPlayerQuest q : quests) 
		{
			if(!q.getQuestState().equals(QuestStateEnum.AVAILABLE))
			{
				buildQuestRow(q, skin);
			}
			else
			{
				//Increment the number of quests available to find
				num_Avail++;
			}
		}		
		*/
		
		//Show how many quests are available to be found
		questTable.add(new Label(""+num_Avail,skin));
		questTable.add(new Label("?????", skin));	
		//Set the Legend at the bottom of the Quests Table
		questTable.row();
		questTable.add(new Image(legend)).colspan(2).center();
	}

	private void buildAdvRow(Texture state, String desc, final Skin row_skin)
	{
		adventureTable.add(new Image(state));
		adventureTable.add(new Label(desc,row_skin));
		adventureTable.row();	
	}

	private void buildQuestRow(final ClientPlayerQuest quest, final Skin row_skin)
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
		
		TextButton button = new TextButton(quest.getQuestDescription(),row_skin);
		questTable.add(button);
		
		button.addListener(new ClickListener(){
			@Override 
			public void clicked(InputEvent event, float x, float y) 
			{
				clearAdventureTable(row_skin);
				for(ClientPlayerAdventure a : quest.getAdventureList()) 
				{
					if(a.getAdventuretState().equals(AdventureStateEnum.PENDING))
					{
						buildAdvRow(triggered, a.getAdventureDescription(), row_skin);
					}

					else
					{
						buildAdvRow(complete, a.getAdventureDescription(), row_skin);
					}
				}
			}
		});
		
		questTable.row();
	}
	
	private void clearAdventureTable(Skin skin) 
	{
		Label header = new Label("Adventures", skin);	
		adventureTable.clearChildren();
		//Set Header
		adventureTable.add(header).colspan(2).center();
		adventureTable.row();
	}
}