package view.screen.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ObjectFloatMap;

import communication.ConnectionManager;
import runners.GameLibGDX;
import view.screen.ScreenBasic;
import view.screen.Screens;
import view.screen.SkinPicker;
import view.screen.highscore.HighScoreUI;
import view.screen.playerinfo.PlayerInfoUI;
import view.screen.popup.LogoutNotificationBehavior;
import view.screen.popup.ScreenPopUp;
import view.screen.popup.TwoChoiceScreenPopup;
import view.screen.qas.QuestUI;

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
	
	private Button QuestAdventureBtn;
	private Button PlayerUIBtn;
	private Button HighScoreBtn;
	private Button LogoutBtn;
	
	private HighScoreUI highScoreUI;
	private QuestUI qaScreen;
	private PlayerInfoUI playerInfoUI;
	private Stage stage;
	

	/**
	 * Create a new chat ui that displays at the bottom of the screen
	 * @param qaScreen2 Quest menu to edit
	 * @param highScoreUI score UI to edit
	 * @param chatArea chat UI to edit
	 * @param playerInfoUI player uI to edit
	 * @param stage 
	 */
	public MenuUI(HighScoreUI highScoreUI, QuestUI qaScreen2, ChatUi chatArea, PlayerInfoUI playerInfoUI, Stage stage)
	{
		this.highScoreUI = highScoreUI;
		this.qaScreen = qaScreen2;
		this.playerInfoUI = playerInfoUI;
		this.stage = stage;
		setupUI();
	}
	
	/**
	 * Prepare the actual ui elements for the display
	 */
	private void setupUI()
	{
		skin = SkinPicker.getSkinPicker().getCrewSkin();
		
		Table tabs = new Table(skin);
		tabs.setWidth(800f);
		tabs.setHeight(800f);
		
		tabs.top();
		{
			//Quest/Adventure Button
			{
				ButtonStyle style = skin.get(ButtonStyle.class);
				
				QuestAdventureBtn = new Button(style);
				QuestAdventureBtn.add(new Label("Q", skin));
				QuestAdventureBtn.addListener(new ChangeListener(){
					@Override
					public void changed(ChangeEvent event, Actor actor)
					{
						if(highScoreUI.isHighScoreScreenShowing()) //Toggle the HS screen if it is open
						{
							toggleHSButton();
						}
						
						if(playerInfoUI.isPlayerInfoScreenShowing()) //Toggle the Player info screen if it is open
						{
							togglePlayerUIButton();
						}
						qaScreen.toggleQAScreenVisible(); //Toggle the QA screen
						
					}

					
				});
				tabs.add(QuestAdventureBtn).size(32f);
			}
			//High scores button
			{
				ButtonStyle style = skin.get( ButtonStyle.class);
				HighScoreBtn = new Button(style);
				HighScoreBtn.add(new Label("H", skin));
				HighScoreBtn.addListener(new ChangeListener(){
					@Override
					public void changed(ChangeEvent event, Actor actor)
					{
						if(qaScreen.isQAScreenShowing()) //Toggle the Q/A screen if it is already open
						{
							toggleQAButton(); 
						}
						
						if(playerInfoUI.isPlayerInfoScreenShowing()) //Toggle the player Info screen if it is already open
						{
							togglePlayerUIButton();
						}
						highScoreUI.toggleHSScreenVisible(); //Toggle the HS screen
					}	
				});
				tabs.add(HighScoreBtn).size(32f);
			
			}
			
			//Player UI Button
			{
				ButtonStyle style = skin.get(ButtonStyle.class);
				PlayerUIBtn = new Button(style);
				PlayerUIBtn.add(new Label("P", skin));
				PlayerUIBtn.addListener(new ChangeListener(){
					@Override
					public void changed(ChangeEvent event, Actor actor)
					{
						if(qaScreen.isQAScreenShowing()) //Toggle the QA screen if it is already open
						{
							toggleQAButton();
						}
						
						if(highScoreUI.isHighScoreScreenShowing()) //Toggle the HS screen if it is already open
						{
							
							toggleHSButton();
						}
						
						playerInfoUI.togglePlayerInfoScreen(); // actually open the player info screen
					}	
				});
				tabs.add(PlayerUIBtn).size(32f);
			}
			
			//Logout Button
			{
				ButtonStyle style = skin.get(ButtonStyle.class);
				LogoutBtn = new Button(style);
				LogoutBtn.add(new Label("Logout", skin));
				LogoutBtn.addListener(new ChangeListener(){
					@Override
					public void changed(ChangeEvent event, Actor actor)
					{	
						//ScreenBasic screen = Screens.LOGIN_SCREEN.getScreen();
						new TwoChoiceScreenPopup("Are you sure you want to logout?", "Logout", "Cancel", stage , new LogoutNotificationBehavior(), null);
					}	
				});
				tabs.add(LogoutBtn).size(80f, 32f);
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
	 * 	Toggles the PlayerUI button on & off
	 */
	public void togglePlayerUIButton() 
	{
		if(!PlayerUIBtn.isChecked())
		{
			PlayerUIBtn.setChecked(true);
		}
		else
		{
			PlayerUIBtn.setChecked(false);
		}
							
	}	
	/**
	 * @return the button for the Quest and adventure screen to press
	 */
	public Button getQuestAdventureBtn()
	{
		return QuestAdventureBtn;
	}
	
	/**
	 * @return the button for the HighScore screen to press
	 */
	public Button getHighScoreBtn()
	{
		return HighScoreBtn;
	}
	
	/**
	 * @return the button for the player UI
	 */
	public Button getPlayerUIBtn()
	{
		return PlayerUIBtn;
	}
}