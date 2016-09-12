package view.screen.map;
import model.ClientModelFacade;
import model.CommandChatMessageSent;
import view.screen.SkinPicker;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectFloatMap;

import datatypes.ChatType;

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
		skin = SkinPicker.getSkinPicker().getDefaultSkin();
		
		Table grid = new Table();
		grid.setWidth(800f);
		
		//create text box for typing messages
		messageBox = new TextField("", SkinPicker.getSkinPicker().getChatMessageBoxSkin());
		//create the message button
		Button sendButton = new Button(skin);
		
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
			//local button
			{
				TextButton btn = new TextButton("L", skin);
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
				//ImageButtonStyle style = skin.get("zone", ImageButtonStyle.class);
				TextButton btn = new TextButton("Z", skin);
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
		
		messageBox.addListener(new InputListener()
		{
			@Override
			public boolean keyDown(InputEvent event, int keycode)
			{
				System.out.println("Keycode: " + keycode);
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
		ClientModelFacade.getSingleton().queueCommand(cmd);
		
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
		boolean scrollToBottom = listPane.getScrollPercentY() == 1f;
		switch (type)
		{
			case Zone:
				message = "[Z]" + message;
				zoneHistory.add(message);
				break;
			case Local:
				message = "[L]" + message;
				localHistory.add(message);
				break;
			default:
				System.err.println("Chat done broke: " + type.name());
				break;
		}
		allHistory.add(message);	
		Label l = new Label(message, skin);
		l.setWrap(true);
		this.newLabels.put(l, 0f);
		chatHistoryView.top().add(l).expandX().fillX();
		if (scrollToBottom)
		{
			listPane.layout();
			listPane.setScrollPercentY(1f);
		}
		l.setColor(Color.WHITE);
		chatHistoryView.row();
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
			Label l = new Label(msg, skin);
			l.setWrap(true);
			chatHistoryView.top().add(l).expandX().fillX();
			chatHistoryView.row();
		}
		listPane.setScrollPercentY(1f);
		listPane.setScrollPercentX(0f);
	}
}
