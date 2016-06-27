package com.itc.utilities.base;

import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.itc.framework.loggers.Log;
import com.itc.utilities.enums.DriverSupportedBrowsers;
import com.itc.utilities.enums.ExecutionMode;
import com.itc.utilities.exceptions.DataSourceException;
import com.itc.utilities.helpers.CommonHelper;

/**
 * BaseTest Class consists of methods that are commonly used across all Tests.
 * 
 * @author ITC Infotech
 * 
 */
public class BaseTest {

	public static String m_customer;
	public static String m_executionMode;
	private static String m_browser;
	private static String m_browserVersion;
	private static String m_gridUrl;
	public static ITestContext m_context;
	public static String m_testMethod;
	private static ChromeDriverService service;

	/**
	 * m_downloadPath holds the path of "ItafDownload" folder which is under
	 * Test Context Output Directory
	 */
	private static String m_downloadPath;

	/**
	 * Attributes to hold different folder structures
	 */
	public static String m_logDir = null;

	/**
	 * Attribute to hold environment URL for the test.
	 */
	public static String m_url = null;

	/**
	 * Attribute to hold value for open or release state transactions.
	 */
	public static String m_transType = null;

	/**
	 * Synchronized list of web drivers that stores the browser driver instance
	 * for each thread instance
	 */
	private static List<WebDriver> m_listOfWebDrivers = Collections
			.synchronizedList(new ArrayList<WebDriver>());
	private static ThreadLocal<WebDriver> m_driverForThread = new ThreadLocal<WebDriver>() {

		@Override
		protected WebDriver initialValue() {
			WebDriver driver = null;
			try {
				driver = loadDriver();
			} catch (DataSourceException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Log.info("Initializing Webdriver");
			m_listOfWebDrivers.add(driver);
			return driver;
		}
	};

	/**
	 * This method is called before any test/tests execution. Method creates the
	 * directory name where the log file will be stored and adds it as an
	 * attribute to TestNG test context
	 * 
	 * @param outdir
	 *            , logAndReportFilePath
	 * @throws IOException
	 */
	@Parameters({ "outdir", "logAndReportFilePath", "browser" })
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(@Optional String outdir,
			@Optional String logAndReportFilePath, @Optional String browser,
			@Optional ITestContext context) throws IOException {

		if (null != outdir && !outdir.isEmpty()
				&& outdir.charAt(outdir.length() - 1) != '/')
			outdir = outdir + "/";

		m_logDir = (outdir == null ? "" : outdir) + "log/"
				+ (logAndReportFilePath == null ? "" : logAndReportFilePath);

		File dir = new File(m_logDir);
		dir.mkdirs();

		// Creation of "ItafDownloads" folder under "outdir" for download tests
		m_downloadPath = context.getOutputDirectory() + "\\..\\iTAFDownloads\\";
		File downloadDir = new File(m_downloadPath);
		downloadDir.mkdirs();

		if (browser.isEmpty())
			browser = System.getenv("BROWSER");

		m_browser = browser;
		/**
		 * Starting the ChromeDriverService or ChromeDriverExecutable
		 */
		if (browser.equalsIgnoreCase(DriverSupportedBrowsers.CHROME.toString())) {
			Log.info("@BeforeSuite: Start ChromeDriver Service.");
			service = new ChromeDriverService.Builder()
					.usingDriverExecutable(
							new File(
									"../utilities/src/main/resources/chromedriver.exe"))
					.usingAnyFreePort().build();
			service.start();
			Log.info("@BeforeSuite: ChromeDriver Service started.");

		}

	}

	/**
	 * This method is called before any test executions. This Method stores the
	 * attribute "DOWNLOAD_PATH" to TestNG test context
	 * 
	 * @param customerName
	 * @throws IOException
	 */

	@Parameters({ "mode", "customer", "url", "browserVersion", "gridUrl",
			"password", "DApassword", "transType", "browser" })
	@BeforeTest(alwaysRun = true)
	public void beforeTest(String mode, String customer, @Optional String url,
			@Optional String browserVersion, @Optional String gridUrl,
			@Optional String password, @Optional String DApassword,
			@Optional String transType, String browser, ITestContext context)
			throws Exception {

		context.setAttribute("DOWNLOAD_PATH", m_downloadPath);

		if (customer.isEmpty())
			customer = System.getenv("CUSTOMER");

		if (mode.isEmpty())
			mode = System.getenv("MODE");

		if (url.isEmpty())
			url = System.getenv("URL");

		m_context = context;
		m_browserVersion = browserVersion;
		m_gridUrl = gridUrl;
		m_url = url;
		m_customer = customer;
		m_executionMode = mode;
		m_transType = transType;

		// Taking care that customized report generates cleanly for eclipse runs
		if (null == context.getCurrentXmlTest().getParameter("customer")
				|| context.getCurrentXmlTest().getParameter("customer")
						.isEmpty()) {
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("customer", customer);
			context.getCurrentXmlTest().setParameters(param);

		}

		initialize(context);
	}

	/**
	 * This method creates a log file for the test method to be executed,
	 * instantiates the WebDriver for the supported browser and version. The
	 * Method name and Driver instance is set as key value pair to ensure that
	 * the driver instantiated to run the method is only quit This will help in
	 * scenarios where methods are executed in parallel
	 * 
	 * TODO Need to implement 1.Reading of Browser and version from the cmd line
	 * 2.Defaulting to the versions 3.Choosing profiles
	 * 
	 * @throws IOException
	 */
	// @BeforeMethod(alwaysRun = true)
	public void initialize(ITestContext context) throws IOException {

		String customer = context.getCurrentXmlTest().getParameter("customer");
		String methodName = "";
		m_testMethod = "searchResults";
		methodName = m_testMethod + "_" + customer;
		CommonHelper.renameRetryLog(m_logDir, methodName);
		Log.setLog(m_logDir, methodName);

		try {

			// Logging browser name and version parameters, driver and thread
			// instances
			Capabilities webDriverCapablities = ((RemoteWebDriver) getDriverInstanceForThread())
					.getCapabilities();

			Log.info("\n ****************** START OF TEST CASE  "
					+ m_testMethod + "   " + customer + ":"
					+ webDriverCapablities.getBrowserName() + ":"
					+ webDriverCapablities.getVersion() + "\t THREAD:"
					+ Thread.currentThread().getId() + "\t WEBDRIVER:"
					+ getDriverInstanceForThread() + " ****************** \n");

			if (m_executionMode.equalsIgnoreCase(ExecutionMode.WEB_BROWSER
					.toString()))
				CommonHelper.maximizeWindow(getDriverInstanceForThread());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Parameters({ "customer" })
	@AfterTest(alwaysRun = true)
	public void tearDown(ITestContext context, String customer) {

		String methodName = m_testMethod + "_" + customer;

		if (getDriverInstanceForThread() != null) {

			// Log.info("Active ajax Connections :: "
			// + CommonHelper
			// .getActiveAjaxConnections(getDriverInstanceForThread()));
			try {
				try {
					CommonHelper.takeScreenShot(getDriverInstanceForThread(),
							context.getOutputDirectory(), methodName);
				} catch (Exception e) {
					Log.error("Failed taking screenshot");
				}

				try {
					CommonHelper.dumpHTMLSource(getDriverInstanceForThread(),
							context.getOutputDirectory(), methodName);
				} catch (Exception e) {
					Log.error("Failed to dump the HTML Source");
				}

			} catch (Exception e) {
				Log.fatal("Error in @AfterMethod : " + e.getMessage());
			}
		}

		Log.info(" \n**********************  END OF TEST CASE " + m_testMethod
				+ "  ********************** \n");
		Log.flushLog();

	}

	@AfterSuite(alwaysRun = true)
	public void shutdown() {

		Log.info("@AfterSuite Shutdown WebDrivers");

		if (!m_listOfWebDrivers.isEmpty()) {

			Log.info("@AfterSuite Drivers to quit:" + m_listOfWebDrivers.size());

			for (WebDriver driver : m_listOfWebDrivers) {

				if (driver != null) {

					try {

						Log.info("@AfterSuite Quit the driver:" + driver);
						// CommonHelper.waitInSeconds(3);
						driver.quit();
						Log.info("@AfterSuite WebDriver Quit Success");

					} catch (Exception e) {
						Log.error("@AfterSuite Exception Caught while quitting driver: "
								+ e.getMessage());
						e.printStackTrace();
					}

				}

			}

		}

		/**
		 * Stopping/Terminating the ChromeDriverService or
		 * ChromeDriverExecutable
		 */
		if (m_browser.equalsIgnoreCase(DriverSupportedBrowsers.CHROME
				.toString())) {
			service.stop();
			Log.info("@AfterSuite ChromeDriver Service stopped.");
		}
	}

	/**
	 * Creates a instance of driver corresponding to the thread id and returns
	 * the driver If a driver instance already exists for thread id then it will
	 * just return that instance
	 * 
	 * @return
	 */
	public WebDriver getDriverInstanceForThread() {
		return m_driverForThread.get();
	}

	/**
	 * This Method is used to instantiate the respective driver with its browser
	 * type and version being passed.
	 * 
	 * @return
	 * @throws DataSourceException
	 * @throws IOException
	 */
	protected static WebDriver loadDriver() throws DataSourceException,
			IOException {

		Log.debug("Get Driver for Browser : " + m_browser + " Version : "
				+ m_browserVersion);

		WebDriver driver = getNewDriver(
				DriverSupportedBrowsers.valueOf(m_browser.toUpperCase()), "",
				"", m_gridUrl, m_context);

		Log.debug("Succesfully instantiated driver :: " + m_browser);

		return driver;
	}

	/**
	 * This Method initializes the driver by taking driver type as the main
	 * input.
	 * 
	 * @param driverType
	 * @param browserVersion
	 * @param platform
	 * @return
	 * @throws IOException
	 */
	public static WebDriver getNewDriver(DriverSupportedBrowsers driverType,
			String browserVersion, String platform, String gridUrl,
			ITestContext context) throws IOException {

		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.BROWSER, Level.ALL);

		if (null != gridUrl && !gridUrl.isEmpty()) {

			gridUrl = gridUrl + "/wd/hub";

			switch (driverType) {

			case FIREFOX:

				DesiredCapabilities desiredCapabilities = DesiredCapabilities
						.firefox();
				desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS,
						logPrefs);
				desiredCapabilities.setCapability(
						CapabilityType.ACCEPT_SSL_CERTS, true);
				desiredCapabilities.setCapability("elementScrollBehavior", 1);

				FirefoxProfile firefoxProfile = new FirefoxProfile();
				firefoxProfile.setEnableNativeEvents(false);

				// Added to turn off "Firefox Safe Mode" Popup
				firefoxProfile.setPreference(
						"toolkit.startup.max_resumed_crashes", 999999);
				firefoxProfile.setPreference("browser.safebrowsing.enabled",
						false);

				/**
				 * Added to auto handle save dialog prompt for file types like
				 * "pdf", "excel", etc.
				 */
				firefoxProfile.setPreference("browser.download.folderList", 2);
				firefoxProfile.setPreference(
						"browser.download.manager.showWhenStarting", false);

				// Setting Download Path
				firefoxProfile.setPreference("browser.download.dir",
						m_downloadPath);

				firefoxProfile.setPreference("pdfjs.disabled", true);
				firefoxProfile.setPreference("plugin.scan.plid.all", false);
				firefoxProfile.setPreference("plugin.scan.Acrobat", "99.0");

				firefoxProfile.setPreference(
						"plugin.disable_full_page_plugin_for_types",
						"application/pdf,application/vnd.adobe.xfdf,application/vnd.fdf,"
								+ "application/vnd.adobe.xdp+xml");

				firefoxProfile
						.setPreference(
								"browser.helperApps.neverAsk.saveToDisk",
								"application/vnd.ms-excel, "
										+ "application/octet-stream, application/pdf, application/vnd.fdf, "
										+ "application/x-unknown-application-octet-stream");

				firefoxProfile.setPreference(
						"browser.helperApps.alwaysAsk.force", false);
				firefoxProfile.setPreference("network.http.use-cache", false);

				/********* End of handle save dialog prompt *******/

				desiredCapabilities.setCapability(FirefoxDriver.PROFILE,
						firefoxProfile);
				return new RemoteWebDriver(new URL(gridUrl),
						desiredCapabilities);

			case CHROME:

				String chromeDriverPath = "../utilities/src/main/resources/chromedriver.exe";

				if (null != chromeDriverPath) {

					System.setProperty(
							ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
							chromeDriverPath);

					DesiredCapabilities capabilities = DesiredCapabilities
							.chrome();
					capabilities.setCapability(CapabilityType.LOGGING_PREFS,
							logPrefs);
					ChromeOptions options = new ChromeOptions();
					options.addArguments("test-type");
					options.addArguments("--start-maximized");
					capabilities.setCapability(ChromeOptions.CAPABILITY,
							options);
					return new RemoteWebDriver(new URL(gridUrl), capabilities);
				}

			case IE:

				String ieDriverPath = System.getenv("IEDRIVER_HOME");

				if (null != ieDriverPath) {

					DesiredCapabilities capabilities_ie = DesiredCapabilities
							.internetExplorer();
					capabilities_ie.setCapability(CapabilityType.LOGGING_PREFS,
							logPrefs);
					capabilities_ie.setCapability("nativeEvents", false);
					System.setProperty("webdriver.ie.driver", ieDriverPath);
					return new RemoteWebDriver(new URL(gridUrl),
							capabilities_ie);

				} else {

					throw new FileNotFoundException(
							"\"IEDriverServer.exe\" path need to be set as value "
									+ "for User Variable \"IEDRIVER_HOME\" in System Environment Variables");
				}

			case SAFARI:
				return new SafariDriver();

			default:
				throw new InvalidParameterException(
						"You must choose one of the defined driver types");
			}

		} else {

			DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

			switch (driverType) {

			case FIREFOX:
				FirefoxProfile firefoxProfile = new FirefoxProfile();
				desiredCapabilities.setCapability("elementScrollBehavior", 1);
				firefoxProfile.setEnableNativeEvents(false);

				// Added to turn off "Firefox Safe Mode" Popup
				firefoxProfile.setPreference(
						"toolkit.startup.max_resumed_crashes", 999999);
				firefoxProfile.setPreference("browser.safebrowsing.enabled",
						false);

				/**
				 * Added to auto handle save dialog prompt for file types like
				 * "pdf", "excel", etc.
				 */
				firefoxProfile.setPreference("browser.download.folderList", 2);
				firefoxProfile.setPreference(
						"browser.download.manager.showWhenStarting", false);

				// Setting Download Path
				firefoxProfile.setPreference("browser.download.dir",
						m_downloadPath);

				firefoxProfile.setPreference("pdfjs.disabled", true);
				firefoxProfile.setPreference("plugin.scan.plid.all", false);
				firefoxProfile.setPreference("plugin.scan.Acrobat", "99.0");

				firefoxProfile.setPreference(
						"plugin.disable_full_page_plugin_for_types",
						"application/pdf,application/vnd.adobe.xfdf,application/vnd.fdf,"
								+ "application/vnd.adobe.xdp+xml");

				firefoxProfile
						.setPreference(
								"browser.helperApps.neverAsk.saveToDisk",
								"application/vnd.ms-excel, "
										+ "application/octet-stream, application/pdf, application/vnd.fdf, "
										+ "application/x-unknown-application-octet-stream");

				firefoxProfile.setPreference(
						"browser.helperApps.alwaysAsk.force", false);
				firefoxProfile.setPreference("network.http.use-cache", false);

				/********* End of handle save dialog prompt *******/

				desiredCapabilities.setCapability(FirefoxDriver.PROFILE,
						firefoxProfile);
				return new FirefoxDriver(desiredCapabilities);

			case CHROME:

				// String chromeDriverPath =
				// "../utilities/src/main/resources/chromedriver.exe";

				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(CapabilityType.LOGGING_PREFS,
						logPrefs);
				/*
				 * ChromeOptions options = new ChromeOptions();
				 * options.addArguments("test-type");
				 * options.addArguments("--start-maximized");
				 * capabilities.setCapability(ChromeOptions.CAPABILITY);
				 */

				return new RemoteWebDriver(service.getUrl(), capabilities);

			case IE:

				String ieDriverPath = System.getenv("IEDRIVER_HOME");

				if (null != ieDriverPath) {

					desiredCapabilities.setCapability("nativeEvents", false);
					System.setProperty("webdriver.ie.driver", ieDriverPath);
					return new InternetExplorerDriver(desiredCapabilities);

				} else {

					throw new FileNotFoundException(
							"\"IEDriverServer.exe\" path need to be set as value "
									+ "for User Variable \"IEDRIVER_HOME\" in System Environment Variables");
				}

			case SAFARI:
				return new SafariDriver();

			case MOBILE_CHROME:

				desiredCapabilities.setCapability("platformName", "Android");
				desiredCapabilities.setCapability("VERSION", "5.1");
				desiredCapabilities.setCapability("deviceName",
						"07bd6df500f2fb54");
				desiredCapabilities.setCapability("appPackage",
						"com.android.chrome");
				desiredCapabilities.setCapability("BROWSER_NAME", "Android");
				return new RemoteWebDriver(new URL(
						"http://127.0.0.1:4723/wd/hub"), desiredCapabilities);

			case ANDROID_APP:

				desiredCapabilities.setCapability("platformName", "Android");
				desiredCapabilities.setCapability("VERSION", "5.1");
				desiredCapabilities.setCapability("deviceName",
						"07bd6df500f2fb54");
				desiredCapabilities.setCapability("appPackage",
						"in.amazon.mShop.android.shopping");
				desiredCapabilities.setCapability("appActivity",
						"com.amazon.mShop.home.HomeActivity");
				desiredCapabilities.setCapability("BROWSER_NAME", "Android");
				return new AndroidDriver(
						new URL("http://127.0.0.1:4723/wd/hub"),
						desiredCapabilities);

			default:
				throw new InvalidParameterException(
						"You must choose one of the defined driver types");
			}

		}
	}

	/**
	 * Start the browser with the URL specified
	 * 
	 * @param url
	 * @throws Exception
	 */
	public void launchApplication(String url) throws Exception {

		try {

			if (!ExecutionMode.ANDROID_APP.toString().equalsIgnoreCase(
					m_executionMode)) {
				Log.info("Go to URL : " + url);
				WebDriver driver = getDriverInstanceForThread();
				if (null != url && !url.isEmpty())
					driver.get(url);
			}
		} catch (Exception e) {
			Log.error("Unable to go to url : " + e.getMessage());
			throw e;
		}

	}

}