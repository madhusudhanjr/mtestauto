package com.itc.utilities.elementfactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.itc.framework.loggers.Log;
import com.itc.framework.reporters.Resulter;
import com.itc.utilities.exceptions.ComboBoxElementException;

/**
 * ComboBoxElement class consists of all actions related to ComboBox.
 * 
 * @author Madhusudhan
 */
public class ComboBoxElement {

	/**
	 * This Method is used to check for element is displayed.
	 * 
	 * @param strLogicalName
	 * @param adbComboElement
	 * @return
	 * @throws ComboBoxElementException
	 */
	public static boolean isDisplayed(String strLogicalName,
			WebElement adbComboElement) throws ComboBoxElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (adbComboElement != null) {
				if (adbComboElement.isDisplayed()) {
					blResult = true;
					// arrResult=
					// Arrays.asList("\""+strLogicalName+"\"","Combo Box is Displayed",
					// "0");
				} else {
					blResult = false;
					arrResult = Arrays.asList("\"" + strLogicalName + "\"",
							"Combo Box is not displayed", "1");
				}
			} else {
				blResult = false;
				arrResult = Arrays.asList("\"" + strLogicalName + "\"",
						"Combo Box does not exist", "1");
				// throw new Exception(strLogicalName
				// +"--> Combo Box does not exist");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					"Combo Box does not exist", "1");
			throw new ComboBoxElementException(strLogicalName
					+ "--> Combo Box does not exist");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to check for element is enabled.
	 * 
	 * @param strLogicalName
	 * @param adbComboElement
	 * @return boolean
	 * @throws ComboBoxElementException
	 */
	public static boolean isEnabled(String strLogicalName,
			WebElement adbComboElement) throws ComboBoxElementException {
		boolean blResult = false;
		try {
			if (adbComboElement != null) {
				if (adbComboElement.isEnabled()) {
					blResult = true;
					Log.debug("\"" + strLogicalName + "\""
							+ "Combo Box is enabled");
				} else {
					Log.error("\"" + strLogicalName + "\""
							+ "Combo Box is not enabled");
				}
			} else {
				blResult = false;
				Log.error("\"" + strLogicalName + "\""
						+ "Combo Box does not exist");
			}
		} catch (Exception e) {
			Log.error("\"" + strLogicalName + "\"" + "Combo Box does not exist");
			throw new ComboBoxElementException(strLogicalName
					+ "--> Combo Box does not exist");
		}
		return blResult;
	}

	/**
	 * This Method is used to perform select action for element.
	 * 
	 * @param strLogicalName
	 * @param adbComboElement
	 * @param itemToBeSelected
	 * @return boolean
	 * @throws ComboBoxElementException
	 */
	public static boolean aSelect(String strLogicalName,
			WebElement adbComboElement, String itemToBeSelected)
			throws ComboBoxElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (isDisplayed(strLogicalName, adbComboElement)) {
				if (isEnabled(strLogicalName, adbComboElement)) {
					adbComboElement.click();
					java.util.List<WebElement> options = new ArrayList<WebElement>();
					options = adbComboElement.findElements(By
							.className("ddMenuOption"));
					boolean itemCheck = false;
					for (WebElement webElement : options) {
						String elementText = webElement.findElement(
								By.xpath("a")).getAttribute("rel");
						if (elementText.equalsIgnoreCase(itemToBeSelected)) {
							webElement.click();
							blResult = true;
							itemCheck = true;
							// arrResult=Arrays.asList(strLogicalName,"Item has been selected",
							// "0");
							break;
						}
					}
					if (itemCheck == false) {
						blResult = false;
						arrResult = Arrays.asList(strLogicalName,
								" Item not present in list", "1");
						// throw new Exception(strLogicalName
						// +"--> Item not present in list");
					}
				}
				// else {
				// arrResult=
				// Arrays.asList(strLogicalName,"Combo Box is not enabled",
				// "1");
				// }
			}
			// else{
			// blResult=false;
			// arrResult=
			// Arrays.asList(strLogicalName,"Combo Box is not displayed", "1");
			// }
		} catch (Exception e) {
			arrResult = Arrays.asList(strLogicalName,
					" Item not present in list", "1");
			throw new ComboBoxElementException(strLogicalName
					+ "--> Item not present in list");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to select All available options from Multiselect
	 * ComboBox.
	 * 
	 * @param strLogicalName
	 * @param adbComboElement
	 * @return boolean
	 * @throws ComboBoxElementException
	 */
	public static boolean selectAllOptions(String strLogicalName,
			WebElement adbComboElement) throws ComboBoxElementException {

		boolean blResult = false;
		try {
			if (isDisplayed(strLogicalName, adbComboElement)) {
				if (isEnabled(strLogicalName, adbComboElement)) {
					try {
						// Retrieving all the visible Options from the ComboBox
						// Element
						List<String> visibleOptions = getOptions(
								strLogicalName, adbComboElement);

						if (visibleOptions.size() < 1) {

							Log.debug("In \""
									+ strLogicalName
									+ "\" combo Box, No options are present to select.");

							return false;
						} else {

							for (String selectedOption : visibleOptions) {
								new Select(adbComboElement)
										.selectByVisibleText(selectedOption);

								blResult = true;
								Log.debug("In \"" + strLogicalName
										+ "\" combo Box, Item ("
										+ selectedOption
										+ ") has been selected");
							}
						}

					} catch (Exception e) {
						Log.error("In \"" + strLogicalName
								+ "\" combo Box, First Item cannot be selected");
					}
				} else {
					Log.error("In \"" + strLogicalName
							+ "\" Combo box is not enabled");
				}
			} else {
				blResult = false;
				Log.error("In \"" + strLogicalName
						+ "\" Combo box is not displayed");
			}
		} catch (Exception e) {
			Log.error("In \"" + strLogicalName + "\" combo Box, "
					+ " First Item cannot be selected");
			throw new ComboBoxElementException("--> Item not present in list");
		}
		return blResult;
	}

	/**
	 * This Method is used to select first available option.
	 * 
	 * @param strLogicalName
	 * @param adbComboElement
	 * @return boolean
	 * @throws ComboBoxElementException
	 */
	public static boolean selectFirstOption(String strLogicalName,
			WebElement adbComboElement) throws ComboBoxElementException {

		boolean blResult = false;
		try {
			if (isDisplayed(strLogicalName, adbComboElement)) {
				if (isEnabled(strLogicalName, adbComboElement)) {
					try {
						// Retrieving all the visible Options from the ComboBox
						// Element
						List<String> visibleOptions = getOptions(
								strLogicalName, adbComboElement);

						if (visibleOptions.size() < 2) {

							Log.debug("In \""
									+ strLogicalName
									+ "\" combo Box, No options are present to select.");

							return false;
						} else {

							String selectedOption = visibleOptions.get(1)
									.toString();
							new Select(adbComboElement)
									.selectByVisibleText(selectedOption);

							blResult = true;
							Log.debug("In \"" + strLogicalName
									+ "\" combo Box, Item (" + selectedOption
									+ ") has been selected");
						}

					} catch (Exception e) {
						Log.error("In \"" + strLogicalName
								+ "\" combo Box, First Item cannot be selected");
					}
				} else {
					Log.error("In \"" + strLogicalName
							+ "\" Combo box is not enabled");
				}
			} else {
				blResult = false;
				Log.error("In \"" + strLogicalName
						+ "\" Combo box is not displayed");
			}
		} catch (Exception e) {
			Log.error("In \"" + strLogicalName + "\" combo Box, "
					+ " First Item cannot be selected");
			throw new ComboBoxElementException("--> Item not present in list");
		}
		return blResult;
	}

	/**
	 * This Method is used to select last available option that contain the
	 * passed String.
	 * 
	 * @param strLogicalName
	 * @param adbComboElement
	 * @return boolean
	 * @throws ComboBoxElementException
	 */
	public static boolean selectLastMatchingOption(String strLogicalName,
			WebElement adbComboElement, String itemToBeSelected)
			throws ComboBoxElementException {

		boolean blResult = false;
		try {
			if (isDisplayed(strLogicalName, adbComboElement)) {
				if (isEnabled(strLogicalName, adbComboElement)) {
					try {
						// Retrieving all the visible Options from the ComboBox
						// Element
						List<String> visibleOptions = getOptions(
								strLogicalName, adbComboElement);

						for (int i = visibleOptions.size() - 1; i > -1; i--) {

							String option = visibleOptions.get(i);

							if (option.contains(itemToBeSelected)) {

								new Select(adbComboElement).selectByIndex(i);
								blResult = true;
								Log.debug("In \"" + strLogicalName
										+ "\" combo Box, Item (" + option
										+ ") has been selected");
								break;
							}

						}

						if (!blResult) {

							Log.warn("In \""
									+ strLogicalName
									+ "\" combo Box, "
									+ itemToBeSelected
									+ "--> Item not matched with any Item in list");

						}

					} catch (Exception e) {
						Log.error("In \"" + strLogicalName
								+ "\" combo Box, Item (" + itemToBeSelected
								+ ") cannot be selected\n" + e.getMessage());
					}
				} else {
					Log.error("In \"" + strLogicalName
							+ "\" Combo box is not enabled");
				}
			} else {
				blResult = false;
				Log.error("In \"" + strLogicalName
						+ "\" Combo box is not displayed");
			}
		} catch (Exception e) {
			Log.error("In \"" + strLogicalName + "\" combo Box, " + " Item ("
					+ itemToBeSelected + ") cannot be selected");
			throw new ComboBoxElementException(itemToBeSelected
					+ "--> Item not present in list");
		}
		return blResult;
	}

	/**
	 * This Method is used to select first available option that contain the
	 * passed String.
	 * 
	 * @param strLogicalName
	 * @param adbComboElement
	 * @return boolean
	 * @throws ComboBoxElementException
	 */
	public static boolean selectFirstMatchingOption(String strLogicalName,
			WebElement adbComboElement, String itemToBeSelected)
			throws ComboBoxElementException {

		boolean blResult = false;
		try {
			if (isDisplayed(strLogicalName, adbComboElement)) {
				if (isEnabled(strLogicalName, adbComboElement)) {
					try {
						// Retrieving all the visible Options from the ComboBox
						// Element
						List<String> visibleOptions = getOptions(
								strLogicalName, adbComboElement);

						for (int i = 0; i < visibleOptions.size(); i++) {

							String option = visibleOptions.get(i);

							if (option.contains(itemToBeSelected)) {

								new Select(adbComboElement).selectByIndex(i);
								blResult = true;
								Log.debug("In \"" + strLogicalName
										+ "\" combo Box, Item (" + option
										+ ") has been selected");
								break;
							}

						}

						if (!blResult) {

							Log.warn("In \""
									+ strLogicalName
									+ "\" combo Box, "
									+ itemToBeSelected
									+ "--> Item not matched with any Item in list");

						}

					} catch (Exception e) {
						Log.error("In \"" + strLogicalName
								+ "\" combo Box, Item (" + itemToBeSelected
								+ ") cannot be selected\n" + e.getMessage());
					}
				} else {
					Log.error("In \"" + strLogicalName
							+ "\" Combo box is not enabled");
				}
			} else {
				blResult = false;
				Log.error("In \"" + strLogicalName
						+ "\" Combo box is not displayed");
			}
		} catch (Exception e) {
			Log.error("In \"" + strLogicalName + "\" combo Box, " + " Item ("
					+ itemToBeSelected + ") cannot be selected");
			throw new ComboBoxElementException(itemToBeSelected
					+ "--> Item not present in list");
		}
		return blResult;
	}

	/**
	 * This Method is used to select with visible text for element.
	 * 
	 * @param strLogicalName
	 * @param adbComboElement
	 * @param itemToBeSelected
	 * @return
	 * @throws ComboBoxElementException
	 */
	public static boolean select(String strLogicalName,
			WebElement adbComboElement, String itemToBeSelected)
			throws ComboBoxElementException {

		boolean blResult = false;
		try {
			if (isDisplayed(strLogicalName, adbComboElement)) {
				if (isEnabled(strLogicalName, adbComboElement)) {
					try {
						// Retrieving all the visible Options from the ComboBox
						// Element
						List<String> visibleOptions = getOptions(
								strLogicalName, adbComboElement);

						for (String option : visibleOptions) {

							if (option.contains(itemToBeSelected)) {
								if (option.startsWith(itemToBeSelected)) {
									// Selecting Combo which matches with the
									// itemToBeSelected text
									new Select(adbComboElement)
											.selectByVisibleText(option);
									blResult = true;
									Log.debug("In \"" + strLogicalName
											+ "\" combo Box, Item (" + option
											+ ") has been selected");
									break;
								}
							}

						}

						if (!blResult) {

							Log.warn("In \"" + strLogicalName
									+ "\" combo Box, " + itemToBeSelected
									+ "--> Item not present in list");

						}

					} catch (Exception e) {
						Log.error("In \"" + strLogicalName
								+ "\" combo Box, Item (" + itemToBeSelected
								+ ") cannot be selected");
					}
				} else {
					Log.error("In \"" + strLogicalName
							+ "\" Combo box is not enabled");
				}
			} else {
				blResult = false;
				Log.error("In \"" + strLogicalName
						+ "\" Combo box is not displayed");
			}
		} catch (Exception e) {
			Log.error("In \"" + strLogicalName + "\" combo Box, " + " Item ("
					+ itemToBeSelected + ") cannot be selected");
			throw new ComboBoxElementException(itemToBeSelected
					+ "--> Item not present in list");
		}
		return blResult;
	}

	/**
	 * This Method is used to perform undo select for element.
	 * 
	 * @param strLogicalName
	 * @param adbComboElement
	 * @return boolean
	 * @throws ComboBoxElementException
	 */
	public static boolean deSelect(String strLogicalName,
			WebElement adbComboElement) throws ComboBoxElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (isDisplayed(strLogicalName, adbComboElement)) {
				if (isEnabled(strLogicalName, adbComboElement)) {
					WebElement selectedElement = new Select(adbComboElement)
							.getFirstSelectedOption();
					new Select(adbComboElement)
							.deselectByVisibleText(selectedElement.getText());
					blResult = true;
					// arrResult=
					// Arrays.asList("\""+strLogicalName+"\""," Item has been de-selected",
					// "0");
				}
				// else {
				// arrResult=
				// Arrays.asList(strLogicalName," Combo box is not enabled",
				// "0");
				// }
			}
			// else{
			// blResult=false;
			// arrResult=
			// Arrays.asList("\""+strLogicalName+"\""," Combo box is not displayed",
			// "1");
			// }
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					"Combo Box does not exist", "1");
			throw new ComboBoxElementException(strLogicalName
					+ "--> Item not present in list");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to getOptions for element.
	 * 
	 * @param strLogicalName
	 * @param adbComboElement
	 * @param indexToBeSelected
	 * @return boolean
	 * @throws ComboBoxElementException
	 */
	public static List<String> getOptions(String strLogicalName,
			WebElement adbComboElement) throws ComboBoxElementException {

		Log.debug("Get ComboBox Options");

		List<String> optionVisibleText = new ArrayList<String>();

		try {

			if (isDisplayed(strLogicalName, adbComboElement)) {
				if (isEnabled(strLogicalName, adbComboElement)) {
					List<WebElement> optionsList = new Select(adbComboElement)
							.getOptions();
					if (optionsList.isEmpty()) {
						Log.debug(strLogicalName + "No options found");
					} else {
						for (WebElement element : optionsList) {
							optionVisibleText.add(element.getText());
						}
						Log.debug(strLogicalName + "Options are: "
								+ optionVisibleText);
					}
				}
			}

		} catch (Exception e) {
			Log.error("\"" + strLogicalName + "\"" + "Combo Box does not exist"
					+ "1");
			throw new ComboBoxElementException(strLogicalName
					+ "--> Combo Box does not exist");
		}

		return optionVisibleText;
	}

