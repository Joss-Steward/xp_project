import model.OptionsManager;
import runners.GameLibGDX;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import static view.screen.Screens.DEFAULT_RES;

/**
 * 
 * @author Merlin
 * 
 */
public class DesktopClient
{
	
	/**
	 * 
	 * @param args nothing
	 */
	public static void main(String[] args)
	{
		String host = "localhost";
		for(String arg: args)
		{
			String[] splitArg = arg.split("=");
			if(splitArg[0].equals("--loginhost"))
			{
				host = splitArg[1];
			}
		}
		OptionsManager.getSingleton().setLoginHost(host);
		OptionsManager.getSingleton().setTestMode(false);
		
		new LwjglApplication(new GameLibGDX(), "Game", (int)DEFAULT_RES[0], (int)DEFAULT_RES[1]);
	}
}