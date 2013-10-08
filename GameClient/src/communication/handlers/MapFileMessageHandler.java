package communication.handlers;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import model.CommandNewMap;
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
		String path = MapFileMessageHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try
		{
			System.out.println("received " + msg);
			
			String decodedPath = URLDecoder.decode(path, "UTF-8").substring(1);
			MapFileMessage mapFileMessage = (MapFileMessage)msg;
			System.out.println("handler decoded path:"+decodedPath);
			writeToFile(decodedPath + "../" + MAP_FILE_TITLE, mapFileMessage.getContents() );
			writeTileSets(mapFileMessage, decodedPath);
			
			ModelFacade.getSingleton(false).queueCommand(new CommandNewMap(decodedPath + "../" + MAP_FILE_TITLE));
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 */
	private void writeTileSets(MapFileMessage msg,String path)
	{
		ArrayList<String> imgFileTitles = msg.getImageFileTitles();
		ArrayList<byte[]> imgFiles = msg.getImageFiles();
		for (int i=0;i<imgFileTitles.size();i++)
		{
			writeToFile(path + "../maps/" + imgFileTitles.get(i), imgFiles.get(i));
		}
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
