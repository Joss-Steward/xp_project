package view;

import model.CommandChatMessageSent;
import model.ModelFacade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;

import communication.messages.ChatMessage.ChatType;

/**
 * 
 * @author nhydock
 *
 */
public class ChatUi
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
	
	List chatHistoryView;
	TextField messageBox;
	
	Stage stage;
	
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
		final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		stage = new Stage();
		stage.setViewport(600, YSIZE, true, 0, 0, Math.min(600, Gdx.graphics.getWidth()), Gdx.graphics.getHeight());
		
		Table grid = new Table();
		grid.setFillParent(true);
		
		//create text box for typing messages
		messageBox = new TextField("", skin);
		
		//create the message button
		TextButton sendButton = new TextButton("Send", skin);
		
		//add button listener
		sendButton.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				sendMessage();
				System.out.println("button clicked");
			}
			
		});
		
		//create chat log area
		activeHistory = allHistory;
		chatHistoryView = new List(activeHistory.toArray(), skin);
		ScrollPane listPane = new ScrollPane(chatHistoryView, skin);
		
		//create chat filter buttons
		Table tabs = new Table();
		TextButton allButton = new TextButton("All", skin);
		TextButton localButton = new TextButton("Local", skin);
		TextButton zoneButton = new TextButton("Zone", skin);
		
		allButton.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				changeFilter(null);
			}
			
		});
		localButton.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				changeFilter(ChatType.Local);
			}
			
		});
		zoneButton.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				changeFilter(ChatType.Zone);
			}
			
		});
		
		tabs.add(allButton).fill();
		tabs.row();
		tabs.add(localButton).fill();
		tabs.row();
		tabs.add(zoneButton).fill();
		
		grid.add(tabs).fill().colspan(1);
		grid.add(listPane).expandX().fill().height(100f).colspan(9);
		
		grid.row();
		grid.left().add(messageBox).expandX().fillX().height(32f).colspan(9);
		grid.add(sendButton).width(100f).height(32f).colspan(1);
		
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
	 * Rescale the ui for different resolutions
	 * @param width
	 * 	viewport width
	 * @param height
	 *  viewport height
	 */
	public void resize(int width, int height)
	{
		stage.setViewport(600, YSIZE, true, 0, 0, width, height);
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
		switch (type)
		{
			case Zone:
				zoneHistory.add(message);
				break;
			case Local:
				localHistory.add(message);
				break;
			default:
				break;
		}
		allHistory.add(message);
		chatHistoryView.setItems(activeHistory.toArray());
	}
	
	/**
	 * Changes the history filter of messages
	 * @param type
	 * 	type of messages to filter
	 */
	private void changeFilter(ChatType type)
	{
		if (type != null)
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
		}
		chatHistoryView.setItems(activeHistory.toArray());
	}
}
