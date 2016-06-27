package com.itc.utilities.elementfactory;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;

import com.itc.framework.loggers.Log;
import com.itc.framework.reporters.Resulter;
import com.itc.utilities.exceptions.ButtonElementException;
import com.itc.utilities.helpers.CommonHelper;

/**
 * ButtonElement Class consists of methods related to Button actions.
 * 
 * @author Madhusudhan
 */
public class ButtonElement {

	/**
	 * This method is used for click action
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param objectLocator
	 * @return boolean
	 * @throws ButtonElementException
	 */
	public static boolean click(String strLogicalName, WebDriver driver,
			By objectLocator) throws ButtonElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		WebElement element = null;
		objectLocator.toString();
		try {
			List<WebElement> elements = driver.findElements(objectLocator);
			if (elements.size() != 0) {
				element = elements.get(0);
				if (isDisplayed(strLogicalName, element)) {
					if (isEnabled(strLogicalName, element)) {
						// try {
						element.click();
						blResult = true;
						// arrResult = Arrays.asList("\""+strLogicalName+"\"",
						// " button has been clicked", "0");
						// } catch (Exception e) {
						// arrResult = Arrays.asList(strLogicalName,
						// "Unable to click \"" + strLogicalName
						// + "\" button", "1");
						// }

					}
					// else {
					// arrResult = Arrays.asList(strLogicalName,
					// "Unable to click \"" + strLogicalName
					// + "\" button as it is not displayed", "1");
					//
					// }
				}
				// else {
				// arrResult = Arrays.asList(strLogicalName,
				// "Unable to click \""
				// + strLogicalName + "\" button as it is not displayed",
				// "1");
				//
				// }
			} else {
				arrResult = Arrays.asList(strLogicalName, "Unable to click \""
						+ strLogicalName + "\" button as not found", "1");
				// throw new Exception(strLogicalName + "--> Button not found");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName,
					"\"Button not found", "1");
			throw new ButtonElementException(strLogicalName
					+ "--> Button not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used for click action.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param element
	 * @return boolean
	 * @throws ButtonElementException
	 */
	public static boolean click(String strLogicalName, WebDriver driver,
			WebElement element) throws ButtonElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (null != element) {
				if (isDisplayed(strLogicalName, element)) {
					if (isEnabled(strLogicalName, element)) {
						element.click();
						blResult = true;
					}

				}

			} else {
				arrResult = Arrays.asList(strLogicalName, "Unable to click \""
						+ strLogicalName + "\" button as element is NULL", "1");

			}
		} catch (Exception e) {

			try {

				CommonHelper.waitInSeconds(5);
				CommonHelper.clickbyJS(driver, element);
				Log.debug("clicked by JS");
				blResult = true;

			} catch (Exception ex) {

				arrResult = Arrays.asList("\"" + strLogicalName,
						"\"Button not found-->"+ex);
				throw new ButtonElementException(strLogicalName
						+ "--> Button not found -->"+ex);

			}

		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used for click action.
	 * 
	 * @param strLogicalName
	 * @param oButton
	 * @return boolean(execution status)
	 * @throws ButtonElementException
	 */
	public static boolean click(String strLogicalName, WebElement oButton)
			throws ButtonElementException {
		boolean blResult = false;
		try {
			if (isDisplayed(strLogicalName, oButton)) {
				if (isEnabled(strLogicalName, oButton)) {
					oButton.click();
					blResult = true;
					Log.debug("\"" + strLogicalName
							+ "\" button has been clicked");
				}
				else
					Log.error("Was unable to click the locator " + strLogicalName +" as it is not enabled");
			}
			else
				Log.error("Was unable to click the locator " + strLogicalName +" as it is not displayed");
		} catch (Exception e) {
			Log.error("\"" + strLogicalName + "\" Button not found -->"+e);
			throw new ButtonElementException(strLogicalName
					+ "--> Button not found --> "+e);
		}
		return blResult;
	}

	/**
	 * This Method is used for double click action.
	 * 
	 * @param strLogicalName
	 * @param button
	 * @param driver
	 * @return
	 * @throws ButtonElementException
	 */
	/*
	 * works only for firefoxDriver and InternetExplorer Driver
	 */
	public static boolean doubleClick(String strLogicalName, WebElement button,
			WebDriver driver) throws ButtonElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (isDisplayed(strLogicalName, button)) {
				if (isEnabled(strLogicalName, button)) {
					Actions ac = new Actions(driver);
					if (driver instanceof InternetExplorerDriver
							|| driver instanceof FirefoxDriver) {
						ac.doubleClick(button);
						blResult = true;
						// arrResult=Arrays.asList("\""+strLogicalName,"\"button has been clicked",
						// "0");
					} else {
						arrResult = Arrays.asList("\"" + strLogicalName,
								"\"Button can not be clicked", "1");
						// throw error message for chrome and safari drivers
					}
				}
				// else{
				// arrResult=Arrays.asList("\""+strLogicalName,"\"button is not enabled",
				// "1");
				// }
			}
			// else {
			// arrResult=Arrays.asList("\""+strLogicalName,"\"button is not displayed",
			// "1");
			// }
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName,
					"\"Button not found", "1");
			throw new ButtonElementException(strLogicalName
					+ "--> Button not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to check for element is disabled
	 * 
	 * @param strLogicalName
	 * @param oButton
	 * @return Boolean
	 * @throws ButtonElementException
	 */
	public static boolean isDisabled(String strLogicalName, WebElement oButton)
			throws ButtonElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (oButton != null) {
				if (!isEnabled(strLogicalName, oButton)) {
					blResult = true;
					// arrResult=Arrays.asList("\""+strLogicalName,"\"button is disabled",
					// "0");
				} else {
					arrResult = Arrays.asList("\"" + strLogicalName,
							"\"Button not disabled", "1");
				}
			} else {
				arrResult = Arrays.asList("\"" + strLogicalName,
						"\"Button element is NULL", "1");
				// throw new Exception(strLogicalName
				// + "--> Button element is NULL");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName,
					"\"Button not found", "1");
			throw new ButtonElementException(strLogicalName
					+ "--> Button not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to check for element is displayed.
	 * 
	 * @param strLogicalName
	 * @param button
	 * @return
	 * @throws Exception
	 */
	public static boolean isDisplayed(String strLogicalName, WebElement button)
			throws Exception {
		
		boolean blResult = false;
		
		try {
			if (button != null) {
				if (button.isDisplayed()) {
					blResult = true;
				} else {
					blResult = false;
					Log.debug("\"" + strLogicalName + "\"Button not displayed");
				}
			} else {
				blResult = false;
				Log.debug("\"" + strLogicalName + "\"Button element is NULL");
			}
		} catch (Exception e) {
			Log.debug("\"" + strLogicalName + "\"Button not found");
			throw new ButtonElementException(strLogicalName
					+ "--> Button not found");
		}
		
		return blResult;
	}

	/**
	 * This Method is used to check for element is Enabled.
	 * 
	 * @param strLogicalName
	 * @param oButton
	 * @return
	 * @throws ButtonElementException
	 */
	public static boolean isEnabled(String strLogicalName, WebElement oButton)
			throws ButtonElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (oButton != null) {
				if (oButton.isEnabled()) {
					blResult = true;
					// arrResult=Arrays.asList("\""+strLogicalName,"\" button is enabled",
					// "0");
				} else {
					blResult = false;
					arrResult = Arrays.asList("\"" + strLogicalName,
							"\"Button not enabled", "1");
				}
			} else {
				blResult = false;
				arrResult = Arrays.asList("\"" + strLogicalName,
						"\"Button element is NULL", "1");
				// throw new Exception(strLogicalName
				// + "--> Button element is NULL");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName,
					"\"Button not found", "1");
			throw new ButtonElementException(strLogicalName
					+ "--> Button not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used check for text field.
	 * 
	 * @param strLogicalName
	 * @param textField
	 * @return boolean
	 * @throws ButtonElementException
	 */
	public static boolean isTextField(String strLogicalName,
			WebElement textField) throws ButtonElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (textField != null) {
				if (textField.getTagName().equals("button")) {
					blResult = true;
					// arrResult = Arrays.asList("\""+strLogicalName+"\"",
					// " is a textField ", "0");
				} else {
					arrResult = Arrays.asList("\"" + strLogicalName + "\"",
							" is not a text field", "1");
				}
			} else {
				arrResult = Arrays.asList("\"" + strLogicalName + "\"",
						" does not exist", "1");
				// throw new Exception(strLogicalName + "--> Button not found");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName,
					"\"Button not found", "1");
			throw new ButtonElementException(strLogicalName
					+ "--> Button not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to submit element which is in the form.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param objectLocator
	 * @return boolean
	 * @throws ButtonElementException
	 */
	public static boolean submit(String strLogicalName, WebDriver driver,
			By objectLocator) throws ButtonElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		WebElement element = null;
		objectLocator.toString();
		try {
			List<WebElement> elements = driver.findElements(objectLocator);
			if (elements.size() != 0) {
				element = elements.get(0);
				if (isDisplayed(strLogicalName, element)) {
					if (isEnabled(strLogicalName, element)) {
						// try {
						element.submit();
						blResult = true;
						// arrResult = Arrays.asList("\""+strLogicalName,
						// " button has been clicked", "0");
						// } catch (Exception e) {
						// arrResult = Arrays.asList("Unable to submit \"" +
						// strLogicalName
						// + "\" button", "1");
						// }
					}
					// else {
					// arrResult = Arrays.asList("Unable to submit \"" +
					// strLogicalName
					// + "\" button as it is not displayed", "1");
					//
					// }
				}
				// else {
				// arrResult = Arrays.asList(strLogicalName,
				// "Unable to submit \""
				// + strLogicalName + "\" button as it is not displayed",
				// "1");
				//
				// }
			} else {
				arrResult = Arrays.asList(strLogicalName, "Unable to submit \""
						+ strLogicalName + "\" button as it is not found", "1");
				// throw new Exception(strLogicalName + "--> Button not found");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName,
					"\"Button not found", "1");
			throw new ButtonElementException(strLogicalName
					+ "--> Button not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to submit element which is in the form.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param element
	 * @return boolean
	 * @throws ButtonElementException
	 */
	public static boolean submit(String strLogicalName, WebDriver driver,
			WebElement element) throws ButtonElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (null != element) {
				if (isDisplayed(strLogicalName, element)) {
					if (isEnabled(strLogicalName, element)) {
						// try {
						element.submit();
						blResult = true;
						// arrResult = Arrays.asList("\""+strLogicalName+"\"",
						// " button has been clicked", "0");
						// } catch (Exception e) {
						// arrResult = Arrays.asList("\""+strLogicalName+"\"",
						// "Unable to submit \"" + strLogicalName
						// + "\" button", "1");
						// }

					}
					// else {
					// arrResult = Arrays.asList(strLogicalName,
					// "Unable to submit \"" + strLogicalName
					// + "\" button as it is not displayed", "1");
					// }

				}
				// else {
				// arrResult = Arrays.asList(strLogicalName,
				// "Unable to submit \""
				// + strLogicalName + "\" button as it is not displayed",
				// "1");
				//
				// }
			} else {
				arrResult = Arrays.asList(strLogicalName, "Unable to submit \""
						+ strLogicalName + "\" button as it is not found", "1");
				// throw new Exception(strLogicalName + "--> Button not found");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName,
					"\"Button not found", "1");
			throw new ButtonElementException(strLogicalName
					+ "--> Button not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to verify Name of the element.
	 * 
	 * @param strLogicalName
	 * @param button
	 * @param valueToBeMatched
	 * @return boolean
	 * @throws ButtonElementException
	 */
	public static boolean verifyName(String strLogicalName, WebElement button,
			String valueToBeMatched) throws ButtonElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (isDisplayed(strLogicalName, button)) {
				String buttonText = button.getText();
				if (buttonText.isEmpty())
					buttonText = button.getAttribute("value");
				if (!buttonText.isEmpty()) {
					if (buttonText.compareTo(valueToBeMatched) == 0) {
						blResult = true;
						// arrResult=Arrays.asList(strLogicalName,"For\""+strLogicalName+"\"button: Expected value("+valueToBeMatched+") Actual value("+buttonText+")",
						// "0");
					} else {
						arrResult = Arrays.asList(strLogicalName, "For\""
								+ strLogicalName + "\"button: Expected value("
								+ valueToBeMatched + ") Actual value("
								+ buttonText + ")", "1");
					}
				} else {
					arrResult = Arrays.asList("\"" + strLogicalName,
							"\"Button value is NULL", "1");
					// throw new Exception(strLogicalName
					// + "--> Button value is NULL");
				}
			}
			// else {
			// arrResult=Arrays.asList("\""+strLogicalName,"\"button is not displayed",
			// "1");
			// }
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName,
					"\"Button not found", "1");
			throw new ButtonElementException(strLogicalName
					+ "--> Button not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}
	
	/**
	 * This method is used to click the WebElement by using JavaScript Executor
	 * 
	 * @param driver
	 * @param button
	 */
	public static void clickbyJS(WebDriver driver, WebElement button) {

		Log.debug("Javascript Executor for click Action");

		CommonHelper.clickbyJS(driver, button);
	}
}