	/**
	 * This Method is used to perform get select value for element.
	 * 
	 * @param strLogicalName
	 * @param adbComboElement
	 * @return
	 * @throws ComboBoxElementException
	 */
	public static String getSelectedValue(String strLogicalName,
			WebElement adbComboElement) throws ComboBoxElementException {

		String selectedText = "";

		try {
			if (isDisplayed(strLogicalName, adbComboElement)) {

				if (!isEnabled(strLogicalName, adbComboElement))
					Log.warn("\"" + strLogicalName + "\"" + " is not enabled");

				WebElement selectedElement = new Select(adbComboElement)
						.getFirstSelectedOption();

				selectedText = selectedElement.getText();
				Log.debug("\"" + strLogicalName + "\"" + " Selected text is "
						+ selectedText);
			} else

				Log.error("\"" + strLogicalName + "\""
						+ " Combo box is not displayed");

		} catch (Exception e) {

			Log.error("\"" + strLogicalName + "\"" + "Combo Box does not exist");
			throw new ComboBoxElementException(strLogicalName
					+ "--> Combo Box does not exist");
		}

		return selectedText;
	}

	/**
	 * This Method is used to perform get select for element.
	 * 
	 * @param strLogicalName
	 * @param adbComboElement
	 * @return
	 * @throws ComboBoxElementException
	 */
	public static String getSelected(String strLogicalName,
			WebElement adbComboElement) throws ComboBoxElementException {

		String selectedText = "";

		try {
			if (isDisplayed(strLogicalName, adbComboElement)) {

				WebElement selectedElement = new Select(adbComboElement)
						.getFirstSelectedOption();

				if (isEnabled(strLogicalName, adbComboElement)) {
					selectedText = selectedElement.getText();
					Log.debug("\"" + strLogicalName + "\""
							+ " Selected text is " + selectedText);
				} else {
					if (null != selectedElement.getAttribute("title")
							&& !selectedElement.getAttribute("title").isEmpty()) {
						selectedText = selectedElement.getText();
						Log.debug("\"" + strLogicalName + "\""
								+ " Selected text is " + selectedText);
					}
				}

			}

			Log.error("\"" + strLogicalName + "\""
					+ " Combo box is not displayed");

		} catch (Exception e) {

			Log.error("\"" + strLogicalName + "\"" + "Combo Box does not exist");
			throw new ComboBoxElementException(strLogicalName
					+ "--> Combo Box does not exist");
		}

		return selectedText;
	}

