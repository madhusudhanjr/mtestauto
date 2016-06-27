package com.itc.utilities.elementfactory;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.itc.framework.reporters.Resulter;
import com.itc.utilities.exceptions.ListElementException;


/**
 * ListElement Class contains actions related to ListElement.
 * 
 * @author Madhusudhan
 */
public class ListElement {

	/**
	 * This Method is used to check for listElement exists.
	 * @param strLogicalName
	 * @param listElement
	 * @return boolean
	 * @throws ListElementException
	 */
	public static boolean exists(String strLogicalName, WebElement listElement)
			throws ListElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			// if(listElement.isDisplayed()){
			if (listElement != null) {
				blResult = true;
				// arrResult= Arrays.asList(strLogicalName," List exists", "0");
			} else {
				blResult = false;
				arrResult = Arrays.asList(strLogicalName,
						" List does not exist", "1");
//				throw new Exception(strLogicalName+"--> List does not exist");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList(strLogicalName, " List does not exist",
					"1");
			throw new ListElementException(strLogicalName+"--> List does not exist");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to check for ListElement enabled.
	 * @param strLogicalName
	 * @param listElement
	 * @return boolean
	 * @throws ListElementException
	 */
	public static boolean isEnabled(String strLogicalName,
			WebElement listElement) throws ListElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			// if(listElement.isDisplayed()){
			if (listElement != null) {
				if (listElement.isEnabled()) {
					blResult = true;
					// arrResult=
					// Arrays.asList(strLogicalName," List is enabled", "0");
				} else {
					arrResult = Arrays.asList(strLogicalName,
							" List is not enabled", "0");
				}
			} else {
				blResult = false;
				arrResult = Arrays.asList(strLogicalName,
						" List does not exist", "1");
//				throw new Exception(strLogicalName+"--> List does not exist");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList(strLogicalName, " List does not exist",
					"1");
			throw new ListElementException(strLogicalName+"--> List does not exist");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to perform select action.
	 * @param strLogicalName
	 * @param driver
	 * @param objectLocator
	 * @param itemToBeSelected
	 * @return boolean
	 * @throws ListElementException
	 */
	public static boolean select(String strLogicalName, WebDriver driver,
			By objectLocator, String itemToBeSelected) throws ListElementException {
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
						// try {
						new Select(element)
								.selectByVisibleText(itemToBeSelected);
						blResult = true;
						// arrResult = Arrays.asList(strLogicalName,
						// " List is enabled", "0");
						// } catch (Exception e) {
						// arrResult = Arrays.asList(strLogicalName,
						// " List could not be selected", "1");
						// }
					}
					// else {
					// arrResult = Arrays.asList(strLogicalName,
					// " List is not enabled", "1");
					// }
				} else {
					arrResult = Arrays.asList(strLogicalName,
							" List not found", "1");
//					throw new Exception(strLogicalName+"--> List does not exist");
				}
			} else {
				arrResult = Arrays.asList(strLogicalName,
						" List does not exist", "1");
//				throw new Exception(strLogicalName+"--> List does not exist");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList(strLogicalName, " List does not exist",
					"1");
			throw new ListElementException(strLogicalName+"--> List does not exist");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to perform select action for ListElement.
	 * @param strLogicalName
	 * @param driver
	 * @param element
	 * @param itemToBeSelected
	 * @return boolean
	 * @throws ListElementException
	 */
	public static boolean select(String strLogicalName, WebDriver driver,
			WebElement element, String itemToBeSelected) throws ListElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (null != element) {
				if (element.isDisplayed()) {
					if (isEnabled(strLogicalName, element)) {
						// try {
						new Select(element)
								.selectByVisibleText(itemToBeSelected);
						blResult = true;
						// arrResult = Arrays.asList(strLogicalName,
						// " List is enabled", "0");
						// } catch (Exception e) {
						// arrResult = Arrays.asList(strLogicalName,
						// " List could not be selected", "1");
						// }
					}
					// else {
					// arrResult = Arrays.asList(strLogicalName,
					// " List is not enabled", "1");
					// }
				} else {
					arrResult = Arrays.asList(strLogicalName,
							" List not found", "1");
//					throw new Exception(strLogicalName+"--> List does not exist");
				}
			} else {
				arrResult = Arrays.asList(strLogicalName,
						" List does not exist", "1");
//				throw new Exception(strLogicalName+"--> List does not exist");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList(strLogicalName, " List does not exist",
					"1");
			throw new ListElementException(strLogicalName+"--> List does not exist");
		}
		Resulter.log(arrResult);
		return blResult;
	}
}