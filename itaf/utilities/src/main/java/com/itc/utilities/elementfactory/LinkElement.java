package com.itc.utilities.elementfactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.itc.framework.loggers.Log;
import com.itc.framework.reporters.Resulter;
import com.itc.utilities.exceptions.DataSourceException;
import com.itc.utilities.exceptions.LinkElementException;

/**
 * LinkElement Class consists of all actions related to LinkElements.
 * 
 * @author Madhusudhan
 */
public class LinkElement {

	/**
	 * This Method is used to check for Element is displayed.
	 * 
	 * @param strLogicalName
	 * @param link
	 * @return boolean
	 * @throws LinkElementException
	 */
	public static boolean isDisplayed(String strLogicalName, WebElement link)
			throws LinkElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (link.isDisplayed()) {
				blResult = true;
				// arrResult=
				// Arrays.asList("\""+strLogicalName+"\""," link is displayed",
				// "0");
			} else {
				blResult = false;
				arrResult = Arrays.asList("\"" + strLogicalName + "\"",
						" link is not displayed", "1");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					" link not found", "1");
			throw new LinkElementException(strLogicalName
					+ "--> link not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to check for Element is enabled.
	 * 
	 * @param strLogicalName
	 * @param link
	 * @return boolean
	 * @throws LinkElementException
	 */
	public static boolean isEnabled(String strLogicalName, WebElement link)
			throws LinkElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (link.isDisplayed()) {
				if (link.isEnabled()) {
					blResult = true;
					// arrResult=
					// Arrays.asList("\""+strLogicalName+"\""," link is enabled",
					// "0");
				} else {
					arrResult = Arrays.asList("\"" + strLogicalName + "\"",
							" link is not enabled", "1");
				}
			} else {
				arrResult = Arrays.asList("\"" + strLogicalName + "\"",
						" link is not displayed", "1");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					" link not found", "1");
			throw new LinkElementException(strLogicalName
					+ "--> link not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to check for Element is disabled.
	 * 
	 * @param strLogicalName
	 * @param link
	 * @return boolean
	 * @throws LinkElementException
	 */
	public static boolean isDisabled(String strLogicalName, WebElement link)
			throws LinkElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (link.isDisplayed()) {
				if (link.isEnabled() == false) {
					blResult = true;
					// arrResult=
					// Arrays.asList("\""+strLogicalName+"\""," link is disabled",
					// "0");
				} else {
					arrResult = Arrays.asList("\"" + strLogicalName + "\"",
							" link is not disabled", "1");
				}
			} else {
				arrResult = Arrays.asList("\"" + strLogicalName + "\"",
						" link is not displayed", "1");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					" link not found", "1");
			throw new LinkElementException(strLogicalName
					+ "--> link not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to compare between two lint text.
	 * 
	 * @param strLogicalName
	 * @param link
	 * @param valueToBeMatched
	 * @return boolean
	 * @throws LinkElementException
	 */
	public static boolean compareLinkText(String strLogicalName,
			WebElement link, String valueToBeMatched)
			throws LinkElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		String linkText = "";
		try {
			if (link.isDisplayed()) {
				linkText = link.getText();
				if (!linkText.isEmpty()) {
					if (linkText.compareTo(valueToBeMatched) == 0) {
						blResult = true;
						// arrResult = Arrays.asList("\"" + strLogicalName +
						// "\"",
						// " link: Expected value(" + valueToBeMatched
						// + ") Actual value(" + linkText + ")",
						// "0");
					} else {
						arrResult = Arrays.asList("\"" + strLogicalName + "\"",
								" link: Expected value(" + valueToBeMatched
										+ ") Actual value(" + linkText + ")",
								"1");
					}

				} else {
					arrResult = Arrays.asList("\"" + strLogicalName + "\"",
							" link text is null", "1");
				}
			} else {
				arrResult = Arrays.asList("\"" + strLogicalName + "\"",
						" link is not displayed", "1");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					" link not found", "1");
			throw new LinkElementException(strLogicalName
					+ "--> link not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to get link text of Element.
	 * 
	 * @param strLogicalName
	 * @param link
	 * @return String
	 * @throws LinkElementException
	 */
	public static String getLinkText(String strLogicalName, WebElement link)
			throws LinkElementException {
		List<String> arrResult = null;
		String linkText = null;
		try {
			if (link.isDisplayed()) {
				linkText = link.getText();
				if (null != linkText) {
					if (!linkText.isEmpty()) {
						// arrResult = Arrays.asList("\"" + strLogicalName +
						// "\"",
						// "link text is " + linkText, "0");
					} else {
						arrResult = Arrays.asList("\"" + strLogicalName + "\"",
								"link text is empty", "0");
					}
				} else {
					arrResult = Arrays.asList("\"" + strLogicalName + "\"",
							"link text not available", "1");
				}
			} else {
				arrResult = Arrays.asList("\"" + strLogicalName + "\"",
						" link is not displayed", "1");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					" link not found", "1");
			throw new LinkElementException(strLogicalName
					+ "--> link not found");
		}
		Resulter.log(arrResult);
		return linkText;
	}

	/**
	 * This Method is used to perform click action for LinkElement.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param objectLocator
	 * @return boolean
	 * @throws LinkElementException
	 * @throws DataSourceException
	 * @throws IOException
	 */
	public static boolean click(String strLogicalName, WebDriver driver,
			By objectLocator) throws LinkElementException, DataSourceException,
			IOException {
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
						try {
							element.click();
							blResult = true;
							// arrResult = Arrays.asList("\"" + strLogicalName
							// + "\"", " link has been clicked", "0");
						} catch (org.openqa.selenium.WebDriverException e) {
							try {
								//Locatable locatable = (Locatable) element;
								/*MouseEvent mouse = ((org.openqa.selenium.interactions.HasInputDevices) driver)
										.getMouse();
								mouse.click(locatable.getCoordinates());*/
							} catch (Exception e1) {
								e.printStackTrace();
								arrResult = Arrays
										.asList("\"" + strLogicalName + "\"",
												" link could not be clicked by Mouse(locatable workaround Failed)",
												"1");
								throw new LinkElementException(strLogicalName
										+ "--> link could not be clicked; ", e);
							}
						} catch (Exception e) {
							e.printStackTrace();
							arrResult = Arrays.asList("\"" + strLogicalName
									+ "\"", " link could not be clicked", "1");
							throw new LinkElementException(strLogicalName
									+ "--> link could not be clicked; ", e);
						}

					} else {
						arrResult = Arrays.asList("\"" + strLogicalName + "\"",
								" link is not enabled", "1");
					}
				} else {
					arrResult = Arrays.asList("\"" + strLogicalName + "\"",
							" link is not displayed", "1");
				}
			} else {
				arrResult = Arrays.asList("\"" + strLogicalName + "\"",
						" link is not found", "1");
				// throw new Exception(strLogicalName+"--> link not found");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					" link not found", "1");
			throw new LinkElementException(strLogicalName
					+ "--> link not found; ", e);
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to perform click action for LinkElement.
	 * 
	 * @param strLogicalName
	 * @param element
	 * @return boolean
	 * @throws LinkElementException
	 */
	public static boolean click(String strLogicalName, WebElement element)
			throws LinkElementException {

		boolean blResult = false;

		try {
			if (null != element) {
				if (element.isDisplayed()) {
					if (element.isEnabled()) {
						element.click();
						blResult = true;
						Log.debug("\"" + strLogicalName + "\""
								+ " link has been clicked");
					} else {
						Log.error("\"" + strLogicalName + "\""
								+ " link is not enabled");
					}
				} else {
					Log.error("\"" + strLogicalName + "\""
							+ " link is not displayed");
				}
			} else {
				Log.error("\"" + strLogicalName + "\"" + " link is not found");
			}
		} catch (Exception e) {
			Log.fatal("\"" + strLogicalName + "\"" + " link not found");
			throw new LinkElementException(strLogicalName
					+ "--> link not found");
		}

		return blResult;
	}

	/**
	 * This Method is used to perform click action for LinkElement in New tab.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param element
	 * @return boolean
	 * @throws LinkElementException
	 */
	public static boolean clickToNewTab(String strLogicalName,
			WebDriver driver, WebElement element) throws LinkElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (null != element) {
				if (element.isDisplayed()) {
					if (element.isEnabled()) {
						// try {

						Actions action = new Actions(driver);
						action.keyDown(Keys.CONTROL).build().perform();
						action.click(element).build().perform();
						blResult = true;
						arrResult = Arrays.asList("\"" + strLogicalName + "\"",
								" link has been clicked", "0");
						// } catch (Exception e) {
						// arrResult = Arrays.asList("\"" + strLogicalName
						// + "\"", " link is could not be clicked",
						// "1");
						// }

					} else {
						arrResult = Arrays.asList("\"" + strLogicalName + "\"",
								" link is not enabled", "1");

					}
				} else {
					arrResult = Arrays.asList("\"" + strLogicalName + "\"",
							" link is not displayed", "1");

				}
			} else {
				arrResult = Arrays.asList("\"" + strLogicalName + "\"",
						" link is not found", "1");
				// throw new Exception(strLogicalName+"--> link not found");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					" link not found", "1");
			throw new LinkElementException(strLogicalName
					+ "--> link not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}
}