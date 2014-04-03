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

/**
 * 
 * @author nhydock
 *
 */
public class ChatUi
{
	Array<String> history;
	List chatHistoryView;
	TextField messageBox;
	Button sendButton;
	
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
		sendButton = new TextButton("Send", skin);
		
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
		history = new Array<String>();
		history.addAll(chat);
		chatHistoryView = new List(history.toArray(), skin);
		ScrollPane listPane = new ScrollPane(chatHistoryView, skin);
		
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
		// String message = messageBox.getMessageText();
		
		messageBox.setText("");
	}
}
