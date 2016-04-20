package view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class OverlayingScreen extends Group
{
	protected Table container;

	public abstract float getWidth();

	public abstract float getHeight();

	public OverlayingScreen()
	{
		setSize(getWidth(), getHeight());
		setPosition(getXPosition(), getYPosition());
		// Make the container
		container = new Table();
		container.setFillParent(true); 
		container.left().top();
		
		addActor(container);
		setVisible(false);
	}

	/**
	 * By default, overlaying screens are centered in the x dimension
	 * 
	 * @return the x position this should be displayed at
	 */
	public float getXPosition()
	{
		return (Gdx.graphics.getWidth() - getWidth()) / 2;
	}

	/**
	 * By default, overlaying screens are displayed 10% down in the y direction
	 * 
	 * @return the x position this should be displayed at
	 */
	public float getYPosition()
	{
		return (Gdx.graphics.getHeight() - getHeight()) / 1.1f;
	}

}
