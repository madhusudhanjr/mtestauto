package com.itc.utilities.elementfactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.itc.framework.loggers.Log;
import com.itc.framework.reporters.Resulter;
import com.itc.utilities.exceptions.DataSourceException;
import com.itc.utilities.exceptions.ElementException;
import com.itc.utilities.helpers.CommonHelper;

/**
 * Element class is used to perform actions related to Element.
 * 
 * @author Madhusudhan
 */
public class Element {

	/**
	 * This Method is used to get Element.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param objectLocator
	 * @return
	 * @throws ElementException
	 */
	public static WebElement getElement(String strLogicalName,
			WebDriver driver, By objectLocator, boolean isMandatory)
			throws ElementException {

		WebElement element = null;

		try {
			if (!CommonHelper.isObjectLocatorNull(objectLocator)) {
				objectLocator.toString();

				if (CommonHelper.exists(strLogicalName, driver, objectLocator)) {
					element = driver.findElement(objectLocator);
				} else {
					if (isMandatory) {
						Log.error("\"" + strLogicalName
								+ "\" WebElement not found");
						throw new ElementException(strLogicalName
								+ "--> WebElement does not exist");
					} else {
						Log.error("\"" + strLogicalName
								+ "\" WebElement not found");
					}
				}

			} else {

				if (isMandatory)
					Log.error("\"" + strLogicalName
							+ "\" ObjectLocator is Null");
				else
					Log.warn("\"" + strLogicalName + "\" ObjectLocator is Null");
			}
		} catch (Exception e) {
			Log.error("\"" + strLogicalName
					+ "\" WebElement not found - Exception");
			throw new ElementException(strLogicalName
					+ "--> WebElement does not exist");
		}

		return element;
	}

	/**
	 * This Method is used to get List of Elements.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param objectLocator
	 * @return
	 * @throws ElementException
	 */
	public static List<WebElement> getElements(String strLogicalName,
			WebDriver driver, By objectLocator, boolean isMandatory)
			throws ElementException {

		List<WebElement> elements = null;

		try {
			if (!CommonHelper.isObjectLocatorNull(objectLocator)) {
				objectLocator.toString();

				if (CommonHelper.exists(strLogicalName, driver, objectLocator)) {
					elements = driver.findElements(objectLocator);
				} else {
					if (isMandatory) {
						Log.error("\"" + strLogicalName
								+ "\" WebElement not found - Exception");
						throw new ElementException(strLogicalName
								+ "--> WebElement does not exist");
					} else {
						Log.warn("\"" + strLogicalName
								+ "\" WebElement not found");
					}
				}

			} else {
				if (isMandatory) {
					Log.error("\"" + strLogicalName
							+ "\" ObjectLocator is Null");
					throw new ElementException(strLogicalName
							+ "--> Element ObjectLocator is Null");
				} else {
					Log.warn("\"" + strLogicalName + "\" ObjectLocator is Null");
				}
			}
		} catch (Exception e) {
			Log.error("\"" + strLogicalName
					+ "\" WebElement not found - Exception");
			throw new ElementException(strLogicalName
					+ "--> WebElement does not exist");
		}

		return elements;
	}

