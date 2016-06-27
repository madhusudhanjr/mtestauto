package com.itc.utilities.elementfactory;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.itc.framework.loggers.Log;
import com.itc.framework.reporters.Resulter;
import com.itc.utilities.exceptions.RadioButtonElementException;

/**
 * RadioButtonELement Class consists of all actions related to RadioButton.
 * 
 * @author Madhusudhan
 */
public class RadioButtonElement {

	/**
	 * This Method is used to check for RadioButton exists.
	 * 
	 * @param strLogicalName
	 * @param radioButton
	 * @return boolean
	 * @throws RadioButtonElementException
	 */
	public static boolean exists(String strLogicalName, WebElement radioButton)
			throws RadioButtonElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			// if(radioButton.isDisplayed()){
			if (radioButton != null) {
				blResult = true;
				// arrResult=
				// Arrays.asList(strLogicalName," Radio Button exists", "0");
			} else {
				blResult = false;
				arrResult = Arrays.asList(strLogicalName,
						" Radio Button does not exist", "1");
				// throw new Exception(strLogicalName+
				// "--> Radio Button does not exist");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList(strLogicalName,
					" Radio Button does not exist", "1");
			throw new RadioButtonElementException(strLogicalName
					+ "--> Radio Button does not exist");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to check for RadioButton enabled.
	 * 
	 * @param strLogicalName
	 * @param radioButton
	 * @return
	 * @throws RadioButtonElementException
	 */
	public static boolean isEnabled(String strLogicalName,
			WebElement radioButton) throws RadioButtonElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		// if(radioButton.isDisplayed()){
		try {
			if (radioButton != null) {
				if (radioButton.isEnabled()) {
					blResult = true;
					// arrResult=
					// Arrays.asList(strLogicalName," Radio Button is enabled",
					// "0");
				}
				// else
				// {
				// arrResult=
				// Arrays.asList(strLogicalName," Radio Button is not enabled",
				// "1");
				// }
			} else {
				arrResult = Arrays.asList(strLogicalName,
						" Radio Button does not exist", "1");
				// throw new Exception(strLogicalName+
				// "--> Radio Button does not exist");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList(strLogicalName,
					" Radio Button does not exist", "1");
			throw new RadioButtonElementException(strLogicalName
					+ "--> Radio Button does not exist");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to click on RadioButton.
	 * 
	 * @param strLogicalName
	 * @param checkBox
	 * @return boolean
	 * @throws RadioButtonElementException
	 */
	public static boolean click(String strLogicalName, WebElement checkBox)
			throws RadioButtonElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (exists(strLogicalName, checkBox)) {
				if (checkBox.isDisplayed()) {
					if (isEnabled(strLogicalName, checkBox)) {
						checkBox.click();
						blResult = true;
						// arrResult=
						// Arrays.asList(strLogicalName," Radio Button has been clicked",
						// "0");
					}
				}
				// else
				// {
				// arrResult=
				// Arrays.asList(strLogicalName," Radio Button is not enabled",
				// "1");
				// }
			}
			// else {
			// arrResult=
			// Arrays.asList(strLogicalName," Radio Button does not exist",
			// "1");
			// }
		} catch (Exception e) {
			arrResult = Arrays.asList(strLogicalName,
					" Radio Button does not exist", "1");
			throw new RadioButtonElementException(strLogicalName
					+ "--> Radio Button does not exist");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to get status of RadioButton.
	 * 
	 * @param strLogicalName
	 * @param radioButton
	 * @return boolean
	 * @throws RadioButtonElementException
	 */
	public static boolean getStatus(String strLogicalName,
			WebElement radioButton) throws RadioButtonElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {

			/*
			 * if (exists(strLogicalName, radioButton)) { if
			 * (radioButton.isDisplayed()) { if (isEnabled(strLogicalName,
			 * radioButton)) { if
			 * (radioButton.getAttribute("value").equalsIgnoreCase( "on")) {
			 */

			if (exists(strLogicalName, radioButton)) {
				if (radioButton.isDisplayed()) {
					if (isEnabled(strLogicalName, radioButton)) {
						if (radioButton.getAttribute("checked") != null) {
							if (radioButton.getAttribute("value")
									.equalsIgnoreCase("on")
									|| radioButton.getAttribute("checked")
											.equalsIgnoreCase("true")) {
								blResult = true;
								arrResult = Arrays.asList(strLogicalName,
										" Status of RadioButton: ON ", "0");
							} else {
								blResult = false;
								arrResult = Arrays.asList(strLogicalName,
										" Status of RadioButton: OFF", "0");
							}
						} else {
							blResult = false;
							arrResult = Arrays.asList(strLogicalName,
									" Status of RadioButton: OFF", "0");
						}
					}
					// else
					// {
					// arrResult=
					// Arrays.asList(strLogicalName," Radio Button is not enabled",
					// "1");
					// }
				}
				// else {
				// arrResult=
				// Arrays.asList(strLogicalName," Radio Button does not exist",
				// "1");
				// }
			}
		} catch (Exception e) {
			arrResult = Arrays.asList(strLogicalName,
					" Radio Button does not exist", "1");
			throw new RadioButtonElementException(strLogicalName
					+ "--> Radio Button does not exist");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to select RadioButton.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param objectLocator
	 * @param strValue
	 * @return boolean
	 * @throws RadioButtonElementException
	 */
	public static boolean select(String strLogicalName, WebDriver driver,
			By objectLocator, String strValue)
			throws RadioButtonElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		WebElement element = null;
		objectLocator.toString();
		try {
			List<WebElement> elements = driver.findElements(objectLocator);
			if (elements.size() != 0) {
				element = elements.get(0);
				if (element.isDisplayed()) {
					if (isEnabled(strLogicalName, element)) {
						// try{
						element.click();
						arrResult = Arrays.asList(strLogicalName,
								" Radio Button has been clicked", "0");
						// }
						// catch(Exception e)
						// {
						// arrResult=
						// Arrays.asList(strLogicalName," Radio Button is could not be selected",
						// "1");
						// }
					}
					// else {
					// arrResult=
					// Arrays.asList(strLogicalName," Radio Button is not enabled",
					// "1");
					// }
				} else {
					arrResult = Arrays.asList(strLogicalName,
							" Radio Button does not exist", "1");
				}
			} else {
				arrResult = Arrays.asList(strLogicalName,
						" Radio Button does not exist", "1");
				// throw new Exception(strLogicalName+
				// "--> Radio Button does not exist");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList(strLogicalName,
					" Radio Button does not exist", "1");
			throw new RadioButtonElementException(strLogicalName
					+ "--> Radio Button does not exist");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to select RadioButton.
	 * 
	 * @param strLogicalName
	 * @param element
	 * @return boolean
	 * @throws RadioButtonElementException
	 */
	public static boolean select(String strLogicalName, WebElement element)
			throws RadioButtonElementException {
		
		boolean blResult = false;
		
		try {
			if (null != element) {
				if (element.isDisplayed()) {
					if (isEnabled(strLogicalName, element)) {

						element.click();

						Log.debug(strLogicalName
								+ " Radio Button has been clicked");
					}

				} else {
					Log.debug(strLogicalName + " Radio Button does not exist");
				}
			} else {
				Log.debug(strLogicalName + " Radio Button does not exist");
			}
		} catch (Exception e) {

			Log.error(strLogicalName + " Radio Button does not exist");
			throw new RadioButtonElementException(strLogicalName
					+ "--> Radio Button does not exist");
		}
		return blResult;
	}
}