package com.itc.framework.reporters;

import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.SkipException;

/**
 * Implementing the iTAFHook listener which invokes each and every Test Method
 * and Skips the test execution if the Invalid Login Credentials are supplied to
 * the Test Suite.
 * 
 * @author ITC Infotech
 */
public class iTAFHook implements IHookable {

	public void run(IHookCallBack paramIHookCallBack,
			ITestResult paramITestResult) {

		if (!ITAFListener.m_quitExecution)
			paramIHookCallBack.runTestMethod(paramITestResult);
		else
			throw new SkipException(
					"Test Skipped due to Invalid Login Credentials");

	}

}
