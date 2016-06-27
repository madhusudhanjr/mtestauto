package com.itc.utilities.helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.itc.framework.helpers.FrameworkConstants;
import com.itc.framework.loggers.Log;
import com.itc.utilities.elementfactory.ButtonElement;
import com.itc.utilities.elementfactory.Element;
import com.itc.utilities.exceptions.ButtonElementException;
import com.itc.utilities.exceptions.DataSourceException;
import com.itc.utilities.exceptions.ElementException;

/**
 * CommonHelper consists of generic custom actions used across the project.
 * 
 * @author ITC Infotech
 */
public class CommonHelper {

	/****************************************************
	 * Handling Alert in the UI
	 * ********************************************************/
	public static boolean handleAlert(WebDriver driver) {
		Wait<WebDriver> wait = fluentWait(driver, 5);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
			Log.debug("***Alert accepted***");
			return true;
		} catch (TimeoutException e) {
			Log.warn("***Alert Not Found***");
			return false;
		}
	}

	/****************************************************
	 * Handling Alert in the UI
	 * ********************************************************/
	public static void dismissAlert(WebDriver driver) {
		Wait<WebDriver> wait = fluentWait(driver, 2);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			Log.debug("***Alert accepted***");
		} catch (TimeoutException e) {
			Log.warn("***Alert Not Found***");
		}
	}

	/**
	 * This method performs keyboard events. It clicks Ctl+Alt+p buttons on the
	 * keyboard. This stimulates the print function in the application.
	 * <p>
	 * Unicode value of p is '0070'.
	 * <p>
	 * <p>
	 * For Unicode values please refer
	 * http://unicode-table.com/en/#control-character
	 * <p>
	 * 
	 * @param driver
	 */
	public static void print(WebDriver driver) {

		try {

			Log.debug("Clicking print(ctl+alt+p)");
			Actions action = new Actions(driver);
			action.keyDown(Keys.CONTROL).keyDown(Keys.ALT)
					.sendKeys(String.valueOf('\u0070')).perform();
		} catch (Exception e) {
			Log.error("Exception occured while printing" + e.getMessage());
		}
	}

	/**
	 * This method performs keyboard events. It clicks Clt+Alt+c buttons on the
	 * keyboard. This stimulates the copy function in the application.
	 * <p>
	 * Unicode value of c is '0063'.
	 * <p>
	 * <p>
	 * For Unicode values please refer
	 * http://unicode-table.com/en/#control-character
	 * <p>
	 * 
	 * @param driver
	 */
	public static void copy(WebDriver driver) {

		try {

			Log.debug("Clicking copy(ctl+alt+c)");
			Actions action = new Actions(driver);
			action.keyDown(Keys.CONTROL).keyDown(Keys.ALT)
					.sendKeys(String.valueOf('\u0063')).perform();
		} catch (Exception e) {
			Log.error("Exception occured while copying" + e.getMessage());
		}
	}

	/**
	 * This method performs keyboard events. It clicks Clt+Alt+m buttons on the
	 * keyboard. This stimulates the amend function in the application.
	 * <p>
	 * Unicode value of m is '006D'.
	 * <p>
	 * <p>
	 * For Unicode values please refer
	 * http://unicode-table.com/en/#control-character
	 * <p>
	 * 
	 * @param driver
	 */
	public static void amend(WebDriver driver) {

		try {

			Log.debug("Clicking amend(ctl+alt+m)");
			Actions action = new Actions(driver);
			action.keyDown(Keys.CONTROL).keyDown(Keys.ALT)
					.sendKeys(String.valueOf('\u006D')).perform();
		} catch (Exception e) {
			Log.error("Exception occured while amending" + e.getMessage());
		}
	}

	/**
	 * This method performs keyboard events. It clicks Clt+Alt+x buttons on the
	 * keyboard. This stimulates the cancel function in the application.
	 * <p>
	 * Unicode value of x is '0078'.
	 * <p>
	 * <p>
	 * For Unicode values please refer
	 * http://unicode-table.com/en/#control-character
	 * <p>
	 * 
	 * @param driver
	 */
	public static void cancel(WebDriver driver) {

		try {

			Log.debug("Clicking cancel(ctl+alt+x)");
			Actions action = new Actions(driver);
			action.keyDown(Keys.CONTROL).keyDown(Keys.ALT)
					.sendKeys(String.valueOf('\u0078')).perform();
		} catch (Exception e) {
			Log.error("Exception occured while canceling" + e.getMessage());
		}
	}

	/**
	 * This method performs keyboard events. It clicks Clt+Alt+c buttons on the
	 * keyboard. This stimulates the copy function in the application.
	 * <p>
	 * Unicode value of u is '0075'.
	 * <p>
	 * <p>
	 * For Unicode values please refer
	 * http://unicode-table.com/en/#control-character
	 * <p>
	 * 
	 * @param driver
	 */
	public static void update(WebDriver driver) {

		try {

			Log.debug("Clicking update(ctl+alt+u)");
			Actions action = new Actions(driver);
			action.keyDown(Keys.CONTROL).keyDown(Keys.ALT)
					.sendKeys(String.valueOf('\u0075')).perform();
		} catch (Exception e) {
			Log.error("Exception occured while updating" + e.getMessage());
		}
	}

	/**
	 * This method checks for the alert to be present.
	 * 
	 * @param driver
	 * @return
	 */
	public static boolean isAlertPresent(WebDriver driver) {

		Log.debug("Check For Alert Existence");

		try {
			driver.switchTo().alert();
			Log.debug("***Alert Exists***");
			return true;
		} catch (NoAlertPresentException e) {
			Log.debug("***Alert Not Found***" + e.getMessage());
			return false;
		}
	}

	/**
	 * This method is used to wait explicitly in seconds
	 * 
	 * @param seconds
	 */
	public static void waitInSeconds(int seconds) {

		Log.debug(" waitInSeconds(): " + seconds + " seconds");

		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			Log.debug(e.getMessage());
		}

	}



	public static String getGridHubUrl() {
		String gridHubUrl = null;
		if (null != System.getProperty("Specify grid hub URL")) {
			gridHubUrl = System.getProperty("Specify grid hub URL");
		}
		return gridHubUrl;
	}

	public static int getRandomNumber() throws DataSourceException {
		return new Random().nextInt();
	}

	// public static boolean validMachineToUploadResult() {
	public static boolean isLiveMachineToUploadXMLResults() {
		Log.debug("==>isLiveMachineToUploadXMLResults");
		if (null == System.getProperty("result.upload")
				|| !System.getProperty("result.upload")
						.equalsIgnoreCase("Live")) {
			Log.debug("<==isLiveMachineToUploadXMLResults");
			return false;
		} else {
			Log.debug("<==isLiveMachineToUploadXMLResults");
			return true;
		}
	}

	public static boolean isStageOrProdEnvironment() {
		Log.debug("==>removeDelimiter");
		if ("stage".equals(System.getProperty("environment"))
				|| "stage2".equals(System.getProperty("environment"))
				|| "production".equals(System.getProperty("environment"))) {
			Log.debug("<==removeDelimiter");
			return true;
		} else {
			Log.debug("<==removeDelimiter");
			return false;
		}
	}

	public static String removeDelimiter(String price) {
		Log.debug("==>removeDelimiter");
		int length = price.length();
		String newPrice = "";
		for (int i = 0; i < length; i++) {
			Character character = price.charAt(i);
			if (character.toString().equals(".")
					|| (Character.isDigit(character))) {
				newPrice += character;
			}
		}
		Log.debug("<==removeDelimiter");
		return newPrice;
	}

	public static String getCurrencyFormat(String price) {
		Log.debug("==>getCurrencyFormat");
		int length = price.length();
		String newPrice = "";
		for (int i = 0; i < length; i++) {
			Character character = price.charAt(i);
			if (!character.toString().equals(".")
					&& !character.toString().equals(",")
					&& (!Character.isDigit(character))) {
				newPrice += character;
			}
		}
		Log.debug("<==getCurrencyFormat");
		return newPrice;
	}

	public static LinkedHashMap<String, String> getOutputFolderpath(
			String winPath, String linuxPath, String testClassName) {
		Log.debug("==>getOutputFolderpath");
		String filePath = null;
		String folderName = null;
		String executor = null;
		LinkedHashMap<String, String> folderNameAndExecutor = new LinkedHashMap<String, String>();
		try {
			String os = System.getProperty("os.name").toLowerCase();

			if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
				filePath = linuxPath;// CommonConstants.LINUX_EXEC_FILE_PATH;
				// urlPath = CommonConstants.LINUX_EXEC_URL_PATH;
				executor = "Jenkins";
			} else if (os.indexOf("win") >= 0) {
				// filePath = CommonConstants.WIN_EXEC_FILE_URL_PATH;
				// filePath="C:\\Selenium\\Execution";
				filePath = winPath;// CommonConstants.WIN_EXEC_FILE_URL_PATH_OTHERS;
				// urlPath = CommonConstants.WIN_EXEC_FILE_URL_PATH;
				executor = InetAddress.getLocalHost().getHostName();
			}

			folderName = new SimpleDateFormat("yyyyMMMdd").format(new Date());

		} catch (Exception e) {
			e.printStackTrace();
		}
		folderNameAndExecutor.put("Filepath", filePath + "/" + folderName);
		folderNameAndExecutor.put("executor", executor);
		Log.debug("<==getOutputFolderpath");
		return folderNameAndExecutor;
	}

	public static boolean deleteDuplicateFolder() {
		Log.debug("==>deleteDuplicateFolder");
		String duplicateFolder = "/Volumes/Automation_Results";

		File dir = new File(duplicateFolder);
		boolean isDirectory = false;
		if (dir.isDirectory()) {
			Log.debug("<==deleteDuplicateFolder");
			isDirectory = dir.delete();
		}
		Log.debug("<==deleteDuplicateFolder");

		return isDirectory;

	}

	/**
	 * This method takes the screenshot of the webpage and saves it in the
	 * output directory.
	 * 
	 * @param driver
	 * @param outputDir
	 * @param customer
	 * @param methodName
	 * @throws DataSourceException
	 * @throws IOException
	 */
	public static void takeScreenShot(WebDriver driver, String outputDir,
			String methodName) throws DataSourceException, IOException {

		try {
			String fileNameWithPath = null;
			String fileName = null;

			if (driver != null) {

				// Augment the driver only if the driver instance
				// is of type RemoteWebDriver(When tests are running in Grid)
				// if (driver.getClass() == RemoteWebDriver.class) {
				// driver = new Augmenter().augment(driver);
				// }

				File scrFile = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);

				fileName = methodName + ".jpeg";
				fileNameWithPath = outputDir + "/" + fileName;

				File scrShot = new File(fileNameWithPath);

				if (scrShot.exists()) {

					// append '_retry' to the previous screenshot in case of
					// retry.
					if (null != scrShot && scrShot.exists()) {
						String retryFileName = outputDir + "/" + methodName
								+ "_retry" + ".jpeg";
						File failedScrShot = new File(retryFileName);

						if (failedScrShot.exists())
							failedScrShot.delete();

						FileUtils.moveFile(scrShot, failedScrShot);
					}
				}

				/*
				 * if( null != scrFile && scrShot.exists() ){ scrShot.delete();
				 * }
				 */
				FileUtils.moveFile(scrFile, scrShot);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Renames the file if already present in the output directory to
	 * filename_retry.log.
	 * 
	 * @param dirPath
	 * @param methodName
	 * @throws IOException
	 */
	public static void renameRetryLog(String dirPath, String methodName)
			throws IOException {

		try {
			Log.debug("check if the file already exists wiht the same name in O/P DIR");
			File logFile = new File(dirPath + "/" + methodName + ".log");

			if (logFile.exists()) {
				Log.debug("Renaming the existing file");
				File retryLog = new File(dirPath + "/" + methodName + "_retry"
						+ ".log");

				if (retryLog.exists())
					retryLog.delete();

				FileUtils.moveFile(logFile, retryLog);

				// delete the existing file
				Log.debug("Is log file deleted:" + logFile.delete());
			}
		} catch (Exception e) {
			Log.error("ERROR: " + e);
		}
	}

	/**
	 * Dump the HTML Source to a text file
	 * 
	 * @param driver
	 * @param outputDirectory
	 * @param methodName
	 */
	public static void dumpHTMLSource(WebDriver driver, String outputDirectory,
			String methodName) {

		try {
			String fileNameWithPath = null;

			if (driver != null) {

				// Augment the driver only if the driver instance
				// is of type RemoteWebDriver(When tests are running in Grid)
				// if (driver.getClass() == RemoteWebDriver.class) {
				// driver = new Augmenter().augment(driver);
				// }

				// Create a HTML file and copy the html source to the file
				fileNameWithPath = outputDirectory + "/" + methodName + ".html";
				Log.debug("Dumping the HTML source to file " + fileNameWithPath);
				File htmlDumpFile = new File(fileNameWithPath);

				if (htmlDumpFile.exists()) {

					// append '_retry' to the previous html file in case of
					// retry.
					String retryFileName = outputDirectory + "/" + methodName
							+ "_retry" + ".html";
					File failedTestHtml = new File(retryFileName);

					if (failedTestHtml.exists())
						failedTestHtml.delete();

					if (htmlDumpFile.exists())
						FileUtils.moveFile(htmlDumpFile, failedTestHtml);
				}

				FileWriter htmlWriter = new FileWriter(htmlDumpFile);
				htmlWriter.append(driver.getPageSource());
				htmlWriter.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method writes the WebDriver log to a text file
	 * 
	 * @param webDriver
	 * 
	 * @param outputDirectory
	 * @param methodName
	 */
	public static void createWebDriverLog(WebDriver driver,
			String outputDirectory, String methodName) {

		try {
			String fileNameWithPath = null;

			if (outputDirectory != null) {

				// Create a WebDriver log file per test case wise
				fileNameWithPath = outputDirectory + "/" + methodName
						+ "_webdriver.log";
				Log.debug("Dumping the WebDriver logs per test case wise to "
						+ fileNameWithPath);
				File webdriverLog = new File(fileNameWithPath);

				if (webdriverLog.exists()) {

					// append '_retry' to the previous log file in case of
					// retry.
					String retryFileName = outputDirectory + "/" + methodName
							+ "_retry" + "_webdriver.log";
					File failedTestLog = new File(retryFileName);

					if (failedTestLog.exists())
						failedTestLog.delete();

					if (webdriverLog.exists())
						FileUtils.moveFile(webdriverLog, failedTestLog);
				}

				FileWriter webdriverLogWriter = new FileWriter(webdriverLog);
				LogEntries logEntries = driver.manage().logs()
						.get(LogType.DRIVER);
				for (LogEntry entry : logEntries) {
					webdriverLogWriter.append(new Date(entry.getTimestamp())
							+ " " + entry.getLevel() + " " + entry.getMessage()
							+ "\r\n");

				}

				webdriverLogWriter.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String getBrowserName(WebDriver driver) {
		String browserName = null;
		if (driver instanceof InternetExplorerDriver
				|| ((RemoteWebDriver) driver).getCapabilities()
						.getBrowserName().equals("internet explorer")) {
			browserName = "IE";
		} else if (driver instanceof FirefoxDriver
				|| ((RemoteWebDriver) driver).getCapabilities()
						.getBrowserName().equals("firefox")) {
			browserName = "firefox";
		} else if (driver instanceof ChromeDriver
				|| ((RemoteWebDriver) driver).getCapabilities()
						.getBrowserName().equals("chrome")) {
			browserName = "chrome";
		}

		return browserName;
	}

	// pass UI map as 1st Parameter
	public static boolean compareAddressMaps(
			LinkedHashMap<String, String> uiMap,
			LinkedHashMap<String, String> serviceMap) {

		if (uiMap.isEmpty() || uiMap == null) {
			Log.debug("UI MAP is either empty or null");
			return false;
		}
		if (serviceMap.isEmpty() || serviceMap == null) {
			Log.debug("Service MAP is either empty or null");
			return false;
		}

		ArrayList<Boolean> status = new ArrayList<Boolean>();
		Iterator<Entry<String, String>> serviceMapIterator = serviceMap
				.entrySet().iterator();
		while (serviceMapIterator.hasNext()) {
			Map.Entry<java.lang.String, java.lang.String> serviceEntry = (Map.Entry<java.lang.String, java.lang.String>) serviceMapIterator
					.next();
			Iterator<Entry<String, String>> uiIterator = uiMap.entrySet()
					.iterator();
			boolean iStatus = false;
			while (uiIterator.hasNext()) {
				Map.Entry<java.lang.String, java.lang.String> uiEntry = (Map.Entry<java.lang.String, java.lang.String>) uiIterator
						.next();
				String uiData = uiEntry.getValue();
				if (uiData.equalsIgnoreCase(serviceEntry.getValue())
						|| uiData.contains(serviceEntry.getValue())) {
					iStatus = true;
					status.add(iStatus);
					break;
				}
			}
			if (iStatus == false) {
				status.add(iStatus);
			}
		}
		for (int i = 0; i < status.size(); i++) {
			if (status.get(i) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean compareLists(LinkedHashMap<String, String> map1,
			LinkedHashMap<String, String> map2) {
		boolean status = false;
		if (!map1.isEmpty() && !map2.isEmpty()) {
			MapDifference<String, String> mapdifference = Maps.difference(map1,
					map2);
			if (mapdifference.areEqual()) {
				status = true;
			} else {
				Log.debug("====>Map difference "
						+ mapdifference.entriesDiffering());

			}
		}
		return status;
	}

	public static String getSymbolFromMap(String s) {
		String symbol = "";
		for (int i = s.indexOf('\'') + 1; i < s.length(); i++) {
			if (s.charAt(i) != '\'') {
				symbol += s.charAt(i);
			} else {
				return symbol;
			}
		}
		return symbol;
	}

	public static String getSymbolFromUI(String s) {
		String symbol = "";
		for (int i = 0; i < s.length(); i++) {
			Character character = s.charAt(i);
			if (!(character.toString().equals(".")
					|| (Character.isDigit(character)) || (character.toString()
					.equals(",")))) {
				symbol += character;
			}
		}
		return symbol;
	}

	public static String reverseString(String cost) {
		String reverseCost = "";
		for (int i = cost.length() - 1; i >= 0; i--) {
			reverseCost += cost.charAt(i);
		}
		return reverseCost;
	}

	public static String modifyNodeName(String name) {
		name = name.replaceAll(" ", "_");
		return name;
	}

	protected void clickHrefWorkaround(WebElement element, WebDriver driver) {
		String startUrl = driver.getCurrentUrl();

		element.click();

		if (startUrl.equals(driver.getCurrentUrl())) {
			element.sendKeys(Keys.ENTER);
		}

		if (startUrl.equals(driver.getCurrentUrl())) {
			String endUrl = element.getAttribute("href");
			if (null != endUrl) {
				if (!endUrl.startsWith("http")) {
					if (endUrl.startsWith("/")) {
						endUrl = startUrl.substring(0,
								startUrl.indexOf('/', 10) - 1)
								+ endUrl;
					} else {
						if (startUrl.contains("?")) {
							endUrl = startUrl.substring(0,
									startUrl.indexOf('?') - 1)
									+ endUrl;
						}
						endUrl = startUrl + endUrl;
					}

				}
				driver.get(endUrl);
			}
		}

		if (startUrl.equals(driver.getCurrentUrl())) {
			Assert.fail("Url didn't change when attempting to click on element with text: "
					+ element.getText());
		}
	}

	public boolean isTextPresent(String text, WebDriver driver) {
		return isTextPresent(text, 60, driver);
	}

	/**
	 * Used for searching a text using Page source
	 * 
	 * @param text
	 * @param maxTimeSeconds
	 * @return
	 */

	public boolean isTextPresent(String text, int maxTimeSeconds,
			WebDriver driver) {
		int loops = maxTimeSeconds * 4;
		for (int i = 0; i < loops; i++) {
			try {
				if (driver.getPageSource().contains(text)) {
					return true;
				}
				try {
					Thread.sleep(250L);
				} catch (InterruptedException e) {
					// try again;
				}
			} catch (Exception e) {
				// try again
				try {
					Thread.sleep(250L);
				} catch (InterruptedException e1) {
					// try again;
				}
			}
		}
		return false;
	}

	/**
	 * Used mainly when we don't want to wait for more than the specific time
	 * for an object to load
	 * 
	 * @param by
	 * @param maxTimeSeconds
	 * @return
	 */

	public static WebElement waitForElement(By by, int maxTimeSeconds,
			WebDriver driver) {
		if (driver instanceof FirefoxDriver
				|| ((RemoteWebDriver) driver).getCapabilities()
						.getBrowserName().equals("firefox")) {
			return waitForElementFirefox(by, maxTimeSeconds, driver);
		} else {
			return waitForElementOtherBrowsers(by, maxTimeSeconds, driver);
		}
	}

	/**
	 * Wait for the given element to appear for up to 60 seconds [during dead
	 * slow environments]
	 * 
	 * @param by
	 *            the locator for a
	 */
	public WebElement waitForElement(By by, WebDriver driver) {
		if (driver instanceof FirefoxDriver
				|| ((RemoteWebDriver) driver).getCapabilities()
						.getBrowserName().equals("firefox")) {
			return waitForElementFirefox(by, 60, driver);
		} else {
			return waitForElementOtherBrowsers(by, 60, driver);
		}
	}

	/**
	 * Wait for the given element to appear
	 * 
	 * @param by
	 *            the locator for a
	 */
	public static WebElement waitForElementOtherBrowsers(By by,
			int maxTimeSeconds, WebDriver driver) {
		int loops = maxTimeSeconds * 4;
		for (int i = 0; i < loops; i++) {
			try {
				// List<WebElement> elements = driver.findElements(by);
				List<WebElement> elements = Element.getElements("Wait For",
						driver, by, false);

				if (elements != null) {
					for (WebElement element : elements) {
						seleniumHiddenElementWorkaround(element, driver);
						// if (element.isDisplayed()){
						if (isElementDisplayed(element)) {
							return element;
						}
					}
				}
				try {
					Thread.sleep(250L);
				} catch (InterruptedException e) {
					// try again;
				}
			} catch (Exception e) {
				// try again
				e.printStackTrace();
				try {
					Thread.sleep(250L);
				} catch (InterruptedException e1) {
					// try again;
				}
			}
		}
		return null;
	}

	public static WebElement waitForElementFirefox(final By by, long seconds,
			final WebDriver driver) {
		// Temporary solution for Selenium bug -
		// http://code.google.com/p/selenium/issues/detail?id=2501
		WebElement myDynamicElement = null;

		try {
			driver.switchTo().defaultContent();
			final List<WebElement> elements = driver.findElements(by);
			myDynamicElement = (new WebDriverWait(driver, seconds))
					.until(new ExpectedCondition<WebElement>() {
						public WebElement apply(WebDriver d) {
							WebElement element1 = null;
							for (WebElement element : elements) {
								seleniumHiddenElementWorkaround(element, driver);
								driver.switchTo().defaultContent();
								if (element.isDisplayed()) {
									element1 = element;
								}
							}
							return element1;
						}
					});
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return myDynamicElement;
	}

	private static void seleniumHiddenElementWorkaround(WebElement element,
			WebDriver driver) {
		// js hack for selenium not interacting with elements with 0px height
		if (!"".equals(element.getAttribute("id"))
				&& "0px".equals(element.getCssValue("height"))) {
			((JavascriptExecutor) driver)
					.executeScript("document.getElementById(\""
							+ element.getAttribute("id")
							+ "\").style.height = \"1px\";");
		}
	}

	/**
	 * Wait for the given element to appear for up to 30 seconds
	 * 
	 * @param by
	 *            the locator for a
	 */
	/*
	 * public WebElement waitForElementInside(String childElementLogName,
	 * WebElement parent, By by, WebDriver driver) { return
	 * waitForElementInside(childElementLogName, parent, by, 30, driver); }
	 */

	/**
	 * Wait for the given element to appear
	 * 
	 * @param by
	 *            the locator for a
	 */
	/*
	 * public WebElement waitForElementInside(String childElementLogicalName,
	 * WebElement parent, By by, int maxTimeSeconds, WebDriver driver) { int
	 * loops = maxTimeSeconds * 4; for (int i = 0; i < loops; i++) { try { //
	 * WebElement element = parent.findElement(by); WebElement element =
	 * Element.getChildElement( "Wait For Element: \"" + childElementLogicalName
	 * + "\" ", driver, parent, by); seleniumHiddenElementWorkaround(element,
	 * driver); // if (element.isDisplayed()){ if (isElementDisplayed(element))
	 * { return element; } try { Thread.sleep(250L); } catch
	 * (InterruptedException e) { // try again; } } catch (Exception e) { // try
	 * again try { Thread.sleep(250L); } catch (InterruptedException e1) { //
	 * try again; } } } return null; }
	 */
	protected boolean isImageLoaded(WebElement img, WebDriver driver) {
		return (Boolean) ((JavascriptExecutor) driver).executeScript(
				"return arguments[0].complete", img);
	}

	protected static boolean isElementDisplayed(WebElement element) {
		// return (element != null && element.isDisplayed());
		return (element != null && element.isDisplayed());
		// return (Element.isDisplayed(locatorString,element));
	}

	protected boolean isElementEnabled(WebElement element) {
		// return (Element.isEnabled(locatorString, element));
		if (isElementDisplayed(element))
			return element.isEnabled();
		return false;
	}

	protected void hover(String id, WebDriver driver) {
		// TODO: Replace this when selenium implements this
		((JavascriptExecutor) driver).executeScript("$('#" + id
				+ "').mouseover();");
	}

	/**
	 * Wait for the given element to disappear for up to 30 seconds
	 * 
	 * @param by
	 *            the locator for a
	 */
	public void waitForElementToDisappear(WebElement element) {
		waitForElementToDisappear(element, 30);
	}

	/**
	 * Wait for the given element to disappear for up to 30 seconds
	 * 
	 * @param by
	 *            the locator for a
	 */
	public void waitForElementToDisappear(By by, WebDriver driver) {
		waitForElementToDisappear(by, 30, driver);
	}

	/**
	 * Wait for the given element to disappear
	 * 
	 * @param by
	 *            the locator for a
	 */
	public void waitForElementToDisappear(By by, int maxTimeSeconds,
			WebDriver driver) {
		int loops = maxTimeSeconds * 4;
		for (int i = 0; i < loops; i++) {
			try {
				WebElement element = null;
				List<WebElement> elements = driver.findElements(by);
				for (WebElement e : elements) {
					if (e.isDisplayed()) {
						element = e;
					}
				}
				if (!element.isDisplayed()) {
					return;
				}
				try {
					Thread.sleep(250L);
				} catch (InterruptedException e) {
					// try again;
				}
			} catch (Exception e) {
				return;
			}
		}
	}

	/**
	 * Wait for the given element to disappear
	 * 
	 * @param by
	 *            the locator for a
	 */
	public void waitForElementToDisappear(WebElement element, int maxTimeSeconds) {
		int loops = maxTimeSeconds * 4;
		for (int i = 0; i < loops; i++) {
			try {
				if (!element.isDisplayed()) {
					return;
				}
				try {
					Thread.sleep(250L);
				} catch (InterruptedException e) {
					// try again;
				}
			} catch (Exception e) {
				return;
			}
		}
	}

	/**
	 * This Method is used to mouse Hover to the element passed by scrolling
	 * into the view by JavascriptExecutor.
	 * 
	 * @param driver
	 * @param element
	 * @throws ElementException
	 */
	public static void mouseHover(WebDriver driver, WebElement element)
			throws ElementException {

		Log.debug("Mouse Hover Web Element");

		try {

			if (null != element) {

				Log.debug("Element for mouse hovering is:" + element.getText());

				// Waits for 10 seconds to check if the element is visible.
				WebDriverWait wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.visibilityOf(element));

				if (element.isDisplayed()) {

					/*
					 * // Madhu: Commenting as Selenium 2.39 was able to
					 * MosueHover without help of JS (Issue Exists with Selenium
					 * 2.33 to 2.37) Scrolling into View by JavascriptExecutor
					 * CommonHelper.scrollIntoViewByJS(driver, element);
					 */

					// MouseHovering to the Target element
					Actions builder = new Actions(driver);
					Action mouseOver = builder.moveToElement(element).build();
					mouseOver.perform();

				} else {
					Log.error("Target Webelement is not displayed");
					throw new ElementException(
							"Target Webelement is not displayed");
				}

			} else {
				Log.error("Target Webelement is null");
				throw new ElementException("Target Webelement is null");
			}
		} catch (Exception e) {
			Log.error("Mouse Hovering of Web Element " + element.getText()
					+ " failed");
			throw new ElementException("Mouse Hovering Action failed :: "
					+ e.getMessage());
		}

		Log.debug("Mouse Hover Web Element was succesfull");

	}

	/**
	 * This Method is used to mouse Hover to the element passed by
	 * JavascriptExecutor.
	 * 
	 * @param driver
	 * @param element
	 * @return
	 * @throws ElementException
	 */
	public static boolean mouseHoverByJS(WebDriver driver, WebElement element)
			throws ElementException {

		Log.debug("Mouse Hover over Web Element was succesfull");

		boolean result = false;

		try {

			String mouseOverScript = "if(document.createEvent){"
					+ "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initEvent('mouseover', true, false); "
					+ "arguments[0].dispatchEvent(evObj);" + "}"
					+ " else if(document.createEventObject) {"
					+ " arguments[0].fireEvent('onmouseover');}";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(mouseOverScript, element);
			result = true;

		} catch (Exception e) {
			Log.error("Mouse Hovering thru Javascript Action failed "
					+ element.getText() + " failed");
			throw new ElementException(
					"Mouse Hovering thru Javascript Action failed :: "
							+ e.getMessage());
		}
		return result;
	}

	/**
	 * This Method is used for switch to frame.
	 * 
	 * @param driver
	 * @param iframe
	 * @param value
	 * @param element
	 * @return boolean
	 * 
	 * @throws ElementException
	 */
	public static boolean switchToFrame(WebDriver driver, String iframe,
			Integer value, WebElement element) throws ElementException {

		Log.debug("<==switchToFrame()");

		boolean isSwitchedToFrame = false;

		try {

			if (null != iframe && !iframe.isEmpty()) {

				ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe);
				isSwitchedToFrame = true;

			} else if (null != value && value != 0) {

				driver.switchTo().frame(value);
				isSwitchedToFrame = true;

			} else if (null != element) {

				driver.switchTo().frame(element);
				isSwitchedToFrame = true;
			}

		} catch (Exception e) {
			throw new ElementException("Switch To Frame Action Failed");
		}

		Log.debug("==>switchToFrame()");

		return isSwitchedToFrame;
	}

	/**
	 * This Method is used to switch back to default content after switching to
	 * any frame.
	 * 
	 * @param driver
	 */
	public static void switchToDefaultContent(WebDriver driver) {

		Log.debug("<==switchToDefaultContent()");

		driver.switchTo().defaultContent();

		Log.debug("==>switchToDefaultContent()");
	}

	public static void refreshBrowser(WebDriver driver) {

		Log.debug("<==refreshBrowser()");

		driver.navigate().refresh();

		Log.debug("==>refreshBrowser()");
	}

	/**
	 * This Method is used to clear and enter data testfield element.
	 * 
	 * @param me
	 * @param data
	 */
	public void clearAndEnterData(WebElement me, String data) {
		me.clear();
		me.sendKeys(data);
	}

	/**
	 * This Method loads the wait function with the given time
	 * 
	 * @param driver
	 * @return wait
	 */
	public static Wait<WebDriver> fluentWait(WebDriver driver, long waiting) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(waiting, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS)
				.ignoring(TimeoutException.class);

		return wait;
	}

	/**
	 * This Method waits for element to load till 30sec with the polling for
	 * every 1s
	 * 
	 * @param locator
	 * @param driver
	 * @return List<WebElement>
	 */
	public static List<WebElement> waitForElements(By locator,
			WebDriver driver, String logicalName) {

		List<WebElement> elements = new ArrayList<WebElement>();

		try {
			Wait<WebDriver> wait = fluentWait(driver,
					FrameworkConstants.EXPLICIT_TIMEOUT_SEC);
			elements = wait.until(ExpectedConditions
					.presenceOfAllElementsLocatedBy(locator));
		} catch (TimeoutException exception) {
			Log.error("TimeOut Exception occured for element with Logical Name: "
					+ logicalName);
		}

		return elements;
	}

	/**
	 * This Method is used to check for existence of Element.
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param by
	 * @return boolean
	 * @throws ElementException
	 */
	public static boolean exists(String strLogicalName, WebDriver driver, By by)
			throws ElementException {

		boolean exists = false;
		List<WebElement> elements = waitForElements(by, driver, strLogicalName);
		try {
			if (elements.size() != 0) {
				Log.debug("\"" + strLogicalName + "\" WebElement exists");
				exists = true;
			} else {
				Log.error("\"" + strLogicalName + "\" WebElement not found");
				exists = false;
			}
		} catch (Exception e) {
			Log.error("\"" + strLogicalName + "\" WebElement not found :: "
					+ e.getMessage());
		}

		return exists;
	}

	/**
	 * This Method is used to validate that the element does not exist
	 * 
	 * @param strLogicalName
	 * @param driver
	 * @param by
	 * @return boolean
	 * @throws ElementException
	 */
	public static boolean doesNotExist(String strLogicalName, WebDriver driver,
			By by) throws ElementException {

		List<WebElement> elements = null;
		CommonHelper.waitInSeconds(5);
		try {
			elements = driver.findElements(by);
		} catch (NoSuchElementException e) {
			Log.debug("Element " + strLogicalName + " was not found");
			return true;
		}
		for (int i = 0; i < elements.size(); i++)
			if (elements.get(i).isDisplayed())
				return false;

		return true;
	}

	/**
	 * This Method is will perform null check for the object locator.
	 * 
	 * @param objectLocator
	 * @return boolean
	 */
	public static boolean isObjectLocatorNull(By objectLocator) {

		boolean isLocatorNull = true;

		if (null != objectLocator) {
			isLocatorNull = false;
		}

		return isLocatorNull;
	}

	/**
	 * This Method selects the first record in the table.
	 * 
	 * @param table
	 * @throws ElementException
	 */
	public static void selectFirstRecordFromTable(WebElement table)
			throws ElementException {

		Log.debug("==>selectFirstRecordFromTable()");

		if (null != table) {

			if (table.isDisplayed()) {
				List<WebElement> allRows = table.findElements(By.tagName("tr"));
				Log.debug("No. of Records Displayed: " + allRows.size());

				List<WebElement> visibleRows = new ArrayList<WebElement>();

				if (allRows.size() != 0 && !allRows.isEmpty()) {
					Log.debug("Checking for the Visible Row elements");
					for (int i = 0; i < allRows.size(); i++)
						if (allRows.get(i).isDisplayed()) {
							visibleRows.add(allRows.get(i));
							// Coming out of loop in case size is greater than
							// 2, as our interest is only
							// to select first or second record.
							if (visibleRows.size() > 2)
								break;
						}

					WebElement element = null;
					if (visibleRows.size() == 1) {
						element = visibleRows.get(0);
					} else {
						element = visibleRows.get(1);
					}
					List<WebElement> list = element.findElements(By
							.cssSelector("input[type='radio']"));
					if (list.size() == 0) {
						list = element.findElements(By
								.cssSelector("input[type='checkbox']"));
					}
					list.get(0).click();
				}
			}

		} else {
			Log.error("Unable to select the Record from SearchTable as its WebElement is null");
			throw new ElementException(
					"Unable to select the Record from SearchTable as its WebElement is null");

		}

		Log.debug("<==selectFirstRecordFromTable()");
	}

	/**
	 * This Method selects the multiple records in the table based on the number
	 * of arguments passed.
	 * 
	 * @param table
	 * @throws ElementException
	 */
	public static void selectMultipleRecordsFromTable(WebElement table,
			String... records) throws ElementException {

		Log.debug("==>selectMultipleRecordsFromTable()");
		if (null != table) {

			if (table.isDisplayed()) {

				List<WebElement> allRows = table.findElements(By.tagName("tr"));
				Log.debug("No. of Rows Displayed: " + allRows.size());
				Log.debug("No of records::" + records.length);

				int counter = 0;
				for (int j = 1; j < allRows.size(); j++) {

					String recName = allRows.get(j)
							.findElement(By.cssSelector("td[id*='trhnumber']"))
							.getText();
					for (int i = 0; i < records.length; i++) {

						if (recName.equals(records[i])) {

							allRows.get(j)
									.findElement(
											By.cssSelector("input[type='checkbox']"))
									.click();
							counter++;

						}
					}

					if (counter == records.length)
						break;
				}

			}

		} else {

			Log.error("Unable to select the Record from SearchTable as its WebElement is null");
			throw new ElementException(
					"Unable to select the Record from SearchTable as its WebElement is null");

		}

		Log.debug("<==selectMultipleRecordsFromTable");
	}

	/**
	 * This Method selects the first record in the table.
	 * 
	 * @param table
	 * @throws ElementException
	 */
	public static WebElement getFirstRecordFromTable(WebElement table)
			throws ElementException {

		Log.debug("==>getFirstRecordFromTable()");

		WebElement firstRowElement = null;

		if (null != table) {
			if (table.isDisplayed()) {
				List<WebElement> allRows = table.findElements(By.tagName("tr"));
				Log.debug("No. of Records Displayed: " + allRows.size());
				firstRowElement = allRows.get(1);
			}
		} else {
			Log.error("Unable to get the Record from SearchTable as its WebElement is null");
			throw new ElementException(
					"Unable to get the Record from SearchTable as its WebElement is null");

		}
		Log.debug("<==selectFirstRecordFromTable()");

		return firstRowElement;
	}

	/**
	 * This Method is used to get the cell value from first record of the table
	 * 
	 * @param table
	 * @param locator
	 * @return
	 * @throws ElementException
	 */
	public static String getFirstRecordCellValueFromTable(WebElement table,
			By locator) throws ElementException {

		Log.debug("==>getFirstRecordCellValueFromTable()");

		WebElement firstRow = getFirstRecordFromTable(table);

		return firstRow.findElement(locator).getText();

	}

	/**
	 * This Method selects the record in the table.
	 * 
	 * @param table
	 * @throws ElementException
	 */
	public static WebElement getRecordFromTable(WebElement table, int index)
			throws ElementException {

		Log.debug("==>getRecordFromTable()");

		WebElement rowElement = null;

		if (null != table) {
			if (table.isDisplayed()) {
				List<WebElement> allRows = (table.findElement(By
						.tagName("tbody"))).findElements(By.tagName("tr"));
				Log.debug("No. of Records Displayed: " + allRows.size());
				rowElement = allRows.get(index - 1);

			}

		} else {
			Log.error("Unable to get the Record from SearchTable as its WebElement is null");
			throw new ElementException(
					"Unable to get the Record from SearchTable as its WebElement is null");

		}
		Log.debug("<==getRecordFromTable()");

		return rowElement;
	}

	/**
	 * This Method is used to get the cell value from "index" row of the table
	 * 
	 * @param table
	 * @param locator
	 * @return
	 * @throws ElementException
	 */
	public static String getRecordCellValueFromTable(WebElement table,
			By locator, int index) throws ElementException {

		Log.debug("==>getRecordCellValueFromTable()");

		WebElement row = getRecordFromTable(table, index);

		Log.info(row.findElement(locator).getText());

		return row.findElement(locator).getText();

	}

	/**
	 * This Method returns the count of rows present in the Table passed as
	 * parameter.
	 * 
	 * @param table
	 * @throws ElementException
	 */
	public static int getTableRowCount(WebElement table)
			throws ElementException {

		Log.debug("==>getTableRowCount()");

		int count = 0;
		if (null != table) {
			if (table.isDisplayed()) {
				List<WebElement> allRows = table.findElements(By.tagName("tr"));
				count = allRows.size();
				Log.debug("No. of Records Displayed: " + count);
			}
		} else {
			Log.error("Unable to get the Table Row count as its WebElement is null");
			throw new ElementException(
					"Unable to get the Table Row count as its WebElement is null");
		}

		Log.debug("<==getTableRowCount()");

		return count;
	}

	/**
	 * This Method gives the count of rows displayed after searching the flag
	 * with specific key.
	 * 
	 * @param table
	 * @throws ElementException
	 */
	public static int getConfigTableRowCount(WebElement table)
			throws ElementException {

		Log.debug("==>getTableRowCount()");

		int count = 0;
		if (null != table) {
			if (table.isDisplayed()) {
				List<WebElement> allRows = table.findElements(By.tagName("tr"));
				for (int i = 0; i < allRows.size(); i++) {
					if (allRows.get(i).isDisplayed()) {
						count++;
					}
				}
				Log.debug("No. of Records Displayed: " + count);
			}
		} else {
			Log.error("Unable to get the Table Row count as its WebElement is null");
			throw new ElementException(
					"Unable to get the Table Row count as its WebElement is null");
		}

		Log.debug("<==getTableRowCount()");

		return count;
	}

	/**
	 * This method selects the suggested value from the text field.
	 * 
	 * @throws ElementException
	 *             , ButtonElementException
	 */
	public static void selectSuggestedValue(WebElement element)
			throws ButtonElementException, ElementException {
		ButtonElement.click("Selecting the Suggested Value", element);
	}

	/**
	 * This method is used to maximize browser window
	 * 
	 * @param driver
	 */
	public static void maximizeWindow(WebDriver driver) {

		Log.debug("==>Maximize Window");

		driver.manage().window().maximize();

		Log.debug("<==Maximize Window");
	}

	/**
	 * This me
	 * 
	 * @param driver
	 */
	public static void authenticate(WebDriver driver) {

		try {
			UserAndPassword up = new UserAndPassword("username", "password");
			driver.switchTo().alert().authenticateUsing(up);
		} catch (Exception e) {
			Log.warn("Alert not found");
		}
	}

	/**
	 * This Method returns the date as current date + 5 days
	 */
	public static String setDate() {

		return setDate(5);

	}

	/**
	 * This Method returns the date as current date + 5 days
	 */
	public static String setDate(int days) {

		Log.debug("==>setDate()");

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date()); // Now use today date.
		calendar.add(Calendar.DATE, days); // adding days
		String date = sdf.format(calendar.getTime());

		Log.debug("<==setDate()");

		return date;

	}

	/**
	 * This method gets the Current date.
	 * 
	 * @return current date
	 */
	public static String getDateBeforeOneMonth() {

		Log.debug("==>getCurrentDate()");

		return getDateBeforeOneMonth("dd-MM-yyyy");
	}

	/**
	 * This method gets the Current date.
	 * 
	 * @return current date
	 */
	public static String getDateBeforeOneMonth(String dateFormat) {

		Log.debug("==>getCurrentDate()");

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		String date = sdf.format(calendar.getTime());

		Log.debug("==>getCurrentDate()");

		return date;
	}

	/**
	 * This method gets the Current date.
	 * 
	 * @return current date
	 */
	public static String getCurrentDate(String dateFormat) {

		Log.debug("==>getCurrentDate()");

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar calendar = Calendar.getInstance();
		String date = sdf.format(calendar.getTime());

		Log.debug("==>getCurrentDate()");

		return date;
	}

	/**
	 * This method gets the Current date.
	 * 
	 * @return current date
	 */
	public static String getCurrentDate() {

		Log.debug("==>getCurrentDate()");

		return getCurrentDate("dd-MM-yyyy");
	}

	/**
	 * This method returns the date of 3months after the current date.
	 * 
	 * @return current date
	 */
	public static String getExpiryDate() {

		Log.debug("==>getExpiryDate()");

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 3);
		String date = sdf.format(calendar.getTime());
		return date;
	}

	/**
	 * This method returns the date of current date - 1 year.
	 * 
	 * @return current date
	 */
	public static String setFromDate() {

		Log.debug("setFromDate()");

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -11);
		String date = sdf.format(calendar.getTime());
		return date;
	}

	/**
	 * This method gets the Current date.
	 * 
	 * @return current date
	 */
	public static String getCurrentDateTime() {

		Log.debug("==>getCurrentDateTime()");

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:hh:mm");
		Calendar calendar = Calendar.getInstance();
		String date = sdf.format(calendar.getTime());

		Log.debug("==>getCurrentDateTime()");

		return date;
	}

	/**
	 * This Method returns the record number by using the regular expressions.
	 * 
	 * @param message
	 * @return String
	 */
	public static String splitString(String message) {

		/*
		 * String result = message.replaceAll(
		 * ".*?(\\w+/\\d{2}-\\d{2}-\\d{4}/\\w+).*", "$1");
		 */
		String[] arr = message.split(" ");
		String result = null;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].matches(".*[0-9].*") && arr[i].matches(".*[A-Za-z].*")) {
				result = arr[i];
				break;
			} else if ((arr[i].matches(".*[0-9].*"))) {
				result = arr[i];
				break;
			}
		}

		if (result.contains(":")) {
			arr = result.split(":");
			if (arr.length > 1)
				result = arr[1];
			else
				result = arr[0];
		}

		return result;
	}


	/**
	 * This method is used to convert String to Number
	 * 
	 * @param str
	 * @return
	 */
	public static float convertStringToNumber(String str) {

		Log.debug("convert String To Number");

		Number value = 0;
		DecimalFormat formatter = new DecimalFormat();
		try {
			value = formatter.parse(str);
		} catch (ParseException e) {
			Log.error(e.getMessage());
		}
		return value.floatValue();
	}

	/**
	 * This method is used to convert String to Date
	 * 
	 * @param dateInString
	 * @return Date
	 */
	public static Date convertStringToDate(String dateInString) {

		Log.debug("Convert String to Date");

		Date date = null;
		date = convertStringToDate(dateInString, "dd-MM-yyyy");

		return date;
	}

	/**
	 * This method is used to convert String to Date
	 * 
	 * @param dateInString
	 * @param DateFormat
	 * 
	 * @return Date
	 */
	public static Date convertStringToDate(String dateInString,
			String dateFormat) {

		Log.debug("Convert String to Date");

		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {

			date = formatter.parse(dateInString);

		} catch (ParseException e) {
			Log.error(e.getMessage());
		}

		return date;
	}

	/**
	 * This method is used to convert String to Calendar
	 * 
	 * @param dateInString
	 * @param DateFormat
	 * 
	 * @return Calendar
	 */
	public static Calendar convertStringToCalendar(String dateInString,
			String dateFormat) {

		Log.debug("Convert String to Calendar");

		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();
		try {

			cal.setTime(formatter.parse(dateInString));

		} catch (ParseException e) {
			Log.error(e.getMessage());
		}

		return cal;

	}

	/**
	 * This method is used to check for the Record Exists on click of Search
	 * Button across Search Pages.
	 * 
	 * @return Boolean
	 * @throws Exception
	 */
	public static Boolean doesRecordExists(String searchTableBodyText,
			WebElement searchTable) throws Exception {

		Boolean exists = null;

		if (!searchTableBodyText.equalsIgnoreCase("NO RECORD EXISTS")) {

			if (CommonHelper.getTableRowCount(searchTable) > 0) {

				exists = true;
				Log.debug("Record Exists");

			} else {
				Log.debug("Record DoesNot Exists");
				exists = false;
			}
		} else {
			Log.debug("Record DoesNot Exists");
			exists = false;
		}

		return exists;
	}

	/**
	 * This method is used to click the WebElement by using JavaScript Executor
	 * 
	 * @param driver
	 * @param element
	 */
	public static void clickbyJS(WebDriver driver, WebElement element) {

		Log.debug("Javascript Executor for click Action");

		((JavascriptExecutor) driver).executeScript("arguments[0].click()",
				element);
	}

	/**
	 * This method is used to scroll into Element using JavaScript Executor
	 * 
	 * @param driver
	 * @param solutionElement
	 */
	public static void scrollIntoViewByJS(WebDriver driver,
			WebElement solutionElement) {

		Log.debug("scrollIntoView By JavaScript");

		// Handles if the element is not in the visible portion of
		// the Page
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", solutionElement);
		CommonHelper.waitInSeconds(2);
	}

	/**
	 * Performs a tabout action on the web element passed as input.
	 * 
	 * @param element
	 */
	public static void performTabout(WebElement element) {

		Log.debug("Performing a tab out action");
		if (null != element) {
			element.sendKeys(Keys.TAB);
		} else {
			Log.warn("element is referencing to a null value.Tab out action"
					+ "failed");
		}

	}

	/**
	 * Performs logout action from the webtop solution.
	 * 
	 * @param element
	 */
	public static void performLogout(WebDriver driver) {

		Log.debug("Performing logout action");

		// Switching to WebTop content
		driver.switchTo().defaultContent();

		// Click LogOut Button
		WebDriverWait webDriver = new WebDriverWait(driver, 60);
		webDriver.until(
				ExpectedConditions.elementToBeClickable(driver.findElement(By
						.id("logoff")))).click();

		// Handling Alert
		CommonHelper.handleAlert(driver);
		driver.manage().deleteAllCookies();

		CommonHelper.waitInSeconds(5);

	}

	/**
	 * This Method selects the first record in the table.
	 * 
	 * @param table
	 * @throws ElementException
	 */
	public static void selectFirstRecordFromTableForFnA(WebElement table)
			throws ElementException {

		Log.debug("==>selectFirstRecordFromTable()");

		if (null != table) {

			if (table.isDisplayed()) {
				List<WebElement> allRows = table.findElements(By.tagName("tr"));
				Log.debug("No. of Records Displayed: " + allRows.size());

				List<WebElement> visibleRows = new ArrayList<WebElement>();

				if (allRows.size() != 0 && !allRows.isEmpty()) {
					Log.debug("Checking for the Visible Row elements");
					for (int i = 0; i < allRows.size(); i++)
						if (allRows.get(i).isDisplayed())
							visibleRows.add(allRows.get(i));

					WebElement element = null;
					if (visibleRows.size() >= 1) {
						element = visibleRows.get(0);
					}

					List<WebElement> list = element.findElements(By
							.cssSelector("input[type='radio']"));
					if (list.size() == 0) {
						list = element.findElements(By
								.cssSelector("input[type='checkbox']"));
					}
					list.get(0).click();
				}
			}

		} else {
			Log.error("Unable to select the Record from SearchTable as its WebElement is null");
			throw new ElementException(
					"Unable to select the Record from SearchTable as its WebElement is null");

		}

		Log.debug("<==selectFirstRecordFromTable()");
	}

	/**
	 * This method is used to perform Double Click Action on the WebElement
	 * 
	 * @param driver
	 * @param element
	 * @throws ElementException
	 */
	public static void doubleClick(WebDriver driver, WebElement element)
			throws ElementException {

		Log.debug("Double Click Action");

		try {

			Actions builder = new Actions(driver);
			Action doubleClick = builder.doubleClick(element).build();
			doubleClick.perform();

		} catch (Exception e) {
			Log.error("WebElement DoubleClick Action failed :: "
					+ e.getMessage());
			throw new ElementException(
					"WebElement DoubleClick Action failed :: " + e.getMessage());
		}

	}

	/**
	 * This method is used to get the alert text.
	 * 
	 * @param driver
	 * @return
	 */
	public static String getAlertText(WebDriver driver) {

		Log.debug("Check For Alert Existence");

		String text = null;
		try {
			text = driver.switchTo().alert().getText();
			Log.debug("***Alert Exists***");
		} catch (NoAlertPresentException e) {
			Log.debug("***Alert Not Found***" + e.getMessage());
		}

		return text;
	}

	/**
	 * This method is used to wait for JQuery Processing till the active JQuery
	 * connections become zero within in the prescribed mentioned time.
	 * 
	 * @param driver
	 * @param timeOutInSeconds
	 * @return
	 */
	public static Boolean waitForJQueryProcessing(WebDriver driver,
			int timeOutInSeconds) {

		Log.debug("Wait For JQuery Processing");

		Boolean jQcondition = null;
		final JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		jQcondition = (Boolean) jsExecutor
				.executeScript("return window.jQuery != undefined");

		if (null != jQcondition && !jQcondition) {

			Object numberOfAjaxConnections = jsExecutor
					.executeScript("return jQuery.active");
			Log.debug("No. of Ajax Connections :: " + numberOfAjaxConnections);
			try {
				new WebDriverWait(driver, timeOutInSeconds) {
				}.until(new ExpectedCondition<Boolean>() {

					public Boolean apply(WebDriver driverObject) {
						return (Boolean) jsExecutor
								.executeScript("return jQuery.active == 0");
					}
				});
				jQcondition = (Boolean) jsExecutor
						.executeScript("jQuery.active === 0");
				return jQcondition;
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			Log.debug("JQuery is not defined");
		}

		return jQcondition;
	}

	/**
	 * This method is used to get the active ajax connections running in the
	 * background of the WebPage.
	 * 
	 * @param driver
	 * @return size
	 */
	public static Object getActiveAjaxConnections(WebDriver driver) {

		Log.debug("Get Active Ajax Connecitons");

		Boolean jQcondition = null;
		Object numberOfAjaxConnections = null;
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		jQcondition = (Boolean) jsExecutor
				.executeScript("return window.jQuery != undefined");

		if (null != jQcondition && jQcondition) {
			numberOfAjaxConnections = jsExecutor
					.executeScript("return jQuery.active");
		} else {
			Log.debug("JQuery is not defined");
		}

		Log.debug("No. of Ajax Connections :: " + numberOfAjaxConnections);

		return numberOfAjaxConnections;
	}

	/**
	 * This method is sued to get the Current month number in String format
	 * 
	 * @return Month
	 */
	public static String getCurrentMonth() {

		Log.debug("Get Current Month");
		Calendar calendar = Calendar.getInstance();
		String month = new SimpleDateFormat("MM").format(calendar.getTime());
		return month;
	}

	/**
	 * This method is used to get the current year in String format
	 * 
	 * @return Year
	 */
	public static String getCurrentYear() {

		Log.debug("Get Current Year");
		Calendar calendar = Calendar.getInstance();
		String year = new SimpleDateFormat("yyyy").format(calendar.getTime());
		return year;
	}

	/**
	 * This method is used to get the Difference in days between two Dates
	 * 
	 * @return diffInDays
	 */
	public static int getDiffInDays(Date d1, Date d2) {

		Log.debug("Get Differnce in Days between two Dates");

		int diffInDays = (int) ((d1.getTime() - d2.getTime()) / (1000 * 60 * 60 * 24));

		Log.debug("Differnce in Days:: " + diffInDays);

		if (diffInDays >= 0)
			diffInDays++;

		return diffInDays;
	}

}
