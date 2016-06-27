package com.itc.framework.reporters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.itc.framework.helpers.TestDetail;
import com.itc.framework.loggers.Log;

/**
 * Implementing the TestNG listener to customize the console out put and also
 * push the execution results to a excel file with case ID and test methods
 * 
 * @author ITC Infotech
 * 
 */
public class ITAFListener extends TestListenerAdapter {

	public static boolean m_quitExecution = false;
	private int m_passCount = 0;
	private int m_failCount = 0;
	private int m_skipCount = 0;

	// Log the test case details and result to a csv file
	FileWriter m_csvWriter;
	PrintWriter m_csvPrintWriter;

	// Log the Transaction Details for MFG DR Test
	FileWriter m_csvMfgDRWriter;
	PrintWriter m_csvMfgDRPrintWriter;

	@Override
	public void onStart(ITestContext testContext) {

		try {
			File dirPath = new File(testContext.getOutputDirectory() + "\\..\\");

			dirPath.mkdirs();

			m_csvWriter = new FileWriter(dirPath.getCanonicalPath()
					+ "/ExecutionReport.csv");
			m_csvPrintWriter = new PrintWriter(m_csvWriter);

			m_csvPrintWriter
					.println("CASE ID,TEST CASE NAME(@TestDetail),TEST METHOD,STATUS");
			m_csvPrintWriter.flush();
			// Writes the contents to file immediately after flushing

			String param = testContext.getCurrentXmlTest().getParameter(
					"transType");

			if (null != param) {
				m_csvMfgDRWriter = new FileWriter(dirPath.getCanonicalPath()
						+ "/MfgDRReport_" + param.toUpperCase() + ".csv");
				m_csvMfgDRPrintWriter = new PrintWriter(m_csvMfgDRWriter);

				m_csvMfgDRPrintWriter
						.println("ENTITY TYPE, ENTITY TYPE ID, DATE, TEST METHOD, TEST CASE NAME, STATUS");
				m_csvMfgDRPrintWriter.flush();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onFinish(ITestContext testContext) {

		(new PrintWriter(m_csvWriter)).close();

		if (null != m_csvMfgDRWriter)
			(new PrintWriter(m_csvMfgDRWriter)).close();
	}

	@Override
	public void onTestFailure(ITestResult tr) {

		m_failCount++;
		printCurrentStatus();

		TestDetail testMthdDetail = tr.getMethod().getConstructorOrMethod()
				.getMethod().getAnnotation(TestDetail.class);
		String testCaseName = "NA";
		String almTestCaseID = "NA";
		if (null != testMthdDetail) {

			if (null != testMthdDetail.testCaseID()
					&& !testMthdDetail.testCaseID().isEmpty())
				almTestCaseID = testMthdDetail.testCaseID();

			if (null != testMthdDetail.testCaseName()
					&& !testMthdDetail.testCaseName().isEmpty())
				testCaseName = testMthdDetail.testCaseName();

		}

		m_csvPrintWriter.println(almTestCaseID + "," + testCaseName + ","
				+ tr.getMethod().getConstructorOrMethod().getMethod().getName()
				+ "," + testCaseName + "," + "FAIL");
		m_csvPrintWriter.flush();

		Log.error(tr.getThrowable());

		if (null != m_csvMfgDRPrintWriter) {

			Object testDetails = tr.getTestContext().getAttribute(
					tr.getMethod().getConstructorOrMethod().getMethod()
							.getName());
			if (null != testDetails)
				m_csvMfgDRPrintWriter.println(testDetails + "," + testCaseName
						+ "," + "FAIL");
			else
				m_csvMfgDRPrintWriter.println("NA" + "," + "NA" + "," + "NA"
						+ "," + "NA" + "," + testCaseName + "," + "FAIL");

			m_csvMfgDRPrintWriter.flush();
		}

		if (null != tr.getThrowable())
			if (null != tr.getThrowable().getMessage()
					&& tr.getThrowable().getMessage().contains("Invalid"))
				m_quitExecution = true;
			else if (null != tr.getThrowable().getCause()
					&& tr.getThrowable().getCause().toString()
							.contains("Invalid"))
				m_quitExecution = true;

	}

	@Override
	public void onTestSkipped(ITestResult tr) {

		m_skipCount++;
		printCurrentStatus();

		TestDetail testMthdDetail = tr.getMethod().getConstructorOrMethod()
				.getMethod().getAnnotation(TestDetail.class);
		String testCaseName = "NA";
		String almTestCaseID = "NA";
		if (null != testMthdDetail) {

			if (null != testMthdDetail.testCaseID()
					&& !testMthdDetail.testCaseID().isEmpty())
				almTestCaseID = testMthdDetail.testCaseID();

			if (null != testMthdDetail.testCaseName()
					&& !testMthdDetail.testCaseName().isEmpty())
				testCaseName = testMthdDetail.testCaseName();

		}

		m_csvPrintWriter.println(almTestCaseID + "," + testCaseName + ","
				+ tr.getMethod().getConstructorOrMethod().getMethod().getName()
				+ "," + "SKIP");
		m_csvPrintWriter.flush();

		if (null != m_csvMfgDRPrintWriter) {

			Object testDetails = tr.getTestContext().getAttribute(
					tr.getMethod().getConstructorOrMethod().getMethod()
							.getName());
			if (null != testDetails)
				m_csvMfgDRPrintWriter.println(testDetails + "," + testCaseName
						+ "," + "SKIP");
			else
				m_csvMfgDRPrintWriter.println("NA" + "," + "NA" + "," + "NA"
						+ "," + "NA" + "," + testCaseName + "," + "SKIP");
			m_csvMfgDRPrintWriter.flush();
		}
	}

	@Override
	public void onTestSuccess(ITestResult tr) {

		m_passCount++;
		printCurrentStatus();

		TestDetail testMthdDetail = tr.getMethod().getConstructorOrMethod()
				.getMethod().getAnnotation(TestDetail.class);
		String testCaseName = "NA";
		String almTestCaseID = "NA";
		if (null != testMthdDetail) {

			if (null != testMthdDetail.testCaseID()
					&& !testMthdDetail.testCaseID().isEmpty())
				almTestCaseID = testMthdDetail.testCaseID();

			if (null != testMthdDetail.testCaseName()
					&& !testMthdDetail.testCaseName().isEmpty())
				testCaseName = testMthdDetail.testCaseName();

		}

		m_csvPrintWriter.println(almTestCaseID + "," + testCaseName + ","
				+ tr.getMethod().getConstructorOrMethod().getMethod().getName()
				+ "," + "PASS");
		m_csvPrintWriter.flush();

		if (null != m_csvMfgDRPrintWriter) {

			m_csvMfgDRPrintWriter.println(tr.getTestContext().getAttribute(
					tr.getMethod().getConstructorOrMethod().getMethod()
							.getName())
					+ "," + testCaseName + "," + "PASS");
			m_csvMfgDRPrintWriter.flush();
		}

	}

	public void printCurrentStatus() {
		System.out.println("\n CURRENT TEST EXECUTION STATUS -> PASS: "
				+ m_passCount + "  FAIL: " + m_failCount + "  SKIP: "
				+ m_skipCount + "\n");
	}

}
