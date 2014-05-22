package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import communication.TypeDetector;

/**
 * These tests should be inherited into the tests for every QualifiedObservable
 * 
 * @author Merlin
 * 
 */
public abstract class QualifiedObservableTestInherited extends TypeDetector
{

	/**
	 * Make sure that it tells us the types of reports it notifies on correctly
	 */
	@Test
	public void reportsConsistently()
	{
		QualifiedObservable obs = getObservableBeingTested();
		ArrayList<Class<?>> reportTypes = this
				.detectAllExtendersInPackage(QualifiedObservableReport.class);
		ArrayList<Class<? extends QualifiedObservableReport>> usedReports = obs
				.getReportTypesWeSend();
		for (Class<?> reportType : reportTypes)
		{
			if (usedReports.contains(reportType))
			{
				assertTrue(obs.notifiesOn(reportType));
			} else
			{
				assertFalse(obs.notifiesOn(reportType));
			}
		}

	}

	protected abstract QualifiedObservable getObservableBeingTested();

}
