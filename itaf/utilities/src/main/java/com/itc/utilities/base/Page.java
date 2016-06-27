package com.itc.utilities.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.itc.framework.helpers.FrameworkConstants;
import com.itc.framework.loggers.Log;
import com.itc.utilities.elementfactory.Element;
import com.itc.utilities.enums.ExecutionMode;
import com.itc.utilities.enums.LocatorType;
import com.itc.utilities.exceptions.DataSourceException;
import com.itc.utilities.exceptions.ElementException;
import com.itc.utilities.helpers.CommonHelper;

/**
 * Page Class consists of generic methods that will be extended by all the Page
 * Classes.
 * 
 * @author ITC Infotech
 */

public abstract class Page {

	/**
	 * Abstract Method which will be overridden by the respective Class
	 * extending the Page Class to check whether the components are displayed in
	 * the respective Pages.
	 * 
	 * @return
	 * @throws ElementException
	 */
	public abstract boolean isDisplayed() throws ElementException;

	/**
	 * Handle to the Selenium web driver object which will be used for all the
	 * actions on the web elements
	 */
	protected WebDriver m_pageDriver;

	// Map created out of Page Specific Web Element ODS file
	private Map<String, List<String>> m_pgSpecificWebElementMap;

	// Index in List of m_pgSpecificWebElementMap for test data
	private Map<String, Integer> m_testDataIndex;

	/**
	 * Constructor for Page Class which takes input as page specific workbook
	 * and worksheet names and updates the m_pgSpecificWebElementMap and
	 * m_testDataIndex object with LogicalName and value as the WebElement
	 * Locator and TestData
	 * 
	 * @param ORWorkBook
	 * @param ORSheet
	 */
	protected Page(String ORWorkBook, String ORSheet) {

		m_pgSpecificWebElementMap = new HashMap<String, List<String>>();
		m_testDataIndex = new HashMap<String, Integer>();

		if (null != ORWorkBook && null != ORSheet)
			PageWebElementDetails.createObjectMap(ORWorkBook, ORSheet, this
					.getClass().getClassLoader()
					.getResourceAsStream(ORWorkBook),
					m_pgSpecificWebElementMap, m_testDataIndex);

	}

	/**
	 * This Method gets the WebElements in a Page by taking locator LogicalName
	 * as the input. If multiple web elements are fetched for the same locator,
	 * warning message is logged and the first web element is returned
	 * 
	 * @param locatorLogicalName
	 * @param commonWebMap
	 * @param isMandatory
	 * @return WebElement
	 * @throws ElementException
	 *             the element exception
	 */
	public WebElement getElementInPage(String locatorLogicalName,
			String executionMode, Map<String, List<String>> commonWebMap,
			boolean isMandatory) throws ElementException {

		Log.debug("Get Element with LOGICAL NAME : " + locatorLogicalName);
		List<WebElement> elementList = null;

		String locatorType = getLocatorType(locatorLogicalName, commonWebMap);
		String locator = getLocator(locatorLogicalName, executionMode,
				commonWebMap);

		// Handle multi locators provided if locatorType is 'multi'
		if (null != locatorType && null != locator
				&& locatorType.equals("multi")) {

			Log.debug("Retrieve all the visible webelements for multilocator:"
					+ locatorLogicalName);
			elementList = getElementsForMultiLocator(locatorLogicalName,
					locator, isMandatory, false, 0);

		} else {

			elementList = getElementsForLogicalName(locatorLogicalName,
					locatorType, locator, isMandatory);
		}

		if (null == elementList || elementList.size() == 0)
			return null;

		return elementList.get(0);
	}

