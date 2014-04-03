package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;

import communication.messages.ChatMessage;
import communication.messages.ChatMessage.ChatType;

/**
 * 
 * @author nhydock
 *
 */
public class ChatUi
{
	//chat historys
	Array<String> allHistory;
	Array<String> zoneHistory;
	Array<String> localHistory;
	
	Array<String> activeHistory;
	
	List chatHistoryView;
	TextField messageBox;
	
	Stage stage;
	
	/**
	 * Create a new chat ui that displays at the bottom of the screen
	 */
	public ChatUi()
	{
		final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		stage = new Stage();
		Table grid = new Table();
		grid.setFillParent(true);
		
		//create text box for typing messages
		messageBox = new TextField("", skin);
		
		//create the message button
		TextButton sendButton = new TextButton("Send", skin);
		
		//add button listener
		sendButton.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				if (button == Buttons.LEFT)
				{
					String message = messageBox.getText();
					if (message.trim().length() > 0) 
					{
						sendMessage();
						System.out.println("Send Button Clicked");
					}
					return true;
				}
				return false;
			}
		});
		
		//create chat log area
		String[] chat = {"hello", "world", "you", "sexy", "thing"};
		allHistory = new Array<String>();
		localHistory = new Array<String>();
		zoneHistory = new Array<String>();
		activeHistory = allHistory;
		allHistory.addAll(chat);
		chatHistoryView = new List(activeHistory.toArray(), skin);
		ScrollPane listPane = new ScrollPane(chatHistoryView, skin);
		
		//create chat filter buttons
		Table tabs = new Table();
		TextButton allButton = new TextButton("All", skin);
		TextButton localButton = new TextButton("Local", skin);
		TextButton zoneButton = new TextButton("Zone", skin);
		
		tabs.add(allButton).expandX().fill();
		tabs.row();
		tabs.add(localButton).fill();
		tabs.row();
		tabs.add(zoneButton).fill();
		
		grid.left().add(tabs).width(80f);
		grid.add(listPane).expandX().fill().height(80f);
		
		grid.row();
		grid.add(messageBox).expandX().fillX().height(32f);
		grid.add(sendButton).width(50f).height(32f);
		
		grid.bottom();
		
		//add message box focusing to the stage
		stage.addListener(new InputListener(){
			@Override
			public boolean keyDown(InputEvent event, int keycode)
			{
				if (keycode == Keys.ENTER)
				{
					if (stage.getKeyboardFocus() == null)
					{
						stage.setKeyboardFocus(messageBox);
						return true;
					}
					else if (stage.getKeyboardFocus() == messageBox)
					{
						sendMessage();
						System.out.println("Enter pressed to send message");
						stage.setKeyboardFocus(null);
						return true;
					}
				}
				return false;
			}
		});
		
		//add the ui to the stage
		stage.addActor(grid);
	}
	
	/**
	 * Updates and draws the ui
	 * @param deltaTime
	 * 	timer resolution between draw cycles
	 */
	public void draw(float deltaTime)
	{
		stage.act(deltaTime);
		stage.draw();
	}
	
	/**
	 * Appends this ui's input processor to an input multiplexer
	 * @param input
	 * 	input multiplexer to add to
	 */
	public void addToInput(InputMultiplexer input)
	{
		input.addProcessor(stage);
	}
	
	/**
	 * Generates a chat message command and updates the ui
	 */
	private void sendMessage()
	{
		// TODO send message command
		messageBox.setText("");
	}

	/**
	 * Adds a new message into the chat history
	 * @param message
	 * 	message to add
	 */
	public void addMessage(String message, ChatType type)
	{
		switch (type)
		{
			case Zone:
				zoneHistory.add(message);
				break;
			case Local:
				localHistory.add(message);
				break;
		}
		allHistory.add(message);
		chatHistoryView.setItems(activeHistory.toArray());
	}
	
	/**
	 * Changes the history filter of messages
	 * @param type
	 */
	private void changeFilter(ChatType type)
	{
		switch (type)
		{
			case Zone:
				activeHistory = zoneHistory;
				break;
			case Local:
				activeHistory = localHistory;
				break;
			default:
				activeHistory = allHistory;
				break;
		}
		chatHistoryView.setItems(activeHistory.toArray());
	}
}
