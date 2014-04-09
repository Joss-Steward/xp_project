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
		new LwjglApplication(new GameLibGDX(), "Game", 800, 800, true);
	}
}