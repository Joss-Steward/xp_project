package view.screen.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectFloatMap;

import data.ChatType;
import model.ClientModelFacade;
import model.CommandChatMessageSent;
import view.screen.highscore.HighScoreUI;
import view.screen.qas.ScreenQAs;

/**
 * MenuUI.java
 * UI for the Menu
 * @author Zachary & Abdul
 *
 */
public class MenuUI extends Group
{
	/**
	 * Expected height of the chat ui
	 */
	public static final float YSIZE = 200f;
	
	private Skin skin;
	
	private static final float FadeRate = .25f;
	private ObjectFloatMap<Label> newLabels;
	
	private ImageButton QuestAdventureBtn;
	private ImageButton ChatBtn;
	private ImageButton HighScoreBtn;
	
	private HighScoreUI highScoreUI;
	private ScreenQAs qaScreen;
	private ChatUi chatArea;
	
	/**
	 * Create a new chat ui that displays at the bottom of the screen
	 * @param qaScreen Quest menu to edit
	 * @param highScoreUI score UI to edit
	 * @param chatArea chat UI to edit
	 */
	public MenuUI(HighScoreUI highScoreUI, ScreenQAs qaScreen, ChatUi chatArea)
	{
		this.highScoreUI = highScoreUI;
		this.qaScreen = qaScreen;
		this.chatArea = chatArea;
		setupUI();
	}
	
	/**
	 * Prepare the actual ui elements for the display
	 */
	private void setupUI()
	{
		skin = new Skin(Gdx.files.internal("data/ui/chat.json"));
		
		Table tabs = new Table();
		tabs.setWidth(800f);
		tabs.setHeight(800f);
		
		tabs.top();
		{
			//Quest/Adventure Button
			{
				ImageButtonStyle style = skin.get("all", ImageButtonStyle.class);
			
				QuestAdventureBtn = new ImageButton(style);
				QuestAdventureBtn.addListener(new ChangeListener(){
					@Override
					public void changed(ChangeEvent event, Actor actor)
					{
						if(highScoreUI.isHighScoreScreenShowing())
						{
						
							toggleHSButton();
						}
						
						qaScreen.toggleQAScreenVisible();
					}	
				});
				tabs.add(QuestAdventureBtn).size(32f);
			}
			//High scores button
			{
				ImageButtonStyle style = skin.get("local", ImageButtonStyle.class);
				HighScoreBtn = new ImageButton(style);
				HighScoreBtn.addListener(new ChangeListener(){
					@Override
					public void changed(ChangeEvent event, Actor actor)
					{
						if(qaScreen.isQAScreenShowing())
						{
							toggleQAButton();
						}
						
						highScoreUI.toggleHSScreenVisible();
					}	
				});
				tabs.add(HighScoreBtn).size(32f);
			
			}
			
			
			//Chat button
			{
				ImageButtonStyle style = skin.get("zone", ImageButtonStyle.class);
				ChatBtn = new ImageButton(style);
				ChatBtn.addListener(new ChangeListener(){
					@Override
					public void changed(ChangeEvent event, Actor actor)
					{

					}	
				});
				tabs.add(ChatBtn).size(32f);
			}
		}
				
		tabs.top();
		//add the ui to the stage
		addActor(tabs);
		newLabels = new ObjectFloatMap<Label>();
	}
	
	/**
	 * Updates the ui
	 * @param delta
	 * 	timer resolution between draw cycles
	 */
	@Override
	public void act(float delta)
	{
		super.act(delta);
		
		for (Label label : newLabels.keys())
		{
			float fade = newLabels.get(label, 0f);
			fade += delta;
			Color c = label.getColor();
			c.a = fade/FadeRate;
			newLabels.put(label, fade);
			
			if (fade > FadeRate)
			{
				newLabels.remove(label, 0f);
			}
		}
	}

	/**
	 *  Toggles the Quest/Adventure Button to on & off
	 */
	public void toggleQAButton() 
	{
		if(!QuestAdventureBtn.isChecked())
		{
			QuestAdventureBtn.setChecked(true);
		}
		else
		{
			QuestAdventureBtn.setChecked(false);
		}
		
	}
	
	/**
	 * Toggles the High score button on & off
	 */
	public void toggleHSButton() 
	{
		if(!HighScoreBtn.isChecked())
		{
			HighScoreBtn.setChecked(true);
		}
		else
		{
			HighScoreBtn.setChecked(false);
		}
		
	}
	
	/**
	 * @return the button to press
	 */
	public Button getQuestAdventureBtn()
	{
		return QuestAdventureBtn;
	}
	
	/**
	 * @return the button to press
	 */
	public Button getHighScoreBtn()
	{
		return HighScoreBtn;
	}
}