	/**
	 * This Method is used to get child Element.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param parent
	 * @param objectLocator
	 * @return
	 * @throws ElementException
	 * @throws DataSourceException
	 * @throws IOException
	 */
	public static WebElement getChildElement(String strLogicalName,
			WebDriver driver, WebElement parent, By objectLocator)
			throws ElementException {
		List<String> arrResult = null;
		WebElement element = null;
		objectLocator.toString();
		try {
			if (null != parent) {
				List<WebElement> elements = parent.findElements(objectLocator);
				if (elements.size() != 0) {
					element = elements.get(0);
					arrResult = Arrays.asList("\"" + strLogicalName + "\"",
							"WebElement exists", "0");
				} else {
					arrResult = Arrays.asList("\"" + strLogicalName + "\"",
							" WebElement not found", "1");
					// throw new Exception(strLogicalName
					// + "--> WebElement not found");
				}
			} else {
				arrResult = Arrays.asList("\"" + strLogicalName + "\"",
						" Parent Element is NULL", "1");
				arrResult = Arrays.asList("\"" + strLogicalName + "\"",
						" WebElement not found", "1");
				// throw new Exception(strLogicalName +
				// "--> WebElement not found");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					" WebElement not found", "1");
			throw new ElementException(strLogicalName
					+ "--> WebElement not found;", e);
		}
		Resulter.log(arrResult);
		return element;
	}

	/**
	 * This Method is used to get child Elements.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param parent
	 * @param objectLocator
	 * @return List<WebElement>
	 * @throws ElementException
	 */
	public static List<WebElement> getChildElements(String strLogicalName,
			WebDriver driver, WebElement parent, By objectLocator)
			throws ElementException {
		List<String> arrResult = null;
		objectLocator.toString();
		List<WebElement> elementsList = null;
		if (parent != null) {
			try {
				elementsList = parent.findElements(objectLocator);
				if (elementsList.size() != 0) {
					arrResult = Arrays
							.asList("\"" + strLogicalName + "\"",
									" No. of Child WebElements: "
											+ elementsList.size(), "0");
				} else {
					arrResult = Arrays.asList("\"" + strLogicalName + "\"",
							"WebElement not found ", "1");
					// throw new Exception(strLogicalName
					// + "--> WebElement not found");
				}
			} catch (Exception e) {
				// e.printStackTrace();
				arrResult = Arrays.asList("\"" + strLogicalName + "\"",
						" WebElement not found", "1");
				throw new ElementException(strLogicalName
						+ "--> WebElement not found");

			}
		} else {
			// arrResult = Arrays
			// .asList("\""+strLogicalName+"\"", " Parent Element is NULL",
			// "1");
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					" WebElement not found", "1");
			// throw new Exception(strLogicalName + "--> WebElement not found");
		}
		Resulter.log(arrResult);
		return elementsList;
	}

	/**
	 * This Method is used to check for Element displayed.
	 * 
	 * @param strLogicalName
	 * @param element
	 * @return boolean
	 */
	public static boolean isDisplayed(String strLogicalName, WebElement element) {
		boolean blResult = false;

		try {
			if (element != null) {
				if (element.isDisplayed()) {
					blResult = true;
					Log.debug(strLogicalName + " Element exists");
				} else {
					Log.warn(strLogicalName + " Element is not displayed");
				}
			} else {
				Log.warn(strLogicalName + " Element does not exist");
			}
		} catch (Exception e) {

			Log.error("\"" + strLogicalName + "\"" + " WebElement not found");
		}
		return blResult;
	}

	/**
	 * This Method is used to check for Element enabled.
	 * 
	 * @param strLogicalName
	 * @param element
	 * @return boolean
	 * @throws ElementException
	 */
	public static boolean isEnabled(String strLogicalName, WebElement element)
			throws ElementException {
		boolean blResult = false;
		try {
			if (element != null) {
				if (element.isEnabled()) {
					blResult = true;
				} else {
					blResult = false;
					Log.warn(strLogicalName + " Element is not enabled");
				}
			} else {
				blResult = false;
				Log.warn(strLogicalName + " Element is does not exist");
			}
		} catch (Exception e) {
			Log.error("\"" + strLogicalName + "\"" + " WebElement not found");
			throw new ElementException(strLogicalName
					+ "--> WebElement not found");
		}
		return blResult;
	}

	/**
	 * This Method is used to check for Element enabled.
	 * 
	 * @param strLogicalName
	 * @param element
	 * @return boolean
	 * @throws ElementException
	 */
	public static boolean isNotEditable(String strLogicalName,
			WebElement element) throws ElementException {
		boolean blResult = false;
		String isEditable = null;
		try {
			if (element != null) {
				if (element.isEnabled()) {

					isEditable = element.getAttribute("readonly");

					if (null != isEditable && isEditable.equalsIgnoreCase("true")) {
						blResult = true;
					} else {
						blResult = false;
						Log.error(strLogicalName + " Element is editable");
					}

					// arrResult = Arrays.asList(strLogicalName,
					// " Element exists", "0");
				}
			} else {
				blResult = false;
				Log.error(strLogicalName + " Element is does not exist");
				// throw new Exception(strLogicalName +
				// "--> WebElement not found");
			}
		} catch (Exception e) {
			Log.error("\"" + strLogicalName + "\"" + " WebElement not found");
			throw new ElementException(strLogicalName
					+ "--> WebElement not found");
		}
		return blResult;
	} 

