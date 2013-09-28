package communication.messages;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import junitx.framework.FileAssert;

import org.junit.Test;

/**
 * Tests a file message which is designed to carry a tmx file
 * @author merlin
 *
 */
public class FileMessageTest
{
	/**
	 * Make sure its toString is correct
	 * @throws IOException shouldn't
	 */
	@Test
	public void testToString() throws IOException
	{
		FileMessage msg = new FileMessage("maps/simple.tmx");
		assertEquals("FileMessage: title = simple.tmx", msg.toString());
		assertEquals("simple.tmx", msg.getFileTitle());
		writeToFile("maps/test.tmx", msg.getContents());
		File expected = new File("maps/simple.tmx");
		File actual = new File("maps/test.tmx");
		FileAssert.assertEquals(expected, actual);
		
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
	 * make sure it throws an exception on the constructor call if the file isn't there
	 * @throws IOException should get FileNotFoundException, not the more general IOException 
	 */
	@Test(expected=FileNotFoundException.class)
	public void throwsExceptionIfNoFile() throws IOException
	{
		new FileMessage("no file here");
	}
}
