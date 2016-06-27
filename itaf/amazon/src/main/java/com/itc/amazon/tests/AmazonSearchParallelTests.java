package com.itc.amazon.tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
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
public class AmazonSearchParallelTests extends BaseTest {

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
	@Test(retryAnalyzer = TestRetry.class)
	public void searchITCProducts() throws Exception {

		Log.info("launching the Amazon Application");
		launchApplication(m_url);

		Log.info("Launch Amazon HOME Page");
		AmazonHomePage amazonPage = new AmazonHomePage(
				getDriverInstanceForThread());
		String results = amazonPage.getSearchResults(m_executionMode,
				m_customer, "TestData_1");

		Log.info("Search Result Text:: " + results);
		Assert.assertTrue(!results.isEmpty(), "Search Results NOT displayed!!!");
		Log.info("Search Results verified successfully!!!");
	}
	
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
	@Test(retryAnalyzer = TestRetry.class)
	public void searchITClothing() throws Exception {

		Log.info("launching the Amazon Application");
		launchApplication(m_url);

		Log.info("Launch Amazon HOME Page");
		AmazonHomePage amazonPage = new AmazonHomePage(
				getDriverInstanceForThread());
		String results = amazonPage.getSearchResults(m_executionMode,
				m_customer, "TestData_2");

		Log.info("Search Result Text:: " + results);
		Assert.assertTrue(!results.isEmpty(), "Search Results NOT displayed!!!");
		Log.info("Search Results verified successfully!!!");
	}

	
}
