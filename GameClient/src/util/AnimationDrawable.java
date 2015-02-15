package util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * A drawable class that wraps an animation's functionality,
 * allowing for an Image actor to display an animation without having
 * to create a large amount of 
 * @author nhydock
 *
 */
public class AnimationDrawable implements Drawable {

	Animation animation;
	TextureRegion currentFrame;
	float timer;
	
	/**
	 * Creates a new animation drawable
	 * @param animation - the animation to wrap
	 */
	public AnimationDrawable(Animation animation) {
		super();
		this.animation = animation;
		timer = 0;
		currentFrame = animation.getKeyFrame(0);
	}
	
	/**
	 * Set's the current position in the animation's timeline
	 * @param time - time in seconds
	 */
	public void setTime(float time) {
		timer = time;
		
		if (animation.animationDuration < timer) {
			timer = 0;
		}
		
		currentFrame = animation.getKeyFrame(timer);
	}
	
	/**
	 * Advances an animation's current position in time
	 * @param delta - time in seconds since last update
	 */
	public void update(float delta) {
		timer += delta;
		currentFrame = animation.getKeyFrame(timer);
	}
	
	/**
	 * Draws the current frame of animation to the batch
	 */
	@Override
	public void draw(Batch batch, float x, float y, float width, float height) {
		batch.draw(currentFrame, x, y, width, height);
	}

	@Override
	public float getLeftWidth() {
		return 0;
	}

	@Override
	public void setLeftWidth(float leftWidth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getRightWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRightWidth(float rightWidth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getTopHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTopHeight(float topHeight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getBottomHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setBottomHeight(float bottomHeight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getMinWidth() {
		return currentFrame.getRegionWidth();
	}

	@Override
	public void setMinWidth(float minWidth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getMinHeight() {
		return currentFrame.getRegionHeight();
	}

	@Override
	public void setMinHeight(float minHeight) {
		// TODO Auto-generated method stub
		
	}
}
