package com.itc.utilities.elementfactory;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;

import com.itc.framework.loggers.Log;
import com.itc.framework.reporters.Resulter;
import com.itc.utilities.enums.ExecutionMode;
import com.itc.utilities.exceptions.ButtonElementException;
import com.itc.utilities.exceptions.ElementException;
import com.itc.utilities.exceptions.TextBoxElementException;
import com.itc.utilities.helpers.CommonHelper;

/**
 * TextBox Element consists of all actions related to TextBox.
 * 
 * @author Madhusudhan
 */
public class TextBoxElement {
	public static boolean enterText(String strLogicalName, WebDriver driver,
			By objectLocator, String strValue) throws TextBoxElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		WebElement element = null;
		objectLocator.toString();
		try {
			List<WebElement> elements = driver.findElements(objectLocator);
			if (elements.size() != 0) {
				element = elements.get(0);
				if (element.isDisplayed()) {
					if (element.isEnabled()) {
						// try {
						element.clear();
						element.sendKeys(strValue);
						blResult = true;
						arrResult = Arrays.asList(strLogicalName, " Enter \""
								+ strValue + "\" data in \"" + strLogicalName
								+ "\" textbox", "0");
						// }
						// catch (Exception e) {
						// arrResult = Arrays.asList(
						// "Unable to enter data in \""
						// + strLogicalName, "1");
						// }
					} else {
						arrResult = Arrays.asList("Unable to enter data in \""
								+ strLogicalName
								+ "\" textbox as it is not enabled", "1");
					}
				} else {
					arrResult = Arrays.asList("Unable to enter data in \""
							+ strLogicalName
							+ "\" textbox as it is not displayed", "1");
				}
			} else {
				arrResult = Arrays.asList("Unable to enter data in \""
						+ strLogicalName + "\" textbox as it cannot be found",
						"1");
				// throw new Exception(strLogicalName
				// +"--> textField not found");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					" textField not found", "1");
			throw new TextBoxElementException(strLogicalName
					+ "--> textField not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to enter text for element.
	 * 
	 * @param strLogicalName
	 * @param element
	 * @param strValue
	 * @return boolean
	 * @throws TextBoxElementException
	 */
	public static boolean enterText(String strLogicalName, WebElement element,
			String strValue) throws TextBoxElementException {

		return enterText(strLogicalName, element, strValue, true);
	}

	/**
	 * This Method is used to enter text for element.
	 * 
	 * @param strLogicalName
	 * @param element
	 * @param strValue
	 * @boolean printStrValue
	 * @return boolean
	 * @throws TextBoxElementException
	 */
	public static boolean enterText(String strLogicalName, WebElement element,
			String strValue, boolean printStrValue)
			throws TextBoxElementException {

		boolean blResult = false;
		try {
			if (null != element) {
				if (element.isDisplayed()) {
					if (element.isEnabled()) {
						if (null != strValue && !strValue.isEmpty()) {
							if (null != element.getAttribute("value")
									&& !element.getAttribute("value").isEmpty()) {
								if (!isReadOnly(strLogicalName, element))
									element.clear();
								CommonHelper.waitInSeconds(1);
							}
							element.sendKeys(strValue);

							String textValue = getText(strLogicalName, element);
							if (null != textValue && !textValue.isEmpty()) {

								blResult = true;

								if (!printStrValue)
									strValue = "******";

								Log.debug(strLogicalName + " Enter \""
										+ strValue + "\" data in \""
										+ strLogicalName + "\" textbox");
							} else {

								Log.error("Text \"" + strValue
										+ "\" not entered in \""
										+ strLogicalName + "\"");
							}
						} else {
							Log.error("Unable to enter data in \""
									+ strLogicalName
									+ "\" textbox as the Value might be NULL or Empty");
						}

					} else {
						Log.error("Unable to enter data in \"" + strLogicalName
								+ "\" textbox as it is not enabled");
					}
				} else {
					Log.error("Unable to enter data in \"" + strLogicalName
							+ "\" textbox as it is not displayed");
				}
			} else {
				Log.error("Unable to enter data in \"" + strLogicalName
						+ "\" textbox as WebElement is NULL");
			}
		} catch (Exception e) {
			Log.error("\"" + strLogicalName + "\"" + " textField not found");
			throw new TextBoxElementException(strLogicalName
					+ "--> textField not found");
		}
		return blResult;
	}

	/**
	 * This Method is used to enter text for element.
	 * 
	 * @param strLogicalName
	 * @param element
	 * @param strValue
	 * @boolean printStrValue
	 * @return boolean
	 * @throws TextBoxElementException
	 */
	public static boolean clearAndEnterText(String strLogicalName,
			WebElement element, String strValue, AndroidDriver driver,
			String executionMode) throws TextBoxElementException {

		boolean blResult = false;
		try {
			if (null != element) {
				if (element.isDisplayed()) {
					if (element.isEnabled()) {
						if (null != strValue && !strValue.isEmpty()) {

							if (executionMode
									.equalsIgnoreCase(ExecutionMode.ANDROID_APP
											.toString())) {
								String text = element.getText();
								if (text != null && !text.contains("Search"))
									while (!element.getText().isEmpty() && !element.getText().contains("Search") && !element.getText().equalsIgnoreCase(strValue)) {
										
										TouchAction touchAction = new TouchAction(
												driver);
										touchAction.longPress(element);
										Keyboard keyboard = ((HasInputDevices) driver)
												.getKeyboard();
										// enter a key
										keyboard.pressKey(Keys.DELETE);

										// (driver).getKeyboard().sendKeys(Keys.DELETE);
									}
							} else {
								element.clear();
							}

							element.sendKeys(strValue);

						} else {
							Log.error("Unable to enter data in \""
									+ strLogicalName
									+ "\" textbox as the Value might be NULL or Empty");
						}

					} else {
						Log.error("Unable to enter data in \"" + strLogicalName
								+ "\" textbox as it is not enabled");
					}
				} else {
					Log.error("Unable to enter data in \"" + strLogicalName
							+ "\" textbox as it is not displayed");
				}
			} else {
				Log.error("Unable to enter data in \"" + strLogicalName
						+ "\" textbox as WebElement is NULL");
			}
		} catch (Exception e) {
			Log.error("\"" + strLogicalName + "\"" + " textField not found");
			throw new TextBoxElementException(strLogicalName
					+ "--> textField not found");
		}
		return blResult;
	}

	/**
	 * This Method is used to enter text for element.
	 * 
	 * @param strLogicalName
	 * @param element
	 * @param strValue
	 * @boolean printStrValue
	 * @return boolean
	 * @throws TextBoxElementException
	 */
	public static boolean enterTextWithoutReCheck(String strLogicalName,
			WebElement element, String strValue, boolean printStrValue)
			throws TextBoxElementException {

		boolean blResult = false;
		try {
			if (null != element) {
				if (element.isDisplayed()) {
					if (element.isEnabled()) {
						if (null != strValue && !strValue.isEmpty()) {
							if (null != element.getAttribute("value")
									&& !element.getAttribute("value").isEmpty()) {
								if (!isReadOnly(strLogicalName, element))
									element.clear();
								CommonHelper.waitInSeconds(1);
							}
							element.sendKeys(strValue);
						} else {
							Log.error("Unable to enter data in \""
									+ strLogicalName
									+ "\" textbox as the Value might be NULL or Empty");
						}

					} else {
						Log.error("Unable to enter data in \"" + strLogicalName
								+ "\" textbox as it is not enabled");
					}
				} else {
					Log.error("Unable to enter data in \"" + strLogicalName
							+ "\" textbox as it is not displayed");
				}
			} else {
				Log.error("Unable to enter data in \"" + strLogicalName
						+ "\" textbox as WebElement is NULL");
			}
		} catch (Exception e) {
			Log.error("\"" + strLogicalName + "\"" + " textField not found");
			throw new TextBoxElementException(strLogicalName
					+ "--> textField not found");
		}
		return blResult;
	}

	/**
	 * This Method is used to clear text of element.
	 * 
	 * @param strLogicalName
	 * @param textField
	 * @return boolean
	 * @throws TextBoxElementException
	 */
	public static boolean clearText(String strLogicalName, WebElement textField)
			throws TextBoxElementException {

		boolean blResult = false;

		try {
			if (textField != null) {
				if (textField.isDisplayed()) {
					if (textField.isEnabled()) {
						textField.clear();
						blResult = true;
						Log.debug("\"" + strLogicalName + "\""
								+ "Data has been cleared");
					} else {
						Log.debug("\"" + strLogicalName + "\""
								+ " textField is not enabled");
					}
				} else {
					Log.debug("\"" + strLogicalName + "\""
							+ " textField does not exist");
				}
			} else {
				Log.debug("\"" + strLogicalName + "\""
						+ " textField does not exist");
			}

		} catch (Exception e) {
			Log.error("\"" + strLogicalName + "\"" + " textField not found");
			throw new TextBoxElementException(strLogicalName
					+ "--> textField not found");
		}

		return blResult;
	}

	/**
	 * This Method is used to get text of element.
	 * 
	 * @param strLogicalName
	 * @param textField
	 * @return String
	 * @throws TextBoxElementException
	 */
	public static String getTextFromDisabled(String strLogicalName,
			WebElement textField) throws TextBoxElementException {
		String returnText = null;
		List<String> arrResult = null;
		try {
			if (textField != null) {
				if (textField.isDisplayed()) {
					if (null != textField.getText()
							&& !textField.getText().isEmpty()) {
						returnText = textField.getText();
					} else if (null != textField.getAttribute("value")
							&& !textField.getAttribute("value").isEmpty()) {
						returnText = textField.getAttribute("value");
					}

					Log.debug("\"" + strLogicalName + "\""
							+ "Text Field : Available data \" " + returnText
							+ "\"");
				} else {
					Log.error("\"" + strLogicalName + "\""
							+ " textField not displayed");
				}
			} else {
				Log.error("\"" + strLogicalName + "\""
						+ " textField does not exist");
			}
		} catch (Exception e) {
			Log.fatal("\"" + strLogicalName + "\"" + " textField not found");
			throw new TextBoxElementException(strLogicalName
					+ "--> textField not found");
		}
		Resulter.log(arrResult);
		return returnText;
	}

	/**
	 * This Method is used to get text of element.
	 * 
	 * @param strLogicalName
	 * @param textField
	 * @return String
	 * @throws TextBoxElementException
	 */
	public static String getTextFromHidden(String strLogicalName,
			WebElement textField) throws TextBoxElementException {
		String returnText = null;
		List<String> arrResult = null;
		try {
			if (textField != null) {
				if (null != textField.getText()
						&& !textField.getText().isEmpty()) {
					returnText = textField.getText();
				} else if (null != textField.getAttribute("value")
						&& !textField.getAttribute("value").isEmpty()) {
					returnText = textField.getAttribute("value");
				}

				Log.debug("\"" + strLogicalName + "\""
						+ "Text Field : Available data \" " + returnText + "\"");
			} else {
				Log.error("\"" + strLogicalName + "\""
						+ " textField does not exist");
			}
		} catch (Exception e) {
			Log.fatal("\"" + strLogicalName + "\"" + " textField not found");
			throw new TextBoxElementException(strLogicalName
					+ "--> textField not found");
		}
		Resulter.log(arrResult);
		return returnText;
	}

	/**
	 * This Method is used to get text of element.
	 * 
	 * @param strLogicalName
	 * @param textField
	 * @return String
	 * @throws TextBoxElementException
	 */
	public static String getText(String strLogicalName, WebElement textField)
			throws TextBoxElementException {
		String returnText = null;
		List<String> arrResult = null;
		try {
			if (textField != null) {
				if (textField.isDisplayed()) {
					if (textField.isEnabled()) {
						if (null != textField.getText()
								&& !textField.getText().isEmpty()) {
							returnText = textField.getText();
						} else if (null != textField.getAttribute("value")
								&& !textField.getAttribute("value").isEmpty()) {
							returnText = textField.getAttribute("value");
						}

						Log.debug("\"" + strLogicalName + "\""
								+ "Text Field : Available data \" "
								+ returnText + "\"");
					} else {
						Log.error("\"" + strLogicalName + "\""
								+ " textField is not enabled");
					}
				} else {
					Log.error("\"" + strLogicalName + "\""
							+ " textField not displayed");
				}
			} else {
				Log.error("\"" + strLogicalName + "\""
						+ " textField does not exist");
			}
		} catch (Exception e) {
			Log.fatal("\"" + strLogicalName + "\"" + " textField not found");
			throw new TextBoxElementException(strLogicalName
					+ "--> textField not found");
		}
		Resulter.log(arrResult);
		return returnText;
	}

	/**
	 * This Method is used to get text of element.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param objectLocator
	 * @return String
	 * @throws TextBoxElementException
	 */
	public static String getText(String strLogicalName, WebDriver driver,
			By objectLocator) throws TextBoxElementException {
		String returnText = null;
		List<String> arrResult = null;
		WebElement element = null;
		objectLocator.toString();
		try {
			List<WebElement> elements = driver.findElements(objectLocator);
			if (elements.size() != 0) {
				element = elements.get(0);
				if (element.isDisplayed()) {
					if (element.isEnabled()) {
						returnText = element.getText();
						// arrResult = Arrays.asList("\"" + strLogicalName +
						// "\"",
						// "Available data in the textField: "
						// + returnText, "0");
					} else {
						arrResult = Arrays.asList("\"" + strLogicalName + "\"",
								" textField is not enabled", "1");
					}
				} else {
					arrResult = Arrays.asList("\"" + strLogicalName + "\"",
							" textField not displayed", "1");
				}
			} else {
				arrResult = Arrays.asList("\"" + strLogicalName + "\"",
						" textField does not exist", "1");
				// throw new Exception(strLogicalName
				// +"--> textField not found");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					" textField not found", "1");
			throw new TextBoxElementException(strLogicalName
					+ "--> textField not found");
		}
		Resulter.log(arrResult);
		return returnText;
	}

	/**
	 * This Method is used to get attribute of element.
	 * 
	 * @param strLogicalName
	 * @param textField
	 * @param attribute
	 * @return String
	 * @throws TextBoxElementException
	 */
	public static String getAttribute(String strLogicalName,
			WebElement textField, String attribute)
			throws TextBoxElementException {
		String returnText = null;
		List<String> arrResult = null;
		try {
			if (textField != null) {
				if (textField.isDisplayed()) {
					if (textField.isEnabled()) {
						returnText = textField.getAttribute(attribute);
						arrResult = Arrays.asList("\"" + strLogicalName + "\"",
								"Available data in the textField: "
										+ returnText, "0");
					} else {
						arrResult = Arrays.asList("\"" + strLogicalName + "\"",
								" textField is not enabled", "1");
					}
				} else {
					arrResult = Arrays.asList("\"" + strLogicalName + "\"",
							" textField is not displayed", "1");
				}
			} else {
				arrResult = Arrays.asList("\"" + strLogicalName + "\"",
						" textField does not exist", "1");
				// throw new Exception(strLogicalName
				// +"--> textField not found");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					" textField not found", "1");
			throw new TextBoxElementException(strLogicalName
					+ "--> textField not found");
		}
		Resulter.log(arrResult);
		return returnText;
	}

	/**
	 * This Method is used to get attribute of element.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param objectLocator
	 * @param attribute
	 * @return String
	 * @throws TextBoxElementException
	 */
	public static String getAttribute(String strLogicalName, WebDriver driver,
			By objectLocator, String attribute) throws TextBoxElementException {
		String returnText = null;
		List<String> arrResult = null;
		WebElement element = null;
		objectLocator.toString();
		try {
			List<WebElement> elements = driver.findElements(objectLocator);
			if (elements.size() != 0) {
				element = elements.get(0);
				if (element.isDisplayed()) {
					if (element.isEnabled()) {
						returnText = element.getAttribute(attribute);
						arrResult = Arrays.asList("\"" + strLogicalName + "\"",
								"Available data in the textField: "
										+ returnText, "0");
					} else {
						arrResult = Arrays.asList("\"" + strLogicalName + "\"",
								" textField is not enabled", "1");
					}
				} else {
					arrResult = Arrays.asList("\"" + strLogicalName + "\"",
							" textField is not displayed", "1");
				}
			} else {
				arrResult = Arrays.asList("\"" + strLogicalName + "\"",
						" textField does not exist", "1");
				// throw new Exception(strLogicalName
				// +"--> textField not found");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					" textField not found", "1");
			throw new TextBoxElementException(strLogicalName
					+ "--> textField not found");
		}
		Resulter.log(arrResult);
		return returnText;
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
						&& (element.getAttribute("readonly").equalsIgnoreCase(
								"readonly") || element.getAttribute("readonly")
								.equalsIgnoreCase("true"))) {
					blResult = true;
				} else if (null != element.getAttribute("disabled")
						&& element.getAttribute("disabled").equalsIgnoreCase(
								"true")) {
					blResult = true;
				} else {
					blResult = false;
					Log.debug(strLogicalName + " Element is not readonly");
				}
			} else {
				blResult = false;
				Log.error(strLogicalName + " Element does not exist");
			}
		} catch (Exception e) {
			Log.error("\"" + strLogicalName + "\"" + " WebElement not found");
			throw new ElementException(strLogicalName
					+ "--> WebElement not found");
		}
		return blResult;
	}

	/**
	 * This Method is used for click action.
	 * 
	 * @param strLogicalName
	 * @param textField
	 * @return boolean(execution status)
	 * @throws ButtonElementException
	 */
	public static boolean click(String strLogicalName, WebElement textField)
			throws TextBoxElementException {
		boolean blResult = false;
		try {
			if (textField.isDisplayed()) {
				if (textField.isEnabled()) {
					textField.click();
					blResult = true;
					Log.debug("\"" + strLogicalName
							+ "\" TextBox has been clicked");
				} else
					Log.error("Was unable to click the locator "
							+ strLogicalName + " as it is not enabled");
			} else
				Log.error("Was unable to click the locator " + strLogicalName
						+ " as it is not displayed");
		} catch (Exception e) {
			Log.error("\"" + strLogicalName + "\" Text Box not found -->" + e);
			throw new TextBoxElementException("--> TextBox not found --> " + e);
		}
		return blResult;
	}
}
