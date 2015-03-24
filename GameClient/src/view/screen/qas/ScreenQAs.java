package view.screen.qas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

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
	
	private Texture available;
	private Texture triggered;
	private Texture checkmark;
	private Texture complete;
	
	private Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	
	private TextButton display = new TextButton("Show", skin);
	
	SpriteBatch batch;
	ThisClientsPlayer myPlayer;
	
	boolean showing = true;
	
	public ScreenQAs()
	{
		this.show();
	}
	
	public boolean isShowing() {
		return showing;
	}
	
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
		Label thing = new Label("Adventures", skin);
		Label desc = new Label("Get stuck in the bookshelf",skin);
		
		adventureTable = new Table(); 
		//Table Setup
		adventureTable.center().top();
		
		//Table Layout
		adventureTable.add(thing).colspan(2).center();
		adventureTable.row();
		
		//Fill the table
		adventureTable.add(new Image(triggered));
		adventureTable.add(new Label("lksjladkgjso", skin));
		adventureTable.row();
		adventureTable.add(new Image(triggered));
		adventureTable.add(desc);
		adventureTable.row();
		adventureTable.add(new Image(complete));
		adventureTable.add(new Label("Lskjdg", skin));
		adventureTable.row();
		
		
	}

	private void initializeQuestTableContents(Skin skin)
	{
		
		available = new Texture("img/available.png");
		triggered = new Texture("img/triggered.png");
		checkmark = new Texture("img/check.png");
		complete = new Texture("img/complete.png");
		Texture legend = new Texture("img/legend.png");	
		Label quest = new Label("Quests",skin);
		
		TextButton quest1 = new TextButton("Quest 1", skin);
		TextButton quest2 = new TextButton("Quest 2", skin);
		TextButton quest3 = new TextButton("Quest 3", skin);
		TextButton quest4 = new TextButton("Quest 4", skin);
		TextButton quest5 = new TextButton("Quest 5", skin);
		TextButton quest6 = new TextButton("Quest 6", skin);
		
		
//		quest1.addListener(new ClickListener(){
//			@Override
//			public void clicked(InputEvent event, float x, float y){
//				ScreenBasic qa = Screens.QAS_SCREEN.getScreen();
//				((Game)Gdx.app.getApplicationListener()).setScreen(qa);
//			}
//		});
	
		Label quest7 = new Label("?????", skin);
		Label quest8 = new Label("?????", skin);
		Image legendImage = new Image(legend);
		questTable = new Table();
		
		//Table Setup
		questTable.setFillParent(true);	
		questTable.top().left();
		
		//Table Layout / mock data for Q/As
		questTable.add(quest).colspan(2).center();
		questTable.row();
		
		questTable.add(new Image(triggered));
		questTable.add(quest1);
		questTable.row();
		
		questTable.add(new Image(triggered));
		questTable.add(quest2);
		questTable.row();
		
		questTable.add(new Image(triggered));
		questTable.add(quest3);	
		questTable.row();
		
		questTable.add(new Image(checkmark));
		questTable.add(quest4);	
		questTable.row();
		
		questTable.add(new Image(checkmark));
		questTable.add(quest5);	
		questTable.row();
		
		questTable.add(new Image(complete));
		questTable.add(quest6);	
		questTable.row();
		
		questTable.add(new Image(available));
		questTable.add(quest7);	
		questTable.row();
		
		questTable.add(new Image(available));
		questTable.add(quest8);			
		
		//Set the Legend at the bottom of the Quests Table
		questTable.row();
		questTable.add(legendImage).colspan(2).center();
	}

}