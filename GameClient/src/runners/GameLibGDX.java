package runners;

import java.io.IOException;
import java.net.Socket;

import model.OptionsManager;
import view.screen.ScreenBasic;
import view.screen.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import communication.ConnectionManager;
import communication.StateAccumulator;
import communication.handlers.MessageHandlerSet;
import communication.packers.MessagePackerSet;
import edu.ship.shipsim.client.model.ModelFacade;

/**
 * The most basic gui!
 * 
 * @author Merlin
 * 
 */
public class GameLibGDX extends Game
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
			String host = OptionsManager.getSingleton().getLoginHost();
			socket = new Socket(host, 1871);
			MessagePackerSet messagePackerSet = new MessagePackerSet();
			StateAccumulator stateAccumulator = new StateAccumulator(messagePackerSet);
			
			cm = new ConnectionManager(socket, stateAccumulator,
					new MessageHandlerSet(stateAccumulator), messagePackerSet);
			

		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}

		// tell the model to initialize itself
		ModelFacade.getSingleton();
		Screens.LOGIN_SCREEN.setGame(this);

		// start at the login screen
		ScreenBasic screen = Screens.LOGIN_SCREEN.getScreen();
		this.setScreen(screen);
		Gdx.input.setInputProcessor(screen.getStage());
		
//		// start QAs screen
//		ScreenBasic qa = Screens.QAS_SCREEN.getScreen();
//		this.setScreen(qa);
//		Gdx.input.setInputProcessor(qa.getStage());
		
	}

	/**
	 * @see com.badlogic.gdx.ApplicationListener#dispose()
	 */
	public void dispose()
	{
		super.dispose();
		cm.disconnect();
	}
}
