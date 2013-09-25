import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import model.CommandLogin;
import model.Player;

import communication.ConnectionManager;
import communication.LoginResponseMessageHandler;
import communication.MessageHandlerSet;
import communication.MovementMessageHandler;
import communication.PlayerJoinedMessageHandler;
import communication.messages.LoginResponseMessage;
import communication.messages.MovementMessage;
import communication.messages.PlayerJoinedMessage;
import communication.packers.MessagePackerSet;

import data.Position;

/**
 * Temporary to play with communication protocols. Will eventually start a
 * client.
 * 
 * @author merlin
 * 
 */
public class ClientRunner
{
	/**
	 * 
	 * @param args
	 *            unused
	 * @throws UnknownHostException
	 *             shouldn't
	 * @throws IOException
	 *             shouldn't
	 */
	public static void main(String[] args) throws UnknownHostException,
			IOException
	{
		Socket socket = new Socket("localhost", 1871);
		MessageHandlerSet handlers = new MessageHandlerSet();
		MovementMessageHandler movementHandler = new MovementMessageHandler();
		handlers.registerHandler(MovementMessage.class, movementHandler);
		handlers.registerHandler(LoginResponseMessage.class, new LoginResponseMessageHandler());
		handlers.registerHandler(PlayerJoinedMessage.class, new PlayerJoinedMessageHandler());
		ConnectionManager.getSingleton().createInitialConnection(socket, handlers, new MessagePackerSet());

		Scanner scanner = new Scanner(System.in);
		System.out.println("input?");
		String input = scanner.nextLine();
		String[] tokens = input.split(" ");
		while (!tokens[0].equalsIgnoreCase("quit"))
		{
			if (tokens[0].equalsIgnoreCase("move"))
			{
				String[] positionParts = tokens[1].split(",");
				Player.getSingleton().move(
						new Position(Integer.parseInt(positionParts[0]),
								Integer.parseInt(positionParts[1])));
				System.out.println("user moved " + Player.getSingleton().getID() + " to " + positionParts[0] + ", " + positionParts[1]);
			} else if (tokens[0].equalsIgnoreCase("login"))
			{
				CommandLogin command = new CommandLogin(tokens[1],tokens[2]);
				command.execute();
				System.out.println("user specified id " + tokens[1]);
			} else 
			{
				System.out.println("unrecognized command " + tokens[0]);
			}
			input = scanner.nextLine();
			tokens = input.split(" ");
		};
		System.out.println("leaving");
		scanner.close();
		ConnectionManager.getSingleton().disconnect();
	}

}
