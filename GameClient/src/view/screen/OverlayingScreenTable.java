package view.screen;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * @author tr3897
 *
 */
public abstract class OverlayingScreenTable extends ScrollPane
{
	private final float tablePadding = 10f;
	protected Table container;
	
	/**
	 * @param scrollable Whether or not the the overlaying screen table is scrollable
	 */
	public OverlayingScreenTable(boolean scrollable) 
	{
		super(null, SkinPicker.getSkinPicker().getCrewSkin());
		setScrollingDisabled(scrollable, scrollable);
		buildTable();
		setWidget(container);
	}

	private void buildTable() 
	{
		container = new Table();
		container.setFillParent(true);
		container.left().top();
		container.pad(tablePadding);
	}
	
	/**
	 * @return
	 */
	public float getPadding()
	{
		return tablePadding;
	}
}