	/**
	 * This Method is used to check for Element is ReadOnly.
	 * 
	 * @param strLogicalName
	 * @param element
	 * @return boolean
	 * @throws ElementException
	 */
	public static boolean isReadOnly(String strLogicalName, WebElement element)
			throws ElementException {
		boolean blResult = false;
		try {
			if (element != null) {
				if (null != element.getAttribute("readonly")
						&& element.getAttribute("readonly").equalsIgnoreCase(
								"readonly")) {
					blResult = true;
					// arrResult = Arrays.asList(strLogicalName,
					// " Element exists", "0");
				} else if (null != element.getAttribute("disabled")
						&& element.getAttribute("disabled").equalsIgnoreCase(
								"true")) {
					blResult = true;
				} else {
					blResult = false;
					Log.warn(strLogicalName + " Element is not readonly");
				}
			} else {
				blResult = false;
				Log.error(strLogicalName + " Element is does not exist");
				// throw new Exception(strLogicalName +
				// "--> WebElement not found");
			}
		} catch (Exception e) {
			Log.error("\"" + strLogicalName + "\"" + " WebElement not found");
			throw new ElementException(strLogicalName
					+ "--> WebElement not found");
		}
		return blResult;
	}

	/**
	 * This Method is used to check for Element displayed.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param by
	 * @return boolean
	 * @throws ElementException
	 */
	public static boolean isDisplayed(String strLogicalName, WebDriver driver,
			By by) throws ElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			List<WebElement> elementList = driver.findElements(by);
			if (!elementList.isEmpty()) {
				WebElement element = elementList.get(0);
				if (element.isDisplayed()) {
					blResult = true;
					// arrResult = Arrays.asList(strLogicalName,
					// " Element is Displayed", "0");
				} else {
					blResult = false;
					arrResult = Arrays.asList(strLogicalName,
							" Element is not displayed", "1");
				}
			} else {
				blResult = false;
				arrResult = Arrays.asList(strLogicalName,
						" Element is does not exist", "1");
				// throw new Exception(strLogicalName +
				// "--> WebElement not found");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					" WebElement not found", "1");
			throw new ElementException(strLogicalName
					+ "--> WebElement not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to check for Element is Enabled.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param by
	 * @return
	 * @throws ElementException
	 */
	public static boolean isEnabled(String strLogicalName, WebDriver driver,
			By by) throws ElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			List<WebElement> elementList = driver.findElements(by);
			if (!elementList.isEmpty()) {
				WebElement element = elementList.get(0);
				if (isEnabled(strLogicalName, element)) {
					blResult = true;
					// arrResult = Arrays.asList(strLogicalName,
					// " Element is enabled", "0");
				}
				// else{
				// blResult=false;
				// arrResult=Arrays.asList(strLogicalName," Element is not enabled",
				// "1");
				// }
			} else {
				blResult = false;
				arrResult = Arrays.asList(strLogicalName,
						" Element is does not exist", "1");
				// throw new Exception(strLogicalName +
				// "--> WebElement not found");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					" WebElement not found", "1");
			throw new ElementException(strLogicalName
					+ "--> WebElement not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to get text of Element.
	 * 
	 * @param strLogicalName
	 * @param element
	 * @return String
	 * @throws ElementException
	 */
	public static String getText(String strLogicalName, WebElement element)
			throws ElementException {
		String returnText = null;
		try {
			if (element != null) {
				if (isDisplayed(strLogicalName, element)) {
					if (isEnabled(strLogicalName, element)) {
						if (null != element.getText()
								&& !element.getText().isEmpty()) {
							returnText = element.getText();
						} else if (null != element.getAttribute("value")
								&& !element.getAttribute("value").isEmpty()) {
							returnText = element.getAttribute("value");
						} else if (null != element.getAttribute("name")
								&& !element.getAttribute("title").isEmpty()) {
							returnText = element.getAttribute("title");
						} else {
							Log.error("\"" + strLogicalName + "\""
									+ "Text Field :Available data \" null");
						}
						Log.debug("\"" + strLogicalName + "\""
								+ "Text Field :Available data \" " + returnText
								+ "\"");
					} else if (isReadOnly(strLogicalName, element)) {
						if (null != element.getText()
								&& !element.getText().isEmpty()) {
							returnText = element.getText();
						} else if (null != element.getAttribute("value")
								&& !element.getAttribute("value").isEmpty()) {
							returnText = element.getAttribute("value");
						} else if (null != element.getAttribute("title")
								&& !element.getAttribute("title").isEmpty()) {
							returnText = element.getAttribute("title");
						} else {
							
							Log.error("\"" + strLogicalName + "\""
									+ "Text Field :Available data \" null");
						}
					} else if (!isEnabled(strLogicalName, element)) {

						if (null != element.getText()
								&& !element.getText().isEmpty()) {
							returnText = element.getText();
						} else if (null != element.getAttribute("value")
								&& !element.getAttribute("value").isEmpty()) {
							returnText = element.getAttribute("value");
						} else if (null != element.getAttribute("title")
								&& !element.getAttribute("title").isEmpty()) {
							returnText = element.getAttribute("title");
						} else {
							
							Log.error("\"" + strLogicalName + "\""
									+ "Text Field :Available data \" null");
						}
					
						Log.debug("\"" + strLogicalName + "\""
								+ " textField is not enabled"+ returnText
								+ "\"");
					}
				} else {
					Log.error("\"" + strLogicalName + "\""
							+ " textField does not exist");
				}
			} else {
				Log.error("\"" + strLogicalName + "\""
						+ " textField does not exist");
			}
		} catch (Exception e) {
			Log.error("\"" + strLogicalName + "\""
					+ " textField does not exist");
			throw new ElementException(strLogicalName
					+ "--> WebElement not found");
		}
		return returnText;
	}
	
	/**
	 * This Method is used to make the TextField editable by JavaScript
	 * 
	 * @param strLogicalName
	 * @param element
	 * @return
	 * @return boolean
	 */
	public static boolean setEditableTrueByJS(String strLogicalName,
			WebElement element, WebDriver driver) {

		Log.debug("Set Element " + strLogicalName + " True by Javascript");

		String readonlyAttr = element.getAttribute("readonly");

		if (null != readonlyAttr
				&& (readonlyAttr.equalsIgnoreCase("true") || readonlyAttr
						.equalsIgnoreCase("readonly"))) {
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].setAttribute('readonly', 'false')", element);

			return true;

		} else
			return false;

	}

	/**
	 * This Method is used to set the value to a TextField by JavaScript.
	 * 
	 * @param strLogicalName
	 * @param element
	 * @return
	 * @return boolean
	 */
	public static void setValueByJS(String strLogicalName, WebElement element,
			WebDriver driver, String value) {

		Log.debug("Set Element value " + strLogicalName + " by Javascript");

		((JavascriptExecutor) driver).executeScript(
				"arguments[0].setAttribute('value', '" + value + "')", element);
	}

	
	/**
	 * This method is used to wait for the element to be displayed with the
	 * WebDriver Wait
	 * 
	 * @param element
	 * @return boolean
	 */
	public static boolean waitForElementVisible(WebDriver driver, WebElement element) {

		Log.debug("Wait for Element to be present with WebDriverWait");

		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 30)
				.ignoring(StaleElementReferenceException.class);
		WebElement webElement = wait.until(ExpectedConditions
				.visibilityOf(element));

		return webElement.isDisplayed();

	}
}