	/**
	 * This Method is used to perform select action for element.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param objectLocator
	 * @param itemToBeSelected
	 * @return boolean
	 * @throws ComboBoxElementException
	 */
	public static boolean select(String strLogicalName, WebDriver driver,
			By objectLocator, String itemToBeSelected)
			throws ComboBoxElementException {
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
						// try{
						new Select(element)
								.selectByVisibleText(itemToBeSelected);
						blResult = true;
						// arrResult=
						// Arrays.asList("\""+strLogicalName+"\""," Item has been selected",
						// "0");
						// }
						// catch(Exception e)
						// {
						// arrResult=
						// Arrays.asList("\""+strLogicalName+"\""," Item could not be selected",
						// "1");
						// }

					}
					// else{
					// arrResult=
					// Arrays.asList("\""+strLogicalName+"\""," Combo box is not enabled",
					// "1");
					// }
				}
				// else{
				// arrResult=
				// Arrays.asList("\""+strLogicalName+"\""," Combo box is not displayed",
				// "1");
				// }
			} else {
				arrResult = Arrays.asList("\"" + strLogicalName + "\"",
						" Combo box is not found", "1");
				// throw new Exception(strLogicalName +
				// "--> Combo Box does not exist");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					" Item could not be selected", "1");
			throw new ComboBoxElementException(strLogicalName
					+ "--> Item could not be selected");
		}
		Resulter.log(arrResult);
		return blResult;
	}

	/**
	 * This Method is used to perform select action for element.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param element
	 * @param itemToBeSelected
	 * @return boolean
	 * @throws ComboBoxElementException
	 */
	public static boolean select(String strLogicalName, WebDriver driver,
			WebElement element, String itemToBeSelected)
			throws ComboBoxElementException {
		boolean blResult = false;
		List<String> arrResult = null;
		try {
			if (null != element) {
				if (isDisplayed(strLogicalName, element)) {
					if (isEnabled(strLogicalName, element)) {
						// try{
						new Select(element)
								.selectByVisibleText(itemToBeSelected);
						blResult = true;
						arrResult = Arrays.asList("\"" + strLogicalName + "\"",
								" Item has been selected", "0");
						// }
						// catch(Exception e)
						// {
						// arrResult=
						// Arrays.asList("\""+strLogicalName+"\""," Item could not be selected",
						// "1");
						// }
					}
					// else{
					// arrResult=
					// Arrays.asList("\""+strLogicalName+"\""," Combo box is not enabled",
					// "1");
					// }
				}
				// else{
				// arrResult=
				// Arrays.asList("\""+strLogicalName+"\""," Combo box is not displayed",
				// "1");
				// }
			} else {
				arrResult = Arrays.asList("\"" + strLogicalName + "\"",
						" Combo box is not found", "1");
				// throw new Exception(strLogicalName +
				// "--> Combo box is not found");
			}
		} catch (Exception e) {
			arrResult = Arrays.asList("\"" + strLogicalName + "\"",
					" Combo box is not found", "1");
			throw new ComboBoxElementException(strLogicalName
					+ "--> Combo box is not found");
		}
		Resulter.log(arrResult);
		return blResult;
	}
}