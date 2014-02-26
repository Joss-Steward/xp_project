package view;

import com.badlogic.gdx.math.Vector2;

/**
 * Facing direction of the sprite
 */
public enum Direction {
	/**
	 * Looking down the screen
	 */
	South,
	/**
	 * Looking up the screen
	 */
	North,
	/**
	 * Looking left on the screen
	 */
	West,
	/**
	 * Looking right on the screen
	 */
	East;
	
	/**
	 * Get the direction of facing between two points
	 * @param from
	 * 	source position
	 * @param to
	 *  destination position
	 * @return the direction of facing between point a to point b
	 */
	public static Direction getFacing(Vector2 from, Vector2 to)
	{
		Direction out = North;
		float diffX = Math.abs(to.x - from.x);
		float diffY = Math.abs(to.y - from.y);
		
		if (diffX >= diffY) {
		
			if (from.x > to.x) {
				out = West;
			}
			else {
				out = East;
			}
		
		}else{
			if (from.y < to.y) {
				out = South;	
			} 
			else {
				out = North;
			}
		}
		
		return out;
	}
}
