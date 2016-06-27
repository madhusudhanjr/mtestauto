package com.itc.amazon.tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.TestNG;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.itc.amazon.pageobjects.AmazonHomePage;
import com.itc.framework.helpers.TestDetail;
import com.itc.framework.loggers.Log;
import com.itc.utilities.base.BaseTest;
import com.itc.utilities.base.TestRetry;

/**
 * AmazonSearch Test Class contains all the tests related to Search
 * Functionality in Amazon Page (www.amazon.in) for eWellness Solution
 * 
 * @author ITC Infotech
 * 
 */
public class AmazonSearch extends BaseTest {

	/**
	 * This Method is used to test Search button action in Hierarchy Page of
	 * eWellness Solution
	 * 
	 * @param customer
	 * @param url
	 * @throws Exception
	 */
	@TestDetail(testCaseID = "NA", testCaseName = "Amazon -> Search", author = "Madhusudhan JR")
	@Parameters({ "mode", "customer", "url" })
	@Test(dataProvider="TestData", retryAnalyzer = TestRetry.class)
	public void searchResults(String testData) throws Exception {

		Log.info("launching the Amazon Application");
		launchApplication(m_url);

		Log.info("Launch Amazon HOME Page");
		AmazonHomePage amazonPage = new AmazonHomePage(
				getDriverInstanceForThread());
		String results = amazonPage.getSearchResults(m_executionMode,
				m_customer, testData);

		Log.info("Search Result Text:: " + results);
		Assert.assertTrue(!results.isEmpty(), "Search Results NOT displayed!!!");
		Log.info("Search Results verified successfully!!!");
		Reporter.log("Search Results verified successfully:: " + results);
	}

	@DataProvider(name = "TestData")
	public Object[][] getTestDataSet() {

		AmazonHomePage amazonPage = new AmazonHomePage(
				getDriverInstanceForThread());
		List<String> testDataSet = amazonPage.getTestDataForAmazonSearch();

		int i = 0;
		Object[][] testData = new Object[testDataSet.size()][1];
		for (String data : testDataSet) {

			testData[i][0] = data;
			i++;
		}

		return testData;

	}

	/**
	 * Java Main method, this is the entry point for the executable jar to
	 * execute the Test Case from Command Line
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Test Execution Sarted....!!");

		List<XmlSuite> xmlSuites = new ArrayList<XmlSuite>();
		XmlSuite suite = new XmlSuite();
		suite.setName("Amazon Test Suite");
		xmlSuites.add(suite);
		// suite.setParameters(options.convertToMap());

		List<XmlClass> classes = new ArrayList<XmlClass>();
		classes.add(new XmlClass("com.itc.amazon.tests.AmazonSearch"));

		XmlTest test = new XmlTest(suite);
		test.setName("MyTests");
		test.setXmlClasses(classes);

		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);

		TestNG testNG = new TestNG();
		testNG.setXmlSuites(suites);
		testNG.run();

		/*ITAFCustomReport customReport = new ITAFCustomReport();
		customReport.generateReport(xmlSuites, suites,
				m_context.getOutputDirectory());*/
		System.out.println("Test Execution Completed....!!");
	}

}
