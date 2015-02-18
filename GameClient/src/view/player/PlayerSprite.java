package view.player;

import util.AnimationDrawable;
import util.SpriteSheet;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * The renderable instance of a player on a map
 */
public class PlayerSprite extends Image implements Comparable<PlayerSprite>
{
	/**
	 * Global constant defining the speed in seconds at which a 
	 *  sprite will move across the screen and animate.
	 */
	public static final float MOVESPEED = .5f;
	
	protected ObjectMap<Direction, AnimationDrawable> animation;
	protected AnimationDrawable currentAnimation;
	protected float lastMoved;

	// destination location of the movement
	protected Vector2 dest;
	protected Vector2 current;
	protected Vector2 real;

	protected Direction facing;

	/**
	 * Creates an instance where the sprite uses this texture region
	 * 
	 * @param region
	 */
	protected PlayerSprite(TextureRegion region)
	{
		SpriteSheet sourceImg = new SpriteSheet(region, 4, 4);
		animation = new ObjectMap<Direction, AnimationDrawable>();
		for (Direction dir : Direction.values())
		{
			TextureRegion[] row = sourceImg.getRow(dir.ordinal());
			Animation a = new Animation(MOVESPEED/row.length, row);
			a.setPlayMode(Animation.PlayMode.LOOP);
			AnimationDrawable ad = new AnimationDrawable(a);
			animation.put(dir, ad);
		}
		currentAnimation = animation.get(Direction.South);
		setDrawable(currentAnimation);
		setSize(currentAnimation.getMinWidth(), currentAnimation.getMinHeight());
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

		animation = new ObjectMap<Direction, AnimationDrawable>();
		for (Direction dir : Direction.values())
		{
			animation.put(dir, null);
		}
		currentAnimation = animation.get(Direction.South);
		setDrawable(currentAnimation);
	}

	/**
	 * Forcably sets the location on screen of the sprite without animating it
	 * 
	 */
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		current.set(x, y);
	}
	
	/**
	 * Sets the location on screen that the sprite is to move to with animation
	 * 
	 * @param x
	 *            horizontal screen location of the sprite
	 * @param y
	 *            vertical screen location
	 */
	public void move(final float x, final float y)
	{
		current.set(dest.x, dest.y);
		clearActions();
		addAction(
			Actions.sequence(
				Actions.moveTo(x, y, MOVESPEED),
				Actions.run(new Runnable(){

					@Override
					public void run() {
						current.set(x, y);
						currentAnimation.setTime(0);
					}
		
				})
			)
		);
		dest.set(x, y);
		// set the amount of time since the last movement
		// this allows continuous animation while moving
		lastMoved = 1f;

		// change the facing direction so it shows the proper sprite strip to
		// animate
		setFacing(Direction.getFacing(current, dest));
	}
	
	/**
	 * Changes the facing direction of the player sprite
	 * @param facing
	 */
	private void setFacing(Direction facing) {
		this.facing = facing;
		currentAnimation = animation.get(facing);
		if (currentAnimation != null){
			currentAnimation.setTime(0);
		}
		setDrawable(currentAnimation);
	}

	/**
	 * Updates the properties of the sprite dependent on the game cycle rate.
	 * 
	 * @param delta
	 *            differences in time between update cycles of the application
	 */
	public void act(float delta)
	{
		super.act(delta);
		real.x = getX();
		real.y = getY();
		
		// update animation if moving
		if (!doneWalking() && currentAnimation != null) {
			currentAnimation.update(delta);
		}
	}
	
	/**
	 * @return the current direction the sprite is facing
	 */
	public Direction getFacing()
	{
		return facing;
	}
	
	/**
	 * 
	 * @return true if the player's movement is done
	 */
	public boolean doneWalking()
	{
		return getActions().size == 0;
	}

	/**
	 * Compares and sorts player sprites by their Y position
	 */
	@Override
	public int compareTo(PlayerSprite p) {
		if (this.real.epsilonEquals(p.real, .05f))
			return 0;
		else if (this.real.y < p.real.y)
			return 1;
		else
			return -1;
	}
}
