package model;

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
		ModelFacade.getSingleton(false).setHeadless(true);
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
		
		CommandNewMap cmd = new CommandNewMap("testMaps/simple.tmx");
		cmd.execute();
		
		EasyMock.verify(obs);
	}
}
