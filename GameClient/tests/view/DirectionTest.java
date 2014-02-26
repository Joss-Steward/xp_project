package view;

import static org.junit.Assert.*;

import org.junit.Test;

import view.Direction;

import com.badlogic.gdx.math.Vector2;

public class DirectionTest {

	@Test
	public void testGetDirectionFromVector() {
		Vector2 from = new Vector2();
		Vector2 to = new Vector2();
		
		//look to the east
		from.set(0, 0);
		to.set(1, 0);
		assertEquals(Direction.East, Direction.getFacing(from, to));
		
		//look down to the east (tests major x)
		from.set(0, 1);
		to.set(1, 0);
		assertEquals(Direction.East, Direction.getFacing(from, to));
		
		//look to the west
		from.set(2, 0);
		to.set(1, 0);
		assertEquals(Direction.West, Direction.getFacing(from, to));
		
		//look north
		from.set(0, 0);
		to.set(0, -1);
		assertEquals(Direction.North, Direction.getFacing(from, to));
		
		//look south
		from.set(0, 0);
		to.set(0, 1);
		assertEquals(Direction.South, Direction.getFacing(from, to));
		
		//test priority difference for setting direction
		from.set(0, 0);
		to.set(5, 3);
		assertEquals(Direction.East, Direction.getFacing(from, to));
		to.set(3, 5);
		assertEquals(Direction.South, Direction.getFacing(from, to));	
	}
}
