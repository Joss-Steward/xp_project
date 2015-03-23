package view;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * A basic screen that displays the quests and adventure states.
 * @author ck4124
 *
 */
public class ScreenQAs extends ScreenBasic
{

	private Table questTable;
	private Table adventureTable;
	
	private Texture available;
	private Texture triggered;
	private Texture checkmark;
	private Texture complete;

	//Call Quest object to retrieve quests
	ArrayList <Label> questList;

	@SuppressWarnings("javadoc")
	@Override
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

	private void initializeAdventureTableContents(Skin skin) 
	{
		
		Label thing = new Label("Adventures", skin);
		Label desc = new Label("Get stuck in the bookshelf",skin);
		
		adventureTable = new Table(); 
		//Table Setup
		adventureTable.center().top();
		adventureTable.setFillParent(true);
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
		
		quest6.addListener(new ClickListener() {
			public void click() {
				//TODO - NEED TO ADD THE DATA FOR EACH ADVENTURE
			}
		});
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
