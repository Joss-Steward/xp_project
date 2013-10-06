package view;

import com.badlogic.gdx.Game;

/**
 * Keeps the screens and their listeners paired
 * @author Merlin
 *
 */
public enum Screens
{
	/**
	 * 
	 */
	LOGIN_SCREEN(new ScreenLogin(),new ScreenLoginListener()),
	/**
	 * 
	 */
	MAP_SCREEN(new ScreenMap(), new ScreenMapListener());
	

	private ScreenBasic screen;
	
	
	private ScreenListener screenListener;


	private static Game game;

	Screens(ScreenBasic screen, ScreenListener listener)
	{
		this.screen = screen;
		this.screenListener = listener;
		listener.setScreen(screen);
	}

	/**
	 * Get the listener for a given screen
	 * @return the appropriate listener
	 */
	public ScreenListener getScreenListener()
	{
		return screenListener;
	}

	/**
	 * Get a specific screen
	 * @return the screen
	 */
	public ScreenBasic getScreen()
	{
		return screen;
	}
	
	/**
	 * Tell this enum where the game that displays these screens is
	 * @param gameToUse the game
	 */
	public void setGame(Game gameToUse)
	{
		game = gameToUse;
	}

	/**
	 * Tell this enum to display a specified screen
	 * @param screen the screen (from this enum) that we should display
	 */
	public static void display(Screens screen)
	{
		game.setScreen(screen.getScreen());
	}
}
