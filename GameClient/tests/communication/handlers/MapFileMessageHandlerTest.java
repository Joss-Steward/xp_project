package communication.handlers;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import junitx.framework.FileAssert;
import model.ModelFacade;

import org.junit.Test;

import communication.messages.MapFileMessage;

/**
 * @author Merlin
 *
 */
public class MapFileMessageHandlerTest
{
	
	/**
	 * The handler should write the file to current.tmx
	 * @throws IOException shouldn't
	 */
	@Test
	public void writesTheFile() throws IOException
	{
		new File("../GameClient-desktop/maps/current.tmx").delete();
		new File("../GameClient-desktop/maps/tileset/grass-tiles-2-small.png").delete();
		new File("../GameClient-desktop/maps/tileset/tree2-final.png").delete();
		
		
		MapFileMessage msg = new MapFileMessage("testMaps/simple.tmx");
		MapFileMessageHandler handler = new MapFileMessageHandler();
		handler.process(msg);
		
		File expected = new File("testMaps/simple.tmx");
		File actual = new File("maps/current.tmx");
		FileAssert.assertEquals(expected, actual);
		
		expected = new File("testMaps/tileset/grass-tiles-2-small.png");
		actual = new File("maps/tileset/grass-tiles-2-small.png");
		FileAssert.assertEquals(expected, actual);
		
		expected = new File("testMaps/tileset/tree2-final.png");
		actual = new File("maps/tileset/tree2-final.png");
		FileAssert.assertEquals(expected, actual);
	}
	/**
	 * The handler should tell the model that the new file is there
	 * @throws IOException shouldn't
	 */
	@Test
	public void tellsEngine() throws IOException
	{
		MapFileMessage msg = new MapFileMessage("testMaps/simple.tmx");
		MapFileMessageHandler handler = new MapFileMessageHandler();
		handler.process(msg);
		
		assertEquals("maps/current.tmx",ModelFacade.getSingleton().getMapFileTitle());
	}
}
