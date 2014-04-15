package communication.handlers;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import model.CommandNewMap;
import model.ModelFacade;
import communication.messages.MapFileMessage;
import communication.messages.Message;

/**
 * Should process an incoming LoginResponseMessage. This means that we should
 * move our connection to the area server specified by that msg and initiate a
 * session with that server
 * 
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
		URI path;
		try
		{
			path = MapFileMessageHandler.class.getProtectionDomain().getCodeSource()
					.getLocation().toURI();
		} catch (URISyntaxException e1)
		{
			e1.printStackTrace();
			return;
		}

		try
		{
			System.out.println("received " + msg);

			URL decodedPath = path.toURL();
			MapFileMessage mapFileMessage = (MapFileMessage) msg;
			String mapFile = (new URL(decodedPath, "../" + MAP_FILE_TITLE)).toURI()
					.getSchemeSpecificPart();
			writeToFile(mapFile, mapFileMessage.getContents());
			writeTileSets(mapFileMessage, path.getSchemeSpecificPart());

			ModelFacade.getSingleton().queueCommand(new CommandNewMap(mapFile));
		} catch (MalformedURLException | URISyntaxException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * 
	 */
	private void writeTileSets(MapFileMessage msg, String path)
	{
		ArrayList<String> imgFileTitles = msg.getImageFileTitles();
		ArrayList<byte[]> imgFiles = msg.getImageFiles();
		for (int i = 0; i < imgFileTitles.size(); i++)
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
		
		//make sure the directories exist for the file
		f.getParentFile().mkdirs();
		try
		{
			//make sure the file exists
			f.createNewFile();
			output = new FileOutputStream(f);
			output.write(contents);
			output.close();
		}catch (IOException e)
		{
			e.printStackTrace();
			fail();
		}
	}
}
