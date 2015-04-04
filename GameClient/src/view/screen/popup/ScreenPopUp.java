package view.screen.popup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * A basic screen that displays any needed Pop-up information.
 * @author sl6469 ck4124
 *
 */
public class ScreenPopUp extends Group 
{
	private final Skin skin = new Skin (Gdx.files.internal("data/uiskin.json"));
	
	/**
	 * Basic constructor. will call showPopUp() to initialize all the data in the tables.
	 * @param header name for the dialog box
	 * @param description for text in popup
	 * @param stage the stage
	 */
	public ScreenPopUp(String header, String description, Stage stage)
	{
		ExitDialog pop_close = new ExitDialog(header, description, skin);
		pop_close.show(stage);
	}
	
	@SuppressWarnings("javadoc")
	public static class ExitDialog extends Dialog{

		/**
		 * @param header The name of the popup window
		 * @param skin The skin the window uses
		 */
		public ExitDialog(String header, String description, Skin skin)
		{
			super(header, skin);
			text(description);
			button("OK");
		}		
	}


	
}
