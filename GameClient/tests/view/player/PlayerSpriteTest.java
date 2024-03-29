package view.player;

import static org.junit.Assert.*;

import org.junit.Test;

import view.player.Direction;
import view.player.PlayerSprite;

/**
 * 
 * @author nhydock
 */
public class PlayerSpriteTest
{

	/**
	 * Test creating a sprite (headless without texture)
	 */
	@Test
	public void testInitialize()
	{
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
	public void testMoving()
	{
		PlayerSprite sprite = new PlayerSprite();

		assertEquals(sprite.getX(), 0, .05);
		assertEquals(sprite.getY(), 0, .05);
		assertEquals(sprite.getFacing(), Direction.South);

		// set position by pixel
		sprite.move(32, 32);
		assertEquals(sprite.getX(), 0, .05);
		assertEquals(sprite.getY(), 0, .05);
		assertEquals(sprite.getFacing(), Direction.East);

		sprite.act(PlayerSprite.MOVESPEED / 2f);
		assertEquals(16, sprite.getX(), .05);
		assertEquals(16, sprite.getY(), .05);
		assertEquals(Direction.East, sprite.getFacing());
	}
}
