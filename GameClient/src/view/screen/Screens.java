package view.screen;

import view.ScreenQAs;
import view.ScreenQAsListener;
import view.screen.login.ScreenLogin;
import view.screen.login.ScreenLoginListener;
import view.screen.map.ScreenMap;
import view.screen.map.ScreenMapListener;

import com.badlogic.gdx.Game;

/**
 * Keeps the screens and their listeners paired
 * 
 * @author Merlin
 * 
 */
public enum Screens
{
	/**
	 * 
	 */
	LOGIN_SCREEN(new ScreenLogin(), new ScreenLoginListener()),
	/**
	 * 
	 */
	MAP_SCREEN(new ScreenMap(), new ScreenMapListener()),
	/**
	 * 
	 */
	QAS_SCREEN(new ScreenQAs(), new ScreenQAsListener());

	private ScreenBasic screen;

	private ScreenListener screenListener;

	/**
	 * Default expected internal resolution in pixels around which we can program
	 */
	public static float[] DEFAULT_RES = {800f, 800f};
	
	Screens(ScreenBasic screen, ScreenListener listener)
	{
		this.screen = screen;
		this.screenListener = listener;
		listener.setAssociatedScreen(screen);
	}

	/**
	 * Get the listener for a given screen
	 * 
	 * @return the appropriate listener
	 */
	public ScreenListener getScreenListener()
	{
		return screenListener;
	}

	/**
	 * Get a specific screen
	 * 
	 * @return the screen
	 */
	public ScreenBasic getScreen()
	{
		return screen;
	}

	/**
	 * Tell this enum where the game that displays these screens is
	 * 
	 * @param gameToUse
	 *            the game
	 */
	public void setGame(Game gameToUse)
	{
		for (Screens screen : Screens.values())
		{
			screen.getScreenListener().setGame(gameToUse);
		}
	}
}
