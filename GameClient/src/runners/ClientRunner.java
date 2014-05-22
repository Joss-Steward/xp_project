package runners;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import communication.ConnectionManager;
import communication.StateAccumulator;
import communication.handlers.MessageHandlerSet;
import communication.packers.MessagePackerSet;
import data.Position;
import edu.ship.shipsim.client.model.CommandLogin;
import edu.ship.shipsim.client.model.CommandMovePlayer;
import edu.ship.shipsim.client.model.ModelFacade;
import edu.ship.shipsim.client.model.PlayerManager;

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
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		Socket socket = new Socket("localhost", 1871);
		MessagePackerSet messagePackerSet = new MessagePackerSet();
		StateAccumulator stateAccumulator = new StateAccumulator(messagePackerSet);
		
		ConnectionManager cm = new ConnectionManager(socket, stateAccumulator,
				new MessageHandlerSet(stateAccumulator), messagePackerSet);
		ModelFacade modelFacade = ModelFacade.getSingleton(true, false);

		Scanner scanner = new Scanner(System.in);
		System.out.println("input?");
		String input = scanner.nextLine();
		String[] tokens = input.split(" ");
		while (!tokens[0].equalsIgnoreCase("quit"))
		{
			if (tokens[0].equalsIgnoreCase("move"))
			{
				String[] positionParts = tokens[1].split(",");
				CommandMovePlayer command = new CommandMovePlayer(PlayerManager
						.getSingleton().getThisClientsPlayer().getID(), new Position(
						Integer.parseInt(positionParts[0]),
						Integer.parseInt(positionParts[1])));
				modelFacade.queueCommand(command);
				System.out.println("player moved "
						+ PlayerManager.getSingleton().getThisClientsPlayer().getID()
						+ " to " + positionParts[0] + ", " + positionParts[1]);
			} else if (tokens[0].equalsIgnoreCase("login"))
			{
				CommandLogin command = new CommandLogin(tokens[1], tokens[2]);
				modelFacade.queueCommand(command);
				System.out.println("player specified id " + tokens[1]);
			} else
			{
				System.out.println("unrecognized command " + tokens[0]);
			}
			input = scanner.nextLine();
			tokens = input.split(" ");
		}
		;
		System.out.println("leaving");
		scanner.close();
		cm.disconnect();
	}

}
