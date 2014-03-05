package runners;

import java.io.IOException;
import java.net.Socket;

import model.ModelFacade;
import view.ScreenBasic;
import view.Screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import communication.ConnectionManager;
import communication.handlers.MessageHandlerSet;
import communication.packers.MessagePackerSet;

/**
 * The most basic gui!
 * 
 * @author Merlin
 * 
 */
public class GameLibGDX extends Game implements ApplicationListener
{
	private ConnectionManager cm;

	/**
	 * 
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	public void create()
	{

		Socket socket;
		try
		{
			socket = new Socket("localhost", 1871);
			cm = new ConnectionManager(socket, new MessageHandlerSet(),
					new MessagePackerSet());

		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}

		// tell the model to initialize itself
		ModelFacade.getSingleton(false, false);
		Screens.LOGIN_SCREEN.setGame(this);

		// start at the login screen
		ScreenBasic screen = Screens.LOGIN_SCREEN.getScreen();
		this.setScreen(screen);
		Gdx.input.setInputProcessor(screen.getStage());
	}

	/**
	 * @see com.badlogic.gdx.ApplicationListener#resize(int, int)
	 */
	public void resize(int width, int height)
	{
	}

	/**
	 * @see com.badlogic.gdx.ApplicationListener#pause()
	 */
	public void pause()
	{
	}

	/**
	 * @see com.badlogic.gdx.ApplicationListener#resume()
	 */
	public void resume()
	{
	}

	/**
	 * @see com.badlogic.gdx.ApplicationListener#dispose()
	 */
	public void dispose()
	{

		cm.disconnect();
	}
}