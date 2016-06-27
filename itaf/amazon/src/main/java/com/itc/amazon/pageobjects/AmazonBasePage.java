package com.itc.amazon.pageobjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.itc.framework.loggers.Log;
import com.itc.utilities.base.Page;
import com.itc.utilities.base.PageWebElementDetails;
import com.itc.utilities.exceptions.ElementException;

/**
 * AmazonBasePage Class consists of generic methods that will be extended by
 * all the Page Objects of Amazon.
 * 
 * @author ITC Infotech
 */
public class AmazonBasePage extends Page {

	/** The m_common web element map. */
	private static Map<String, List<String>> m_commonWebElementMap;

	/**
	 * Constructor class that will call Page class constructor and also create a
	 * map of eWellness common web element
	 * 
	 * @param driver
	 *            the driver
	 * @param ORWorkBook
	 *            the oR work book
	 * @param ORSheet
	 *            the oR sheet
	 */

	protected AmazonBasePage(WebDriver driver, String ORWorkBook, String ORSheet) {
		super(ORWorkBook, ORSheet);
		if (null == m_commonWebElementMap) {
			m_commonWebElementMap = new HashMap<String, List<String>>();

			PageWebElementDetails.createObjectMap("TestData/Common.ods",
					"common", this.getClass().getClassLoader()
							.getResourceAsStream("TestData/Common.ods"),
					m_commonWebElementMap, null);
		}

		this.m_pageDriver = driver;
	}

	/**
	 * Returns the Selenium.WebElement based on the logical name passed as the
	 * argument
	 * 
	 * @param locatorLogicalName
	 *            the locator logical name
	 * @param isMandatory
	 * @return the Selenium.Weblement corresponding to the locator
	 * @throws ElementException
	 */
	public WebElement getElementInPage(String locatorLogicalName,
			String executionMode, boolean isMandatory) throws ElementException {

		return getElementInPage(locatorLogicalName, executionMode,
				m_commonWebElementMap, isMandatory);
	}

	/**
	 * Returns the list of child Selenium.WebElement based on the logical name
	 * passed as the argument
	 * 
	 * @param parentElement
	 *            the parent element
	 * @param locatorLogicalName
	 *            the locator logical name
	 * @param isMandatory
	 *            the is mandatory
	 * @return List of Selenium.WebElement
	 * @throws ElementException
	 */
	public List<WebElement> getChildElementsinPage(WebElement parentElement,
			String locatorLogicalName, String executionMode, boolean isMandatory)
			throws ElementException {

		return getChildElementsinPage(parentElement, locatorLogicalName,
				executionMode, m_commonWebElementMap, isMandatory);
	}

	/**
	 * Returns the child Selenium.WebElement based on the logical name passed as
	 * the argument
	 * 
	 * @param parentElement
	 *            the parent element
	 * @param locatorLogicalName
	 *            the locator logical name
	 * @param isMandatory
	 *            the is mandatory
	 * @return Selenium.WebElement
	 * @throws ElementException
	 */
	public WebElement getChildElementinPage(WebElement parentElement,
			String locatorLogicalName, String executionMode, boolean isMandatory)
			throws ElementException {

		Log.debug("Get child element with LOGICAL NAME : " + locatorLogicalName);

		return getChildElementinPage(parentElement, locatorLogicalName,
				executionMode, m_commonWebElementMap, isMandatory);
	}

	/**
	 * Gets the element in tab.
	 * 
	 * @param locatorLogicalName
	 * @param isMandatory
	 * @param index
	 *            row number in the tab. Will be appended to the locator to get
	 *            the webelement
	 * @return Selenium.WebElement
	 * @throws ElementException
	 */
	public WebElement getElementInTab(String locatorLogicalName,
			String executionMode, boolean isMandatory, int index)
			throws ElementException {

		Log.debug("Get Element with LOGICAL NAME : " + locatorLogicalName);

		return getElementInTab(locatorLogicalName, executionMode,
				m_commonWebElementMap, isMandatory, index);
	}

	/**
	 * Gets the elements in page.
	 * 
	 * @param locatorLogicalName
	 *            the locator logical name
	 * @param isMandatory
	 *            the is mandatory
	 * @return the elements in page
	 * @throws ElementException
	 *             the element exception
	 */
	public List<WebElement> getElementsInPage(String locatorLogicalName,
			String executionMode, boolean isMandatory) throws ElementException {

		Log.debug("Get list of Elements with LOGICAL NAME : "
				+ locatorLogicalName);

		return getElementsInPage(locatorLogicalName, executionMode,
				m_commonWebElementMap, isMandatory);
	}

	/**
	 * Gets the locator type.
	 * 
	 * @param logicalname
	 *            the logicalname
	 * @return the locator type
	 */
	public String getLocatorType(String logicalname) {

		return getLocatorType(logicalname, m_commonWebElementMap);
	}

	/**
	 * Gets the locator.
	 * 
	 * @param logicalname
	 *            the logicalname
	 * @return the locator
	 */
	public String getLocator(String logicalname, String executionMode) {

		return getLocator(logicalname, executionMode, m_commonWebElementMap);
	}

	@Override
	public boolean isDisplayed() throws ElementException {

		return false;
	}

}
