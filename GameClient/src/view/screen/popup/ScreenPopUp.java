package view.screen.popup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

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
	 * @param behavior the PopupBehavior of the popup
	 */
	public ScreenPopUp(String header, String description, Stage stage, PopupBehavior behavior)
	{
		ExitDialog pop_close = new ExitDialog(header, description, skin, behavior);
		pop_close.show(stage);
	}
	
	
	/**
	 * @author sl6469
	 *
	 */
	public static class ExitDialog extends Dialog
	{

		private PopupBehavior behavior;
		/**
		 * @param header The name of the pop up window
		 * @param description information pop up is displaying
		 * @param skin The skin the window uses
		 * @param behavior the PopupBehavior of the popup
		 */
		public ExitDialog(String header, String description, Skin skin, PopupBehavior behavior)
		{
			super(header, skin);
			Label label = new Label(description, skin);
			label.setWrap(true);
			label.setFontScale(1.0f);
			label.setAlignment(Align.center);
			this.getContentTable().add(label).width(400).row();
//			text(description);
			button("OK");
			
			this.behavior = behavior;
		}		
		
		@Override
		protected void result(Object object)
		{
			this.behavior.clicked();
		}
	}


	
}
