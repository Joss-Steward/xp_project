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
 * @author Zachary & Abdul
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
	 * @param overlayingScreen
	 * @param text
	 */
	public void addOverlayScreenToggle(final OverlayingScreen overlayingScreen, String text)
	{
		overlayingScreens.add(overlayingScreen);
		ButtonStyle style = skin.get(ButtonStyle.class);
		Button b = new Button(style);
		b.add(new Label(text, skin));
		b.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) 
			{
				closeAllOverlayingScreens();
				overlayingScreen.toggleVisibility();
				super.clicked(event, x, y);
			}
		});
		tabs.add(b);
	}

	/**
	 * 
	 */
	public void closeAllOverlayingScreens()
	{
		for (OverlayingScreen os : overlayingScreens)
		{
			os.setVisible(false);
		}
	}
}