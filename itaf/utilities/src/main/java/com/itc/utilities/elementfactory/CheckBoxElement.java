package com.itc.utilities.elementfactory;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.itc.framework.loggers.Log;
import com.itc.framework.reporters.Resulter;
import com.itc.utilities.exceptions.CheckBoxElementException;
import com.itc.utilities.helpers.CommonHelper;

/**
 * CheckBoxElement class consists of all actions related to checkbox.
 * 
 * @author Madhusudhan
 */
public class CheckBoxElement {

	/**
	 * This Method is used to perform check action.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param objectLocator
	 * @return boolean
	 * @throws CheckBoxElementException
	 */
	public static boolean check(String strLogicalName, WebDriver driver,
			By objectLocator) throws CheckBoxElementException {
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
						if (!element.isSelected()) {
							// try{
							element.click();
							blResult = true;
							// arrResult=
							// Arrays.asList(strLogicalName," Checkbox is selected",
							// "0");
							// }
							// catch(Exception e)
							// {
							// e.printStackTrace();
							// arrResult=
							// Arrays.asList(strLogicalName," Checkbox could not be selected",
							// "1");
							// }
						} else {
							arrResult = Arrays.asList(strLogicalName,
									" Checkbox is already selected", "0");
						}
					}
					// else{
					// arrResult=
					// Arrays.asList(strLogicalName," Checkbox is not enabled",
					// "1");
					//
					// }
				} else {
					arrResult = Arrays.asList(strLogicalName,
							" Checkbox is not displayed", "1");
				}
			} else {
				arrResult = Arrays.asList(strLogicalName,
						" Checkbox is not found", "1");
				// throw new Exception(strLogicalName+"--> Checkbox not found");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList(strLogicalName, " Checkbox not found",
					"1");
			throw new CheckBoxElementException(strLogicalName
					+ "--> Checkbox not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to perform check action.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param element
	 * @return boolean
	 * @throws CheckBoxElementException
	 */
	public static boolean check(String strLogicalName, WebDriver driver,
			WebElement element) throws CheckBoxElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (null != element) {
				if (element.isDisplayed()) {
					if (isEnabled(strLogicalName, element)) {
						if (!element.isSelected()) {
							// try{
							element.click();
							blResult = true;
							Log.debug(strLogicalName + " Checkbox is selected");
						} else {
							Log.error(strLogicalName
									+ " Checkbox is already selected");
						}
					}
				} else {
					Log.error(strLogicalName + " Checkbox is not displayed");

				}
			} else {
				Log.error(strLogicalName + " Checkbox is not found");
			}
		} catch (Exception e) {
			
			try {

				CommonHelper.waitInSeconds(5);
				CommonHelper.clickbyJS(driver, element);
				Log.debug("clicked by JS");
				blResult = true;

			} catch (Exception ex) {

				Log.error(strLogicalName + " Checkbox not found");
				throw new CheckBoxElementException(strLogicalName
						+ "--> Checkbox not found -->"+ex);

			}
			
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to perform check action.
	 * 
	 * @param strLogicalName
	 * @param checkBox
	 * @return boolean
	 * @throws CheckBoxElementException
	 */
	public static boolean check(String strLogicalName, WebElement checkBox)
			throws CheckBoxElementException {
		boolean blResult = false;

		try {
			if (checkBox.isDisplayed()) {
				if (isEnabled(strLogicalName, checkBox)) {
					if (!checkBox.isSelected()) {
						checkBox.click();
						Log.debug(strLogicalName + " Checkbox selected");
					}
					blResult = true;
					Log.debug(strLogicalName + " Checkbox has been selected");
				} else {
					Log.error(strLogicalName + " Checkbox is not enabled");
				}
			} else {
				Log.error(strLogicalName + " Checkbox does not exist");
			}
		} catch (Exception e) {
			Log.fatal(strLogicalName + " Checkbox not found");
			throw new CheckBoxElementException(strLogicalName
					+ "--> Checkbox not found");
		}

		return blResult;
	}

	/**
	 * This Method is used to perform click action.
	 * 
	 * @param strLogicalName
	 * @param checkBox
	 * @return boolean
	 * @throws CheckBoxElementException
	 */
	public static boolean click(String strLogicalName, WebElement checkBox)
			throws CheckBoxElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (checkBox.isDisplayed()) {
				if (isEnabled(strLogicalName, checkBox)) {
					checkBox.click();
					blResult = true;
					// arrResult = Arrays.asList(strLogicalName,
					// " Checkbox has been clicked", "0");
				}
				// else
				// {
				// arrResult=
				// Arrays.asList(strLogicalName," Checkbox is not enabled",
				// "1");
				// }
			}
			// else {
			// arrResult=
			// Arrays.asList(strLogicalName," Checkbox does not exist", "1");
			// }
		} catch (Exception e) {
			arrResult = Arrays.asList(strLogicalName, " Checkbox not found",
					"1");
			throw new CheckBoxElementException(strLogicalName
					+ "--> Checkbox not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to check for existence of element.
	 * 
	 * @param strLogicalName
	 * @param checkBox
	 * @return boolean
	 * @throws CheckBoxElementException
	 */
	public static boolean exists(String strLogicalName, WebElement checkBox)
			throws CheckBoxElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (checkBox != null) {
				// if(checkBox.isDisplayed()){
				blResult = true;
				// arrResult= Arrays.asList(strLogicalName," Checkbox exists",
				// "0");
			}
			// else{
			// blResult=false;
			// arrResult=
			// Arrays.asList(strLogicalName," Checkbox not displayed", "1");
			// } }
			else {
				blResult = false;
				arrResult = Arrays.asList(strLogicalName,
						" Checkbox does not exist", "1");
				// throw new Exception(strLogicalName+"--> Checkbox not found");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList(strLogicalName, " Checkbox not found",
					"1");
			throw new CheckBoxElementException(strLogicalName
					+ "--> Checkbox not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to check for element check action.
	 * 
	 * @param strLogicalName
	 * @param checkBox
	 * @return
	 * @throws CheckBoxElementException
	 */
	public static boolean isChecked(String strLogicalName, WebElement checkBox)
			throws CheckBoxElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (checkBox.isDisplayed()) {
				if (isEnabled(strLogicalName, checkBox)) {
					if (checkBox.isSelected()) {
						blResult = true;
						// arrResult=
						// Arrays.asList(strLogicalName," Checkbox is selected",
						// "0");
					} else {
						arrResult = Arrays.asList(strLogicalName,
								" Checkbox is not selected", "1");
					}
				}
				// else
				// {
				// arrResult=
				// Arrays.asList(strLogicalName," Checkbox is not enabled",
				// "1");
				// }
			}
			// else {
			// arrResult=
			// Arrays.asList(strLogicalName," Checkbox does not exist", "1");
			// }
		} catch (Exception e) {
			arrResult = Arrays.asList(strLogicalName, " Checkbox not found",
					"1");
			throw new CheckBoxElementException(strLogicalName
					+ "--> Checkbox not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to check for disabled action.
	 * 
	 * @param strLogicalName
	 * @param checkBox
	 * @return
	 * @throws CheckBoxElementException
	 */
	public static boolean isDisabled(String strLogicalName, WebElement checkBox)
			throws CheckBoxElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			// if(checkBox.isDisplayed()){
			if (!checkBox.isEnabled()) {
				blResult = true;
				// arrResult=
				// Arrays.asList(strLogicalName," Checkbox is disabled", "0");
			} else {
				arrResult = Arrays.asList(strLogicalName,
						" Checkbox is not disabled", "1");
			}
			// }
			// else {
			// arrResult=
			// Arrays.asList(strLogicalName," Checkbox does not exist", "1");
			// }
		} catch (Exception e) {
			arrResult = Arrays.asList(strLogicalName, " Checkbox not found",
					"1");
			throw new CheckBoxElementException(strLogicalName
					+ "--> Checkbox not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to check for element enable action.
	 * 
	 * @param strLogicalName
	 * @param checkBox
	 * @return
	 * @throws CheckBoxElementException
	 */
	public static boolean isEnabled(String strLogicalName, WebElement checkBox)
			throws CheckBoxElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			// if(checkBox.isDisplayed()){
			if (exists(strLogicalName, checkBox)) {
				if (checkBox.isEnabled()) {
					blResult = true;
					// arrResult=
					// Arrays.asList(strLogicalName," Checkbox is enabled",
					// "0");
				} else {
					arrResult = Arrays.asList(strLogicalName,
							" Checkbox is not enabled", "1");
				}
			}
			// }
			// else {
			// arrResult=
			// Arrays.asList(strLogicalName," Checkbox does not exist", "1");
			// }
		} catch (Exception e) {
			arrResult = Arrays.asList(strLogicalName, " Checkbox not found",
					"1");
			throw new CheckBoxElementException(strLogicalName
					+ "--> Checkbox not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to perform uncheck action.
	 * 
	 * @param strLogicalName
	 * @param checkBox
	 * @return
	 * @throws CheckBoxElementException
	 */
	public static boolean unCheck(String strLogicalName, WebElement checkBox)
			throws CheckBoxElementException {
		boolean blResult = false;
		
		try {
			if (checkBox.isDisplayed()) {
				if (isEnabled(strLogicalName, checkBox)) {
					if (checkBox.isSelected()) {
						checkBox.click();
						Log.debug(strLogicalName + " checkbox unchecked");
					}
					blResult = true;
				}
			}
		} catch (Exception e) {
			Log.error(strLogicalName + " Checkbox not found");
			throw new CheckBoxElementException(strLogicalName
					+ "--> Checkbox not found");
		}
		
		return blResult;
	}
	
	/**
	 * This Method is used to perform check action.
	 * 
	 * @param strLogicalName
	 * @param checkBox
	 * @return boolean
	 * @throws CheckBoxElementException
	 */
	public static boolean checkByJS(String strLogicalName, WebElement checkBox,
			WebDriver driver) throws CheckBoxElementException {
		
		boolean blResult = false;

		try {
			if (checkBox.isDisplayed()) {
				if (isEnabled(strLogicalName, checkBox)) {
					if (!checkBox.isSelected()) {
						CommonHelper.clickbyJS(driver, checkBox);
						Log.debug(strLogicalName + " Checkbox selected");
					}
					blResult = true;
					Log.debug(strLogicalName + " Checkbox has been selected");
				} else {
					Log.error(strLogicalName + " Checkbox is not enabled");
				}
			} else {
				Log.error(strLogicalName + " Checkbox does not exist");
			}
		} catch (Exception e) {
			Log.fatal(strLogicalName + " Checkbox not found");
			throw new CheckBoxElementException(strLogicalName
					+ "--> Checkbox not found");
		}

		return blResult;
	}
}
