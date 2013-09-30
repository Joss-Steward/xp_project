package tmxfiles;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

/**
 * @author Merlin
 *
 */
public class TMXMapReaderTest
{

	/**
	 * Just make sure we can find the locations of the necessary images
	 */
	@Test
	public void test()
	{
		TMXMapReader reader = new TMXMapReader("maps/simple.tmx");
		
		ArrayList<String> imageFileTitles = reader.getImageFileTitles();
		assertEquals(2, imageFileTitles.size());
		assertEquals("tileset/grass-tiles-2-small.png",imageFileTitles.get(0));
		assertEquals("tileset/tree2-final.png",imageFileTitles.get(1));

	}

}
