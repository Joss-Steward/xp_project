package view.screen;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @author Ian Keefer and TJ Renninger
 *
 */
public class SkinPicker
{
	private static SkinPicker skinPicker;
	private HashMap<String, String> crewColorMap;
	private Skin skin;
	private String crew;
	
	private SkinPicker()
	{
		crewColorMap = new HashMap<String, String>();
		crewColorMap.put("DEFAULT", "gray");
		crewColorMap.put("NULL_POINTER", "red");
		crewColorMap.put("OUT_OF_BOUNDS", "green");
		crewColorMap.put("OFF_BY_ONE", "blue");
	}
	
	/**
	 * @return the instance of the Skin Picker
	 */
	public static SkinPicker getSkinPicker()
	{
		if (skinPicker == null)
		{
			skinPicker = new SkinPicker();
		}
		return skinPicker;
	}
	
	/**
	 * @param c the crew of the current player;
	 */
	public void setCrew(String c)
	{
		crew = c.toUpperCase();
	}
	
	/**
	 * @return the skin that is specific to the crew of the current player
	 */
	public Skin getCrewSkin()
	{
		if (skin == null)
		{
			String color = crewColorMap.getOrDefault(crew, crewColorMap.get("DEFAULT"));	
			skin = new Skin(Gdx.files.internal("data/ui/screenskins/ui-" + color + ".json"));
		}
		return skin;
	}
	
	/**
	 * @return the default skin color
	 */
	public Skin getDefaultSkin()
	{
		String color = crewColorMap.get("DEFAULT");
		return new Skin(Gdx.files.internal("data/ui/screenskins/ui-" + color + ".json"));
	}
	
	/**
	 * @return the skin for the chat message box
	 */
	public Skin getChatMessageBoxSkin()
	{
		String color = crewColorMap.get("DEFAULT");
		Skin skin  = new Skin(Gdx.files.internal("data/ui/screenskins/ui-" + color + ".json"));
		new Label("", skin).getStyle().fontColor.set(Color.BLACK);
		return skin;
	}
}
