package communication.messages;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import junitx.framework.FileAssert;

import org.junit.Test;

/**
 * Tests a file message which is designed to carry a tmx file
 * 
 * @author merlin
 * 
 */
public class MapFileMessageTest
{
	/**
	 * Make sure its toString is correct
	 * 
	 * @throws Exception
	 *             shouldn't
	 */
	@Test
	public void testToStringAndFileStorage() throws Exception
	{
		MapFileMessage msg = new MapFileMessage("maps/current.tmx");
		assertEquals("FileMessage: number of bytes = 753 number of tilesets = 2",
				msg.toString());
		assertEquals("current.tmx", msg.getFileTitle());
		writeToFile("maps/test.tmx", msg.getContents());
		File expected = new File("maps/current.tmx");
		File actual = new File("maps/test.tmx");
		FileAssert.assertEquals(expected, actual);
		ArrayList<String> imageFileTitles = msg.getImageFileTitles();
		ArrayList<byte[]> imageFiles = msg.getImageFiles();
		assertEquals(2, imageFileTitles.size());
		assertEquals("tileset/qubodup-bush_0.png", imageFileTitles.get(0));
		assertEquals("tileset/qubodup-bush_berries_0.png", imageFileTitles.get(1));
		assertEquals(2, imageFiles.size());
	}

	/**
	 * @param string
	 * @param contents
	 */
	private void writeToFile(String title, byte[] contents)
	{
		File f = new File(title);
		FileOutputStream output;
		try
		{
			output = new FileOutputStream(f);
			output.write(contents);
			output.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			fail();
		} catch (IOException e)
		{
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * make sure it throws an exception on the constructor call if the file
	 * isn't there
	 * 
	 * @throws Exception
	 *             should throw the expected one
	 */
	@Test(expected = FileNotFoundException.class)
	public void throwsExceptionIfNoFile() throws Exception
	{
		new MapFileMessage("no file here");
	}
}
