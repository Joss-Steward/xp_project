package view;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Observable;

import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.reports.StubQualifiedObservableReport1;
import model.reports.StubQualifiedObservableReport2;

import org.junit.Test;

/**
 * Test the non-abstract stuff in ScreenListener
 * 
 * @author Merlin
 * 
 */
public class ScreenListenerTest
{

	/**
	 * When a ScreenListener is created, it should automatically hook itself up
	 * to listen for the types of reports it needs
	 */
	@Test
	public void testAddsItselfCorrectly()
	{
		ScreenListener underTest = new MockScreenListener();
		QualifiedObservableConnector connector = QualifiedObservableConnector
				.getSingleton();
		assertTrue(connector.doIObserve(underTest, StubQualifiedObservableReport1.class));
		assertTrue(connector.doIObserve(underTest, StubQualifiedObservableReport2.class));
	}

	private class MockScreenListener extends ScreenListener
	{
		public MockScreenListener()
		{
			super.setUpListening();
		}

		@Override
		public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
		{
			ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = 
					new ArrayList<Class<? extends QualifiedObservableReport>>();
			reportTypes.add(StubQualifiedObservableReport1.class);
			reportTypes.add(StubQualifiedObservableReport2.class);

			return reportTypes;
		}

		@Override
		public void update(Observable arg0, Object arg1)
		{
			// don't need anything here
		}

	}

}
