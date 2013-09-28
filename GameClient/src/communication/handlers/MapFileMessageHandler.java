package communication.handlers;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import model.ModelFacade;

import communication.messages.MapFileMessage;
import communication.messages.Message;



/**
 * Should process an incoming LoginResponseMessage.  This means that we should move our connection to the area server specified by that msg and initiate a session with that server
 * @author merlin
 *
 */
public class MapFileMessageHandler extends MessageHandler
{

	/**
	 * 
	 */
	private static final String MAP_FILE_TITLE = "maps/current.tmx";

	/**
	 * 
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		System.out.println("received " + msg);
		MapFileMessage mapFileMessage = (MapFileMessage)msg;
		writeToFile(MAP_FILE_TITLE, mapFileMessage.getContents() );
		ModelFacade.getSingleton().setMapFile(MAP_FILE_TITLE);
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return MapFileMessage.class;
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
}
