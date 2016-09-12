package communication.handlers;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import model.CommandNewMap;
import model.ClientModelFacade;
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
			System.out.println(decodedPath.getPath());
			MapFileMessage mapFileMessage = (MapFileMessage) msg;
			String mapFile = (new URL(decodedPath,  mapFileMessage.getMapFileName() )).toURI()
					.getSchemeSpecificPart();

			ClientModelFacade.getSingleton().queueCommand(new CommandNewMap(mapFile));
		} catch (MalformedURLException | URISyntaxException e)
		{
			e.printStackTrace();
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
}
