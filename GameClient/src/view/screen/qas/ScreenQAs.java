package view.screen.qas;

import java.util.ArrayList;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;
import view.screen.ScreenBasic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import datasource.AdventureStateEnum;
import datasource.QuestStateEnum;
import edu.ship.shipsim.client.model.PlayerManager;

/**
 * A basic screen that displays the quests and adventure states.
 * @author ck4124
 *
 */
public class ScreenQAs extends ScreenBasic
{

	private Table questTable;
	private Table adventureTable;
	
	private Texture triggered;
	private Texture checkmark;
	private Texture complete;

	//Call Quest object to retrieve quests
	ArrayList <Label> questList;

	@SuppressWarnings("javadoc")
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		stage.act();
		stage.draw();
		Table.drawDebug(stage);

	}

	@SuppressWarnings("javadoc")
	@Override
	public void resize(int width, int height)
	{
		stage.getViewport().update(width, height);
		stage.act();
	}

	@SuppressWarnings("javadoc")
	@Override
	public void show()
	{
		final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));

		Viewport v = new ScreenViewport();
		stage = new Stage(v);
		
		initializeQuestTableContents(skin);
		initializeAdventureTableContents(skin);

		stage.addActor(questTable);
		stage.addActor(adventureTable);
		
		Gdx.input.setInputProcessor(stage);

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
				clearAdventureTable();
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
	
	private void initializeAdventureTableContents(final Skin skin) 
	{

		//Table Setup
		Label header = new Label("Adventures", skin);		
		adventureTable = new Table(); 
		adventureTable.center().top();
		adventureTable.setFillParent(true);
		
		//Table Layout
		adventureTable.add(header).colspan(2).center();
		adventureTable.row();
		
//		//Fill the table
//		buildAdvRow(triggered,"Adventure 1 description",skin);
//		buildAdvRow(triggered,"Adventure 2 description",skin);
//		buildAdvRow(complete,"Adventure 3 description",skin);
		
	}
	
	private void clearAdventureTable() 
	{
		adventureTable.clearChildren();
	}
	
	private void initializeQuestTableContents(Skin skin)
	{
		// Place Holder
		ArrayList<ClientPlayerQuest> quests = new ArrayList<ClientPlayerQuest>(PlayerManager.getSingleton().getThisClientsPlayer().getQuests());
		
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
		
		for(ClientPlayerQuest q : quests) 
		{
			if(!q.getQuestState().equals(QuestStateEnum.AVAILABLE))
			{
				buildQuestRow(q, skin);
			}
		}		
		
//////////////////////////////////////////////////////////////////
//		Table Layout / mock data for Q/As
//		buildQuestRow(triggered,"Quest1",skin);
//		buildQuestRow(triggered,"Quest2",skin);
//		buildQuestRow(triggered,"Quest3",skin);
//		buildQuestRow(checkmark,"Quest4",skin);
//		buildQuestRow(checkmark,"Quest5",skin);
//		buildQuestRow(complete,"Quest6",skin);
//////////////////////////////////////////////////////////////////
		
		//Show how many quests are available to be found
		questTable.add(new Label(""+num_Avail,skin));
		questTable.add(new Label("?????", skin));	
		//Set the Legend at the bottom of the Quests Table
		questTable.row();
		questTable.add(new Image(legend)).colspan(2).center();
	}


	@SuppressWarnings("javadoc")
	@Override
	public void hide() 
	{
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("javadoc")
	@Override
	public void pause() 
	{
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("javadoc")
	@Override
	public void resume() 
	{
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("javadoc")
	@Override
	public void dispose() 
	{
		// TODO Auto-generated method stub

	}

}
