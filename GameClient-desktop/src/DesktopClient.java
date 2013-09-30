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
		new LwjglApplication(new GameLibGDX(), "Game", 480, 320, false);
	}
}