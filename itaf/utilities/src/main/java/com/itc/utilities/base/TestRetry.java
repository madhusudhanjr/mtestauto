package com.itc.utilities.base;

import java.util.HashMap;
import java.util.Map;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.itc.framework.loggers.Log;

/**
 * This class enables re run of failed test cases. Test case will be executed
 * immediately after the failed run for a max number of times = maxCount Retry
 * can be configured at test case level by adding the class name to the @Test
 * annotation : @Test(retryAnalyzer = TestRetry.class,groups = { "sanity high"
 * })
 * 
 * @author ITC Infotech
 * 
 */
public class TestRetry implements IRetryAnalyzer {

	private int m_count = 0;
	private final int m_maxCount = 1;
	private String currentTest = null;

	/**
	 * Creating retryMap to store the combinations of Key as testMethodName +
	 * Customer and value as m_count in order to rerun the failed tests for all
	 * the Test tag tests while running in parallel
	 */
	private Map<String, Integer> retryMap = new HashMap<String, Integer>();

	public TestRetry() {

	}

	/**
	 * If the test case execution has failed and maximum run count is less than
	 * the maxCount then return true else false. Remove the previous failure run
	 * result from the ITestResult
	 */
	public synchronized boolean retry(ITestResult result) {

		Log.info("Running retry logic for  '" + result.getName()
				+ "' on class " + this.getClass().getName());

		Log.debug("Get TestMethodName + Customer");
		currentTest = result.getName()
				+ "_"
				+ result.getTestContext().getCurrentXmlTest()
						.getParameter("customer");

		if (null != retryMap && !retryMap.isEmpty()) {

			if (!retryMap.containsKey(currentTest))
				m_count = 0;
		}

		retryMap.put(currentTest, m_count);

		if (m_count < m_maxCount) {
			Log.info("Current Rerun Test: " + currentTest);
			result.getTestContext().getFailedTests()
					.removeResult(result.getMethod());
			m_count++;
			return true;
		}

		return false;
	}
}
