package view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Everything a basic screen needs
 * @author Merlin
 *
 */
public interface ScreenBasic extends Screen
{
	/**
	 * Get the stage that this screen displays
	 * @return the stage
	 */
	public Stage getStage();
}
