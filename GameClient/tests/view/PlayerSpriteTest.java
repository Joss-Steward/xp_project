package view;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author nhydock
 */
public class PlayerSpriteTest {

	/**
	 * Test creating a sprite (headless without texture)
	 */
	@Test
	public void testInitialize() {
		PlayerSprite sprite = new PlayerSprite();
		
		assertNotNull(sprite);
		assertEquals(sprite.getX(), 0, .05);
		assertEquals(sprite.getY(), 0, .05);
		assertEquals(sprite.getFacing(), Direction.South);
	}

	/**
	 * Test moving and tweening a sprite
	 */
	@Test
	public void testMoving() {
		PlayerSprite sprite = new PlayerSprite();
		
		assertEquals(sprite.getX(), 0, .05);
		assertEquals(sprite.getY(), 0, .05);
		assertEquals(sprite.getFacing(), Direction.South);
		
		//set position by pixel
		sprite.setPosition(32, 32);
		assertEquals(sprite.getX(), 0, .05);
		assertEquals(sprite.getY(), 0, .05);
		assertEquals(sprite.getFacing(), Direction.East);
		
		sprite.update(.5f);
		assertEquals(sprite.getX(), 16, .05);
		assertEquals(sprite.getY(), 16, .05);
		assertEquals(sprite.getFacing(), Direction.East);
	}
}
