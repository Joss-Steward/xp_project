package view;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The renderable instance of a player on a map
 */
public class PlayerSprite {

	private Sprite sprite;
	
	/**
	 * Creates an instance where the sprite uses this texture region
	 * @param region
	 */
	protected PlayerSprite(TextureRegion region) {
		sprite = new Sprite(region);
	}
	
	/**
	 * Sets the location on screen of the sprite
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y) {
		sprite.setPosition(x, y);
	}
	
	/**
	 * Draws the sprite to screen
	 * @param batch
	 */
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}

	/**
	 * @return x position of the sprite
	 */
	public float getX() {
		return sprite.getX();
	}
	
	/**
	 * @return y position of the sprite
	 */
	public float getY() {
		return sprite.getY();
	}
}
