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
public class PlayerSprite
{

	protected SpriteSheet sourceImg;
	protected Sprite sprite;
	protected ObjectMap<Direction, Animation> animation;
	protected Animation currentAnimation;
	protected float animationTimer;
	protected float lastMoved;

	// destination location of the movement
	protected Vector2 dest;
	protected Vector2 real;
	protected Vector2 current;

	protected Direction facing;

	/**
	 * Creates an instance where the sprite uses this texture region
	 * 
	 * @param region
	 */
	protected PlayerSprite(TextureRegion region)
	{
		sourceImg = new SpriteSheet(region, 4, 4);
		sprite = new Sprite(sourceImg.getFrame(0, 0));
		animationTimer = 0;
		animation = new ObjectMap<Direction, Animation>();
		for (Direction dir : Direction.values())
		{
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
	protected PlayerSprite()
	{
		dest = new Vector2();
		current = new Vector2();
		real = new Vector2();
		facing = Direction.South;

		animationTimer = 0;
		animation = new ObjectMap<Direction, Animation>();
		for (Direction dir : Direction.values())
		{
			animation.put(dir, null);
		}
		currentAnimation = animation.get(Direction.South);

	}

	/**
	 * Sets the location on screen that the sprite is be located without
	 * animating
	 * 
	 * @param x
	 *            horizontal screen location of the sprite
	 * @param y
	 *            vertical screen location
	 */
	public void setPosition(float x, float y)
	{
		this.current.set(x, y);
		this.dest.set(x, y);
		this.real.set(x, y);
	}

	/**
	 * Sets the location on screen that the sprite is to move to with animation
	 * 
	 * @param x
	 *            horizontal screen location of the sprite
	 * @param y
	 *            vertical screen location
	 */
	public void move(float x, float y)
	{
		// change destination so the player may tween to the location instead of
		// just popping up
		dest.set(x, y);

		// set the amount of time since the last movement
		// this allows continuous animation while moving
		lastMoved = 1f;

		// change the facing direction so it shows the proper sprite strip to
		// animate
		facing = Direction.getFacing(current, dest);
		currentAnimation = animation.get(facing);
	}

	/**
	 * @return the location of the player
	 */
	public Vector2 getPosition()
	{
		return real;
	}

	/**
	 * Updates the properties of the sprite dependent on the game cycle rate.
	 * 
	 * @param delta
	 *            differences in time between update cycles of the application
	 */
	public void update(float delta)
	{
		// update animation
		lastMoved -= delta;
		if (lastMoved > 0f)
			animationTimer += delta;
		else
			animationTimer = 0f;

		// tween to destination
		if (!doneWalking())
		{
			real = current.lerp(dest, 1f - lastMoved);
		}
	}

	/**
	 * Draws the sprite to screen
	 * 
	 * @param batch
	 *            context in which we draw to the screen
	 */
	public void draw(SpriteBatch batch)
	{
		sprite.setPosition(real.x, real.y);
		sprite.setRegion(currentAnimation.getKeyFrame(animationTimer));
		sprite.draw(batch);
	}

	/**
	 * @return x position of the sprite
	 */
	public float getX()
	{
		return real.x;
	}

	/**
	 * @return y position of the sprite
	 */
	public float getY()
	{
		return real.y;
	}

	/**
	 * @return the current direction the sprite is facing
	 */
	public Direction getFacing()
	{
		return facing;
	}
	
	/**
	 * @return if the player's pixel position is that of their destination position
	 */
	public boolean doneWalking()
	{
		return real.epsilonEquals(dest, .05f);
	}
}
