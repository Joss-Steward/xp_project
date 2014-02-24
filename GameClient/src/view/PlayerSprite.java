package view;

import util.SpriteSheet;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * The renderable instance of a player on a map
 */
public class PlayerSprite {

	protected SpriteSheet sourceImg;
	protected Sprite sprite;
	protected ObjectMap<Direction, Animation> animation;
	protected Animation currentAnimation;
	protected float animationTimer;
	protected float lastMoved;
	
	//destination location of the movement
	protected Vector2 dest;
	protected Vector2 real;
	protected Vector2 current;
	
	protected Direction facing;
	
	/**
	 * Creates an instance where the sprite uses this texture region
	 * @param region
	 */
	protected PlayerSprite(TextureRegion region) {
		sourceImg = new SpriteSheet(region, 4, 4);
		sprite = new Sprite(sourceImg.getFrame(0, 0));
		animationTimer = 0;
		animation = new ObjectMap<Direction, Animation>();
		for (Direction dir : Direction.values()) {
			Animation a = new Animation(.25f, sourceImg.getRow(dir.ordinal()));
			a.setPlayMode(Animation.LOOP);
			animation.put(dir, a);
		}
		currentAnimation = animation.get(Direction.South);
		
		dest = new Vector2();
		current = new Vector2();
		real = new Vector2();
		facing = Direction.South;
	}
	
	/**
	 * Constructs a headless player sprite
	 */
	protected PlayerSprite() {
		dest = new Vector2();
		current = new Vector2();
		real = new Vector2();
		facing = Direction.South;
		
		animationTimer = 0;
		animation = new ObjectMap<Direction, Animation>();
		for (Direction dir : Direction.values()) {
			animation.put(dir, null);
		}
		currentAnimation = animation.get(Direction.South);
		
	}

	/**
	 * Sets the location on screen that the sprite is to move to
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y) {
		// change destination so the player may tween to the location instead of just popping up
		dest.set(x, y);
		
		// set the amount of time since the last movement
		// this allows continuous animation while moving
		lastMoved = 1f;
		
		// change the facing direction so it shows the proper sprite strip to animate
		facing = Direction.getFacing(current, dest);
		currentAnimation = animation.get(facing);
	}
	
	/**
	 * @return the location of the player
	 */
	public Vector2 getPosition() {
		return real;
	}
	
	/**
	 * Updates the properties of the sprite dependent on the
	 * game cycle rate.
	 * @param delta
	 */
	public void update(float delta) {
		// update animation
		lastMoved -= delta;
		if (lastMoved > 0f)
			animationTimer += delta;
		else
			animationTimer = 0f;
		
		// tween to destination
		if (!real.epsilonEquals(dest, .05f))
		{
			real = current.lerp(dest, 1f - lastMoved);
		}
	}
	
	/**
	 * Draws the sprite to screen
	 * @param batch
	 */
	public void draw(SpriteBatch batch) {
		sprite.setPosition(real.x, real.y);
		sprite.setRegion(currentAnimation.getKeyFrame(animationTimer));
		sprite.draw(batch);
	}

	/**
	 * @return x position of the sprite
	 */
	public float getX() {
		return real.x;
	}
	
	/**
	 * @return y position of the sprite
	 */
	public float getY() {
		return real.y;
	}

	/**
	 * @return the current direction the sprite is facing
	 */
	public Direction getFacing() {
		return facing;
	}
}
