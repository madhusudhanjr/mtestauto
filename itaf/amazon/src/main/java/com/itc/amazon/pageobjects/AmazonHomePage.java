package com.itc.amazon.pageobjects;

import io.appium.java_client.android.AndroidDriver;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.itc.framework.loggers.Log;
import com.itc.utilities.base.Page;
import com.itc.utilities.elementfactory.ButtonElement;
import com.itc.utilities.elementfactory.Element;
import com.itc.utilities.elementfactory.TextBoxElement;
import com.itc.utilities.enums.ExecutionMode;
import com.itc.utilities.exceptions.ElementException;
import com.itc.utilities.helpers.CommonHelper;

/**
 * AmazonPage Class consists object locators and actions of Amazon Page.
 * 
 * @author ITC Infotech
 */

public class AmazonHomePage extends Page {

	/**
	 * m_workbook is a Class level member which will have the reference to the
	 * AmazonPage workbook.
	 */
	private static String m_workbook = "TestData/Amazon.ods";

	/**
	 * Constructor for AmazonPage class which uses super constructor to load the
	 * Map of Object Repository of AmazonPage.
	 * 
	 * @param driver
	 */

	public AmazonHomePage(WebDriver driver) {
		super(m_workbook, "Page");
		this.m_pageDriver = driver;
	}

	@Override
	public boolean isDisplayed() throws ElementException {
		return false;
	}

	public WebElement getElementInPage(String locatorLogicalName,
			String executionMode, boolean isMandatory) throws ElementException {

		return getElementInPage(locatorLogicalName, executionMode, null,
				isMandatory);
	}

	/**
	 * This method is used to get the WebElement of "Search Text Box" of Amazon
	 * Home Page.
	 * 
	 * @return SearchBox WebElement
	 * @throws ElementException
	 */
	public WebElement getSearhTextBox(String executionMode)
			throws ElementException {

		return getElementInPage("amazon_homepage_search", executionMode, true);
	}

	/**
	 * This method is used to get the WebElement of "Search Text Box" of Amazon
	 * Home Page. (used for Android Native Application Testing)
	 * 
	 * @return SearchBox WebElement
	 * @throws ElementException
	 */
	public WebElement getElementToClickcSearhTextBox(String executionMode)
			throws ElementException {

		return getElementInPage("amazon_app_search_click", executionMode, true);
	}

	/**
	 * This method is used to get the WebElement of "Skip Sign In" of Amazon
	 * App. (used for Android Native Application Testing)
	 * 
	 * @return SearchBox WebElement
	 * @throws ElementException
	 */
	public WebElement getElementToClickSkipSignIn(String executionMode)
			throws ElementException {

		return getElementInPage("amazon_app_skipsignin", executionMode, true);
	}

	/**
	 * This method is used to get the WebElement of "Search Box Submit Button"
	 * of Amazon Home Page.
	 * 
	 * @return SearchBox WebElement
	 * @throws ElementException
	 */
	public WebElement getSearchSubmitButton(String executionMode)
			throws ElementException {

		return getElementInPage("amazon_homepage_search_submit", executionMode,
				true);
	}

	/**
	 * This method is used to get the WebElement of "Search Results Tex" of
	 * Amazon Home Page.
	 * 
	 * @return SearchBox WebElement
	 * @throws ElementException
	 */
	public WebElement getSearchResultText(String executionMode)
			throws ElementException {

		return getElementInPage("amazon_homepage_searchresults", executionMode,
				true);
	}

	/**
	 * This method gets the search results based on the search keywords entered
	 * in Amazon Home Page
	 * 
	 * @return Search Results
	 * @throws Exception
	 */
	public String getSearchResults(String executionMode, String customer,
			String testData) throws Exception {

		Log.debug("Get Search Results");

		if (isElementExistsInPage("amazon_app_skipsignin", executionMode))
			ButtonElement.click("Click Skip Sign In Link",
					getElementToClickSkipSignIn(executionMode));

		if (isElementExistsInPage("amazon_app_search_click", executionMode))
			TextBoxElement.click("Click Search Text Box",
					getElementToClickcSearhTextBox(executionMode));

		if (executionMode
				.equalsIgnoreCase(ExecutionMode.ANDROID_APP.toString()))
			TextBoxElement.clearAndEnterText("Search Text Box",
					getSearhTextBox(executionMode), testData,
					(AndroidDriver) m_pageDriver, executionMode);
		else
			TextBoxElement.clearAndEnterText("Search Text Box",
					getSearhTextBox(executionMode), testData, null,
					executionMode);

		ButtonElement.click("Search Submit Button",
				getSearchSubmitButton(executionMode));

		String result = Element.getText("Search Results Text",
				getSearchResultText(executionMode));

		if (executionMode
				.equalsIgnoreCase(ExecutionMode.ANDROID_APP.toString())) {

			m_pageDriver.navigate().back();
			CommonHelper.waitInSeconds(1);
			m_pageDriver.navigate().back();
		}

		return result;
	}

	public List<String> getTestDataForAmazonSearch() {

		return getTestDataSet("amazon_homepage_search", "Amazon");
	}
}
