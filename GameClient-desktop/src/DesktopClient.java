import model.OptionsManager;
import runners.GameLibGDX;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

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
			if(splitArg[0].equals("--host"))
			{
				host = splitArg[1];
			}
		}
		OptionsManager.getSingleton().setLoginHost(host);
		
		new LwjglApplication(new GameLibGDX(), "Game", 800, 800, true);
	}
}