	/**
	 * Returns the web elements found for the locator type and locator passed
	 * corresponding to the logical name.
	 * 
	 * @param locatorLogicalName
	 * @param locatorType
	 * @param locator
	 * @param isMandatory
	 * @return WebElement list
	 * @throws ElementException
	 */
	public List<WebElement> getElementsForLogicalName(
			String locatorLogicalName, String locatorType, String locator,
			boolean isMandatory) throws ElementException {

		List<WebElement> visibleElementList = null;

		List<WebElement> elementList = Element.getElements(locatorLogicalName,
				m_pageDriver, getBy(locatorType, locator), isMandatory);

		if (null == elementList) {
			Log.warn("No Elements were found for " + locatorLogicalName + ":"
					+ locatorType + "->" + locator);
			return null;
		}
		if (elementList.size() > 1) {

			visibleElementList = new ArrayList<WebElement>();
			Log.warn("There are multiple web elements for "
					+ locatorLogicalName);
			Log.warn("Returning the first web element that is displayed ");
			for (int i = 0; i < elementList.size(); i++) {
				if (elementList.get(i).isDisplayed())
					visibleElementList.add(elementList.get(i));
			}
			return visibleElementList;
		}
		return elementList;
	}

	/**
	 * Returns the web elements found for the locator type and locator passed in
	 * case of multi locators.
	 * 
	 * 
	 * @param locatorLogicalName
	 * @param locatorType
	 * @param locator
	 * @param isMandatory
	 * @return WebElement list
	 * @throws ElementException
	 */
	public List<WebElement> getElementsInCaseOfMultiLocator(
			String locatorLogicalName, String locatorType, String locator,
			boolean isMandatory) throws ElementException {

		List<WebElement> visibleElementList = null;

		// code for polling and returning the elements
		List<WebElement> elementList = new WebDriverWait(m_pageDriver, 30)
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getBy(
						locatorType, locator)));

		if (null == elementList) {
			Log.warn("No Elements were found for " + locatorLogicalName + ":"
					+ locatorType + "->" + locator);
			return null;
		}
		if (elementList.size() > 1) {

			visibleElementList = new ArrayList<WebElement>();
			Log.warn("There are multiple web elements for "
					+ locatorLogicalName);
			Log.warn("Returning the first web element that is displayed ");
			for (int i = 0; i < elementList.size(); i++) {
				if (elementList.get(i).isDisplayed())
					visibleElementList.add(elementList.get(i));
			}
			return visibleElementList;
		}
		return elementList;
	}

	/**
	 * This method returns the child elements of a specific parent webelement
	 * passed as parameter
	 * 
	 * @param parentElement
	 * @param locatorLogicalName
	 * @param commonWebMap
	 * @param isMandatory
	 * @return
	 * @throws ElementException
	 */
	public List<WebElement> getChildElementsinPage(WebElement parentElement,
			String locatorLogicalName, String executionMode,
			Map<String, List<String>> commonWebMap, boolean isMandatory)
			throws ElementException {

		Log.debug("Get child elements with LOGICAL NAME : "
				+ locatorLogicalName);

		return Element.getChildElements(
				locatorLogicalName,
				m_pageDriver,
				parentElement,
				getBy(getLocatorType(locatorLogicalName, commonWebMap),
						getLocator(locatorLogicalName, executionMode,
								commonWebMap)));
	}

	/**
	 * This method returns the child element of a specific parent webelement
	 * passed as parameter
	 * 
	 * @param parentElement
	 * @param locatorLogicalName
	 * @param commonWebMap
	 * @param isMandatory
	 * @return
	 * @throws ElementException
	 * @throws IOException
	 * @throws DataSourceException
	 */
	public WebElement getChildElementinPage(WebElement parentElement,
			String locatorLogicalName, String executionMode,
			Map<String, List<String>> commonWebMap, boolean isMandatory)
			throws ElementException {

		Log.debug("Get child element with LOGICAL NAME : " + locatorLogicalName);

		return Element.getChildElement(
				locatorLogicalName,
				m_pageDriver,
				parentElement,
				getBy(getLocatorType(locatorLogicalName, commonWebMap),
						getLocator(locatorLogicalName, executionMode,
								commonWebMap)));
	}

	/**
	 * This Method gets the WebElement in Tabs like Items, Services etc of any
	 * Page by taking locator LogicalName as the input.
	 * 
	 * This method handles two kinds of locators:
	 * 
	 * Case 1) id = xyz0 for element in the first row id = xyz1 for element in
	 * second row id = xyz2 for element in third row . . . . We construct the
	 * locator appending the numerical part by passing index as parameter.
	 * 
	 * Case 2) id = xyz0_abc0 for element in the first row id = xyz1_abc1 for
	 * element in the second row id = xyz2_abc2 for element in the third row . .
	 * . . We have an entry of type xyz<n>_abc<n> in the ods file for these kind
	 * of locators and construct locators by replacing the place holder <n> with
	 * index that is passed as parameter.
	 * 
	 * Format in ods for multi locator:
	 * ------------------------------------------
	 * ----------------------------------------------------------------------
	 * Logical Name | Locator Type | Locator | Element Type
	 * ----------------------
	 * ----------------------------------------------------
	 * ------------------------------------ ion_mfg_xyz | multi | id=>trditem<n>
	 * | name=>itemsales<n> | css=>input[value='item<n>'] |TextField
	 * 
	 * @param locatorLogicalName
	 * @param commonWebMap
	 * @param isMandatory
	 * @return WebElement
	 * @throws ElementException
	 */
	public WebElement getElementInTab(String locatorLogicalName,
			String executionMode, Map<String, List<String>> commonWebMap,
			boolean isMandatory, int index) throws ElementException {

		Log.debug("Get Element with LOGICAL NAME : " + locatorLogicalName);

		WebElement elementInTab = null;
		List<WebElement> elementList = null;
		String locatorType = getLocatorType(locatorLogicalName, commonWebMap);
		String locator = getLocator(locatorLogicalName, executionMode,
				commonWebMap);

		if (null != locatorType && null != locator
				&& locatorType.equals("multi")) {

			elementList = getElementsForMultiLocator(locatorLogicalName,
					locator, isMandatory, true, index);
			if (null == elementList || elementList.size() == 0)
				return null;

			elementInTab = elementList.get(0);
		} else {

			if (null != getLocator(locatorLogicalName, executionMode,
					commonWebMap)
					&& getLocator(locatorLogicalName, executionMode,
							commonWebMap).contains("<n>")) {

				locator = locator.replace("<n>", String.valueOf(index));
				locator = locator.replace(" ", "");
				return Element.getElement(
						locatorLogicalName,
						m_pageDriver,
						getBy(getLocatorType(locatorLogicalName, commonWebMap),
								locator), isMandatory);
			}

			return Element.getElement(
					locatorLogicalName,
					m_pageDriver,
					getBy(getLocatorType(locatorLogicalName, commonWebMap),
							getLocator(locatorLogicalName, executionMode,
									commonWebMap) + index), isMandatory);
		}

		return elementInTab;
	}

	/**
	 * This Method gets the list of WebElements in a page by taking locator
	 * logicalName as the input.
	 * 
	 * @param locatorLogicalName
	 * @param isMandatory
	 * @param commonWebMap
	 * @return List<WebElement>
	 * @throws ElementException
	 */
	public List<WebElement> getElementsInPage(String locatorLogicalName,
			String executionMode, Map<String, List<String>> commonWebMap,
			boolean isMandatory) throws ElementException {

		Log.debug("Get list of Elements with LOGICAL NAME : "
				+ locatorLogicalName);

		List<WebElement> elementList = null;

		String locatorType = getLocatorType(locatorLogicalName, commonWebMap);
		String locator = getLocator(locatorLogicalName, executionMode,
				commonWebMap);

		// Handle multi locators provided if locatorType is 'multi'
		if (null != locatorType && null != locator
				&& locatorType.equals("multi")) {

			// retrieve the visible list of web elements for the multi locator
			// given
			elementList = getElementsForMultiLocator(locatorLogicalName,
					locator, isMandatory, false, 0);

		} else {

			elementList = getElementsForLogicalName(locatorLogicalName,
					locatorType, locator, isMandatory);
		}

		if (null == elementList || elementList.size() == 0)
			return null;

		return elementList;
	}

	/**
	 * Returns the list of web elements found for the input multi-locator given
	 * in the ods file. Locators in a multi-locator has to be separated by "|".
	 * 
	 * Format in ods for multi-locator:
	 * 
	 * ------------------------------------------------------------------------
	 * -------------------------------------------------- Logical Name | Locator
	 * Type | Locator | ElementType
	 * ----------------------------------------------
	 * ----------------------------
	 * ------------------------------------------------ ion_mfg_xyz | multi |
	 * id=>trditem|name=>itemsales|css=>input[value='item'] | TextField
	 * 
	 * 
	 * @param locatorLogicalName
	 * @param locator
	 * @param isMandatorye
	 * @return List<WebElement>
	 * @throws ElementException
	 */
	public List<WebElement> getElementsForMultiLocator(
			String locatorLogicalName, String locator, boolean isMandatory,
			boolean isElementInTab, int index) throws ElementException {

		List<WebElement> elementList = null;
		int timeCounter = 0;

		if (!locator.contains("|"))
			throw new ElementException(locatorLogicalName
					+ "--> Provided multi locator" + locator
					+ " is not valid.Please separate multi locators with '|'");

		String[] multiLocators = locator.split("\\|");
		Log.debug("Multi locators:" + multiLocators);

		// Iterate till the maximum time out
		while (timeCounter < FrameworkConstants.EXPLICIT_TIMEOUT_SEC) {

			// Iterate through all the available locators in every cycle
			for (int j = 0; j < multiLocators.length; j++) {

				// Check for valid web element for every locator, if found
				// return it.
				if (multiLocators[j].contains("=>")) {

					String[] locators = multiLocators[j].split("=>");

					if (locators.length == 2) {

						Log.debug("Multi Locator: \t" + (j + 1)
								+ "Locator type:" + locators[0]
								+ "--- Locator:" + locators[1]);

						if (isElementInTab) {

							// element is in tab and index has to be added to
							// locator
							if (locators[1].contains("<n>"))
								locators[1].replace("<n>",
										String.valueOf(index));
							else
								locators[1] = locators[1] + index;
							try {
								// polling for 15 seconds for the element to be
								// present
								elementList = new WebDriverWait(m_pageDriver, 5)
										.until(ExpectedConditions
												.presenceOfAllElementsLocatedBy(getBy(
														locators[0],
														locators[1])));

							} catch (TimeoutException e) {

								timeCounter = timeCounter + 5;
								Log.debug("Element not found:"
										+ "Locator type:" + locators[0]
										+ "--- Locator:" + locators[1]);
							}

						} else {
							try {
								// polling for 15 seconds for the element to be
								// present
								elementList = new WebDriverWait(m_pageDriver, 5)
										.until(ExpectedConditions
												.presenceOfAllElementsLocatedBy(getBy(
														locators[0],
														locators[1])));

							} catch (TimeoutException e) {
								timeCounter = timeCounter + 5;
								Log.debug("Element not found:"
										+ "Locator type:" + locators[0]
										+ "--- Locator:" + locators[1]);
							}

						}
					} else
						Log.warn("Invalid multi locator provided for:"
								+ locatorLogicalName + "-->" + multiLocators[j]);

					if (null != elementList && !elementList.isEmpty())
						return elementList;

				} else {
					Log.warn("Invalid multi locator provided for:"
							+ locatorLogicalName + "-->" + multiLocators[j]);
				}

				if (j == multiLocators.length - 1 && isMandatory) {
					Log.error(locatorLogicalName
							+ "--> WebElement not found - Exception");
					throw new ElementException(locatorLogicalName
							+ "--> WebElement does not exist");
				}
			}

		}
		return elementList;
	}

	/**
	 * This Method returns the title of the Page for respective WebElement being
	 * passed.
	 * 
	 * @param element
	 * @return
	 * @throws ElementException
	 */
	public String getPageTitle(WebElement element) throws ElementException {
		return Element.getText("Page title", element);
	}

	/**
	 * This method returns locator type based on the type passed as parameter
	 * with the respective locator.
	 * 
	 * @param type
	 *            locator type
	 * @param locator
	 *            actual locator
	 * @return locator By
	 */
	public By getBy(String type, String locator) {

		Log.debug("LOCATOR: " + locator + "  TYPE: " + type);
		By by = null;

		if (type != null && locator != null) {

			String locatorType = type.toUpperCase();
			switch (LocatorType.valueOf(locatorType)) {
			case ID:
				by = By.id(locator);
				break;
			case CSS:
				by = By.cssSelector(locator);
				break;
			case CLASSNAME:
				by = By.className(locator);
				break;
			case NAME:
				by = By.name(locator);
				break;
			case XPATH:
				by = By.xpath(locator);
				break;
			case LINKTEXT:
				by = By.linkText(locator);
				break;
			case PARTIALLINKTEXT:
				by = By.partialLinkText(locator);
				break;
			case TAGNAME:
				by = By.tagName(locator);
				break;
			default:
				by = null;
			}

		} else {
			Log.debug("Locator:" + locator + "or Type:" + type
					+ "should not be null");
		}
		return by;
	}

	/**
	 * Returns the index of the Test Data in value of m_webElementMap
	 * 
	 * @param customer
	 * @param testCaseID
	 * @return
	 */
	private int getIndexOf(String customer, String testCaseID) {

		int indexOf = -1;

		if (m_testDataIndex.containsKey(customer + ":" + testCaseID))
			indexOf = m_testDataIndex.get(customer + ":" + testCaseID) - 1;
		else
			Log.warn("Could not locate the Test Data for customer " + customer
					+ " Test case ID " + testCaseID);

		return indexOf;

	}

	/**
	 * This method returns the type of the locator from the Web Element with the
	 * respective LogicalName as the input.
	 * 
	 * @param logicalname
	 * @return locator type
	 */
	public String getLocatorType(String logicalname,
			Map<String, List<String>> commonWebMap) {

		String locatorType = null;

		// Check if the Logical Name is defined in the Page Specific Web Object
		// Map file
		if (m_pgSpecificWebElementMap.containsKey(logicalname)) {
			if (logicalname.contains("_common_")) {
				if (null != commonWebMap) {
					if (commonWebMap.containsKey(logicalname)) {
						locatorType = commonWebMap.get(logicalname).get(
								PageWebElementDetails.LOCATOR_TYPE_COL_NO - 1);
					} else {
						Log.warn(logicalname
								+ " is not found in the Common object repository");
					}
				} else
					Log.error("Common WebElement Map is null");
			} else
				locatorType = m_pgSpecificWebElementMap.get(logicalname).get(
						PageWebElementDetails.LOCATOR_TYPE_COL_NO - 1);
		} else
			Log.warn(logicalname
					+ " is not found in the Page Specific object repository");

		return locatorType;
	}

	/**
	 * This method returns the actual value of the locator from the WebElement
	 * with the respective LogicalName as the input.
	 * 
	 * @param logicalname
	 * @return locator value
	 */
	public String getLocator(String logicalname, String executionMode,
			Map<String, List<String>> commonWebMap) {

		String locator = null;

		/**
		 * Check if the Logical Name is defined in the Page Specific Web Object
		 * Map File
		 */
		if (m_pgSpecificWebElementMap.containsKey(logicalname)) {
			if (logicalname.contains("_common_")) {
				if (null != commonWebMap) {
					if (commonWebMap.containsKey(logicalname)) {

						if (executionMode
								.equalsIgnoreCase(ExecutionMode.WEB_BROWSER
										.toString()))
							locator = commonWebMap
									.get(logicalname)
									.get(PageWebElementDetails.LOCATOR_WEB_BROWSER_COL_NO - 1);
						else if (executionMode
								.equalsIgnoreCase(ExecutionMode.MOBILE_BROWSER
										.toString()))
							locator = commonWebMap
									.get(logicalname)
									.get(PageWebElementDetails.LOCATOR_MOBILE_BROWSER_COL_NO - 1);
						else if (executionMode
								.equalsIgnoreCase(ExecutionMode.ANDROID_APP
										.toString()))
							locator = commonWebMap
									.get(logicalname)
									.get(PageWebElementDetails.LOCATOR_ANDROID_NATIVE_COL_NO - 1);
					} else {
						Log.warn(logicalname
								+ " is not found in the Common object repository");
					}
				} else
					Log.error("Common WebElement Map is null");
			} else {

				if (executionMode.equalsIgnoreCase(ExecutionMode.WEB_BROWSER
						.toString()))
					locator = m_pgSpecificWebElementMap
							.get(logicalname)
							.get(PageWebElementDetails.LOCATOR_WEB_BROWSER_COL_NO - 1);
				else if (executionMode
						.equalsIgnoreCase(ExecutionMode.MOBILE_BROWSER
								.toString()))
					locator = m_pgSpecificWebElementMap
							.get(logicalname)
							.get(PageWebElementDetails.LOCATOR_MOBILE_BROWSER_COL_NO - 1);
				else if (executionMode
						.equalsIgnoreCase(ExecutionMode.ANDROID_APP.toString()))
					locator = m_pgSpecificWebElementMap
							.get(logicalname)
							.get(PageWebElementDetails.LOCATOR_ANDROID_NATIVE_COL_NO - 1);
			}
		} else
			Log.warn(logicalname
					+ " is not found in the Page Specific object repository");

		return locator;
	}

	/**
	 * Returns the test data corresponding to logical name of a customer and
	 * specific test case id
	 * 
	 * @param logicalName
	 * @param customer
	 * @param testCaseID
	 * @return
	 */
	public String getTestData(String logicalName, String customer,
			String testCaseID) {

		Log.debug("Get Test Data for : " + logicalName + " : " + customer
				+ " : " + testCaseID);
		String testData = null;

		try {
			testData = m_pgSpecificWebElementMap.get(logicalName).get(
					getIndexOf(customer, testCaseID));
		} catch (NullPointerException e) {
			Log.debug("No Data found for " + logicalName + " : " + customer
					+ " : " + testCaseID + " specified");
		}

		return testData;

	}

	/**
	 * Returns the test data corresponding to logical name of a customer and
	 * specific test case id
	 * 
	 * @param logicalName
	 * @param customer
	 * @param testCaseID
	 * @return
	 */
	public List<String> getTestDataSet(String logicalName, String customer) {

		Log.debug("Get Test Data for : " + logicalName + " : " + customer);
		List<String> testDataList = new ArrayList<String>();

		try {
			int testDataSize = m_pgSpecificWebElementMap.get(logicalName)
					.size();

			for (int i = PageWebElementDetails.TESTDATA_START_COL_NO - 1; i < testDataSize; i++) {

				testDataList.add(m_pgSpecificWebElementMap.get(logicalName)
						.get(i));
			}

		} catch (NullPointerException e) {
			Log.debug("No Data found for " + logicalName + " : " + customer);
		}

		return testDataList;

	}

	/**
	 * Check if there is an entry in the web element map for the customer and
	 * the test case ID mentioned
	 * 
	 * @param customer
	 * @param testCaseID
	 * @return
	 */
	public boolean isTestDataExists(String customer, String testCaseID) {

		if (getIndexOf(customer, testCaseID) < 0)
			return false;

		return true;

	}

	public WebDriver getWebDriver() {
		return m_pageDriver;
	}

	/**
	 * This method is used to check for the Existence of the Element.
	 * 
	 * @param logicalName
	 * @return boolean
	 * @throws ElementException
	 */
	public boolean isElementExistsInPage(String logicalName,
			String executionMode) throws ElementException {

		Log.debug("is Element Exists");

		if (!CommonHelper.doesNotExist(
				logicalName,
				m_pageDriver,
				getBy(getLocatorType(logicalName, m_pgSpecificWebElementMap),
						getLocator(logicalName, executionMode,
								m_pgSpecificWebElementMap))))
			return true;

		return false;
	}

}