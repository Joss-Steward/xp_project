package model;

import static org.junit.Assert.fail;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.Observable;
import java.util.Observer;

import model.reports.NewMapReport;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Merlin
 *
 */
public class CommandNewMapTest
{

	/**
	 * 
	 */
	@Before
	public void setup()
	{
		ModelFacade.getSingleton(true, true);
	}
	
	/**
	 * 
	 */
	@Test
	public void testExecution()
	{
		Observer obs = EasyMock.createMock(Observer.class);
		obs.update(EasyMock.isA(Observable.class), EasyMock.isA(NewMapReport.class));
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				NewMapReport.class);
		EasyMock.replay(obs);
		
		//setup the player
		PlayerManager pm = PlayerManager.getSingleton();
		pm.initiateLogin("john", "pw");
		try {
			pm.setThisClientsPlayer(1);
		} catch (AlreadyBoundException | NotBoundException e) {
			e.printStackTrace();
			fail("Could not create this client's player from login");
		}
		
		CommandNewMap cmd = new CommandNewMap("testMaps/simple.tmx");
		cmd.execute();
		
		EasyMock.verify(obs);
	}
}
