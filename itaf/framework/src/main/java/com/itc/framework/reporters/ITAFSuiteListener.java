package com.itc.framework.reporters;

import java.util.Calendar;
import java.util.Date;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class ITAFSuiteListener implements ISuiteListener{
	
	
	private static Date m_suiteExecutionStartTime;
	
	private static Date m_suiteExecutionEndTime;

	public void onStart(ISuite suite) {
		
		//Set the execution start time
		Calendar calendar = Calendar.getInstance();
		m_suiteExecutionStartTime = calendar.getTime();		
		
	}

	public void onFinish(ISuite suite) {
		
		//Set the execution end time
		Calendar calendar = Calendar.getInstance();
		m_suiteExecutionEndTime = calendar.getTime();	
						
	}
	
	public static Date getSuiteStartTime() { 	return m_suiteExecutionStartTime; }
	
	public static Date getSuiteEndTime() { 	return m_suiteExecutionEndTime; }

}
