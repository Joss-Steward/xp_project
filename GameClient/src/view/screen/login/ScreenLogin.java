package view.screen.login;

import model.QualifiedObservableReport;
import view.screen.ScreenBasic;
import view.screen.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.ship.shipsim.client.model.CommandLogin;
import edu.ship.shipsim.client.model.ModelFacade;

/**
 * First screen the player sees to start playing.
 * 
 * @author BrysonHair
 * 
 */
public class ScreenLogin extends ScreenBasic
{
	private Texture logo;
	private TextField loginField;
	private TextButton connectButton;
	
	private Table table;

	private TextField pwField;
	
	private String flagMsg;

	/**
	 * Create a login screen
	 */
	public ScreenLogin()
	{
		flagMsg = "";
	}

	/**
	 * Creates all elements of LoginScreen
	 */
	private void initializeScreen()
	{
		Viewport v = new ExtendViewport(Screens.DEFAULT_RES[0], Screens.DEFAULT_RES[1]);
		stage = new Stage(v);

		final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		logo = new Texture("data/GameLogo.jpg");

		connectButton = new TextButton("Connect", skin);

		Label nameLabel = new Label("Name:", skin);
		Label pwLabel = new Label("Password:", skin);

		final Label connectingLabel = new Label("Connecting to Lobby...", skin);

		connectButtonListener(connectingLabel);

		// create login field
		loginField = new TextField("", skin);
		loginField.setMessageText("Player Name");

		// create login field
		pwField = new TextField("", skin);
		pwField.setMessageText("Password");

		Label flagLabel = new Label(this.flagMsg, skin);
		initializeTableContents(nameLabel, pwLabel, connectingLabel, flagLabel);
		stage.addActor(table);
		
		Gdx.input.setInputProcessor(stage);
	}

	/**
	 * Creates a listener for the Connect Button
	 * 
	 * @param connectingLabel
	 */
	private void connectButtonListener(final Label connectingLabel)
	{
		connectButton.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer,
					int button)
			{
				Gdx.app.log("Touched Connect button", "down");
				// Lock the button
				if (connectButton.isDisabled())
				{
					return false;
				}
				connectButton.setDisabled(true);
				if (loginField.getText().length() == 0)
				{
					Gdx.app.log("No name given", "NULL Player Name");

					// Set the text field to default name
					loginField.setText("Player-Name");
					loginField.setCursorPosition(loginField.getText().length());
					connectButton.setDisabled(false);
					return false;
				} else
				{
					// Send a message containing the player's name
					Gdx.app.log("Player's name", loginField.getText());
					connectButton.setDisabled(true);
					connectingLabel.setVisible(true);

					// Create the login command to allow the player to login
					CommandLogin lc = new CommandLogin(loginField.getText(), pwField
							.getText());
					ModelFacade.getSingleton().queueCommand(lc);
					return true;
				}
			}
		});
	}

	/**
	 * Create table and add its contents for LoginScreen
	 * 
	 * @param connectingLabel
	 * @param pwLabel
	 * @param nameLabel
	 * @param nameLabel
	 * @param connectingLabel
	 */
	private void initializeTableContents(Label nameLabel, Label pwLabel,
			Label connectingLabel, Label flagLabel)
	{
		table = new Table();
		table.setFillParent(true);
		// table.debug(); //Used to view table grid lines

		table.row();
		table.add(new Image(logo)).colspan(4);

		table.row();
		table.add();
		table.add(nameLabel);
		table.add(loginField).fill();
		table.add();

		table.row();
		table.add();
		table.add(pwLabel);
		table.add(pwField).fill();
		table.add();

		table.row();
		table.add(connectButton).colspan(4).pad(35);

		table.row();
		connectingLabel.setVisible(false);
		table.add(connectingLabel).colspan(4);
		table.row();
		table.add(flagLabel).colspan(4);
	}

	/**
	 * @see com.badlogic.gdx.Screen
	 */
	@Override
	public void dispose()
	{

	}

	/**
	 * @see com.badlogic.gdx.Screen
	 */
	@Override
	public void hide()
	{

	}

	/**
	 * @see com.badlogic.gdx.Screen
	 */
	@Override
	public void pause()
	{

	}

	/**
	 * Renders the login screen to the player
	 */
	@Override
	public void render(float arg0)
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		stage.act();
		stage.draw();
		Table.drawDebug(stage);
	}

	/**
	 * @see com.badlogic.gdx.Screen
	 */
	@Override
	public void resize(int width, int height)
	{
		stage.getViewport().update(width, height, true);
		stage.act();
	}

	/**
	 * @see com.badlogic.gdx.Screen
	 */
	@Override
	public void resume()
	{

	}

	/**
	 * @see com.badlogic.gdx.Screen
	 */
	@Override
	public void show()
	{
		initializeScreen();	
		
	}
	
	/**
	 * Set an error message to show on the login screen
	 * @param msg
	 * 	message to show
	 */
	public void showFlagMessage(String msg)
	{
		this.flagMsg = msg;
	}

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report) {
		// TODO Auto-generated method stub
		
	}

}
