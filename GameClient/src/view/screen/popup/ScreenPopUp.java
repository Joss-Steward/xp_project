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
	private static String pop_header = "Quest";
	private static String pop_data = "adventure complete";
	private final Skin skin = new Skin (Gdx.files.internal("data/uiskin.json"));
	
	private Stage stage;
	
	/**
	 * Basic constructor. will call showPopUp() to initialize all the data in the tables.
	 * @param stage the stage
	 */
	public ScreenPopUp(Stage stage)
	{
		this.stage = stage;
		this.showPopup();
		setUpListening();
	}

	
	/**
	* Sets up the QualifiedObserver for PlayerMapperReport
	*/
	private void setUpListening()
	{
		//QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
		//// Need Player Mapper Report ////
		//cm.registerObserver(this, PlayerMapperReport.class); 		
	}
	
	/**
	 * initializes popup contents and displays center screen with ok button to close
	 */
	private void showPopup()
	{
		ExitDialog pop_close = new ExitDialog(pop_header,skin);
		pop_close.show(stage);
	}
	
	
	@SuppressWarnings("javadoc")
	public static class ExitDialog extends Dialog{

		/**
		 * @param header The name of the popup window
		 * @param skin The skin the window uses
		 */
		public ExitDialog(String header, Skin skin)
		{
			super(header, skin);
			text(pop_data);
			button("OK");
		}		
	}
}
