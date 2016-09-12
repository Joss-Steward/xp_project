package view.screen.map;
import java.util.ArrayList;
import view.screen.OverlayingScreen;
import view.screen.SkinPicker;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * MenuUI.java UI for the Menu
 * 
 * @author Zachary & Abdul, Ian Keefer, TJ Renninger
 *
 */
public class MenuUI extends Group
{	
	private final float HEIGHT = 30f;
	private Skin skin;
	private ArrayList<OverlayingScreen> overlayingScreens;
	private Table tabs;

	/**
	 * Create a new chat ui that displays at the bottom of the screen
	 */
	public MenuUI()
	{
		setHeight(HEIGHT);
		setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - getHeight());
		overlayingScreens = new ArrayList<OverlayingScreen>();
		skin = SkinPicker.getSkinPicker().getCrewSkin();
		tabs = new Table(skin);
		tabs.setFillParent(true);
		addActor(tabs);
		setVisible(true);
	}
	
	/**
	 * @param overlayingScreen That is to be toggled with the MenuUI
	 * @param text To display on the button
	 */
	public void addOverlayingScreenToggle(final OverlayingScreen overlayingScreen, String text)
	{
		overlayingScreens.add(overlayingScreen);
		ClickListener listener = new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) 
			{
				boolean wasVisible = overlayingScreen.isVisible();
				closeAllOverlayingScreens();
				if (!wasVisible)
				{
					overlayingScreen.toggleVisibility();
				}
				super.clicked(event, x, y);
			}
		};
		addButton(text, listener);
	}

	/**
	 * @param text text the button will display
	 * @param listener action the button will do
	 */
	public void addButton(String text, ClickListener listener)
	{
		ButtonStyle style = skin.get(ButtonStyle.class);
		Button button = new Button(style);
		button.add(new Label(text, skin));
		button.addListener(listener);
		tabs.add(button);
	}
	
	/**
	 * Closes all open overlaying screen.
	 */
	public void closeAllOverlayingScreens()
	{
		for (OverlayingScreen os : overlayingScreens)
		{
			os.setVisible(false);
		}
	}
}