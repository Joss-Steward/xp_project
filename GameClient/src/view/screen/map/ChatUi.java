package view.screen.map;

import model.CommandChatMessageSent;
import model.ModelFacade;

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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectFloatMap;

import data.ChatType;

/**
 * 
 * @author nhydock
 *
 */
public class ChatUi extends Group
{
	/**
	 * Expected height of the chat ui
	 */
	public static final float YSIZE = 200f;
	
	//chat historys
	Array<String> allHistory;
	Array<String> zoneHistory;
	Array<String> localHistory;
	
	Array<String> activeHistory;
	
	ScrollPane listPane;
	Table chatHistoryView;
	TextField messageBox;
	
	private Skin skin;
	
	private static final float FadeRate = .25f;
	private ObjectFloatMap<Label> newLabels;
	
	/**
	 * Create a new chat ui that displays at the bottom of the screen
	 */
	public ChatUi()
	{
		allHistory = new Array<String>();
		localHistory = new Array<String>();
		zoneHistory = new Array<String>();
		
		setupUI();
	}
	
	/**
	 * Prepare the actual ui elements for the display
	 */
	private void setupUI()
	{
		skin = new Skin(Gdx.files.internal("data/ui/chat.json"));
		
		Table grid = new Table();
		grid.setWidth(800f);
		
		//create text box for typing messages
		messageBox = new TextField("", skin);
		//create the message button
		ImageButtonStyle sendButtonStyle = skin.get("submit", ImageButtonStyle.class);
		Button sendButton = new ImageButton(sendButtonStyle);
		
		//add button listener
		sendButton.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				sendMessage();
			}
			
		});
		
		//create chat log area
		activeHistory = allHistory;
		chatHistoryView = new Table();
		listPane = new ScrollPane(chatHistoryView, skin);
		
		//create chat filter buttons
		ButtonGroup tabGroup = new ButtonGroup();
		Table tabs = new Table();
		tabs.top();
		{
			//all button
			{
				ImageButtonStyle style = skin.get("all", ImageButtonStyle.class);
				ImageButton btn = new ImageButton(style);
				btn.addListener(new ChangeListener(){
					@Override
					public void changed(ChangeEvent event, Actor actor)
					{
						changeFilter(null);
					}	
				});
				tabs.add(btn).size(32f);
				tabs.row();
				tabGroup.add(btn);
				btn.setChecked(true);
			}
			//local button
			{
				ImageButtonStyle style = skin.get("local", ImageButtonStyle.class);
				ImageButton btn = new ImageButton(style);
				btn.addListener(new ChangeListener(){
					@Override
					public void changed(ChangeEvent event, Actor actor)
					{
						changeFilter(ChatType.Local);
					}	
				});
				tabs.add(btn).size(32f);
				tabs.row();
				tabGroup.add(btn);
			}
			//zone button
			{
				ImageButtonStyle style = skin.get("zone", ImageButtonStyle.class);
				ImageButton btn = new ImageButton(style);
				btn.addListener(new ChangeListener(){
					@Override
					public void changed(ChangeEvent event, Actor actor)
					{
						changeFilter(ChatType.Zone);
					}	
				});
				tabs.add(btn).size(32f);
				tabGroup.add(btn);
			}
		}
				
		grid.add(tabs).width(32f).height(130f).colspan(1);
		grid.add(listPane).expandX().fill().colspan(9);
		
		grid.row();
		grid.add(sendButton).size(32f).colspan(1);
		grid.add(messageBox).expandX().fillX().height(32f).colspan(9);
		
		grid.bottom();
		
		//add the ui to the stage
		addActor(grid);
		
		newLabels = new ObjectFloatMap<Label>();
		
		messageBox.addListener(new InputListener(){
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if (keycode == Keys.ENTER)
				{
					sendMessage();
					return true;
				}
				return super.keyDown(event, keycode);
			}
		});
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
	 * Generates a chat message command and updates the ui
	 */
	private void sendMessage()
	{
		String message = messageBox.getText();
		
		//don't send a message if the message is blank
		if (message.trim().length() == 0)
			return;
		
		if (!(message.startsWith("/z") || message.startsWith("/l") || message.startsWith("/w")))
		{
			if (activeHistory == zoneHistory)
			{
				message = "/z " + message;
			}
			else
			{
				message = "/l " + message;
			}
		}
		CommandChatMessageSent cmd = new CommandChatMessageSent(message);
		ModelFacade.getSingleton().queueCommand(cmd);
		
		messageBox.setText("");
	}

	/**
	 * Adds a new message into the chat history
	 * @param message
	 * 	message to add
	 * @param type 
	 *  type of the message to add to the history
	 */
	public void addMessage(String message, ChatType type)
	{
		LabelStyle style;
		switch (type)
		{
			case Zone:
				zoneHistory.add(message);
				style = skin.get("zone", LabelStyle.class);
				break;
			case Local:
				localHistory.add(message);
				style = skin.get("local", LabelStyle.class);
				break;
			default:
				style = skin.get("default", LabelStyle.class);
				break;
		}
		allHistory.add(message);
		
		Label l = new Label(message, style);
		l.getColor().a = 0;
		l.addAction(Actions.fadeIn(.3f));
		l.setWrap(true);
		this.newLabels.put(l, 0f);
		chatHistoryView.top().add(l).expandX().fillX();
		chatHistoryView.row();
	
		listPane.setScrollPercentY(1f);
		listPane.setScrollPercentX(0f);
	}
	
	/**
	 * Changes the history filter of messages
	 * @param type
	 * 	type of messages to filter
	 */
	private void changeFilter(ChatType type)
	{
		if (type == ChatType.Zone)
		{
			activeHistory = zoneHistory;
		}
		else if (type == ChatType.Local)
		{
			activeHistory = localHistory;
		}
		else
		{
			activeHistory = allHistory;
		}
		chatHistoryView.clear();
		for (int i = 0; i < activeHistory.size; i++)
		{
			String msg = activeHistory.get(i);
			LabelStyle style;
			if (zoneHistory.contains(msg, false))
			{
				style = skin.get("zone", LabelStyle.class);
			}
			else if (localHistory.contains(msg, false))
			{
				style = skin.get("local", LabelStyle.class);
			}
			else
			{
				style = skin.get("default", LabelStyle.class);
			}
			Label l = new Label(msg, style);
			l.setWrap(true);
			chatHistoryView.top().add(l).expandX().fillX();
			chatHistoryView.row();
		}
		listPane.setScrollPercentY(1f);
		listPane.setScrollPercentX(0f);
	}
}
