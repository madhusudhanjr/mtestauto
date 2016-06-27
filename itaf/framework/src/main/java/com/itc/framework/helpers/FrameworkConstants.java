package com.itc.framework.helpers;

/**
 * FrameworkConstants Class consists of all the common String constants which is used
 * across FramwworkFactory Module.
 * 
 * @author ITC Infotech
 */
public class FrameworkConstants {
	
	public static final String ITAFVERSION = "1.0 \"27-June-2016\"";
	public static final int EXPLICIT_TIMEOUT_SEC = 30;
	
	/**This variable stores the special characters supported in master codes**/
	public static final String MASTER_CODE_SUPPORTED_SPECCHAR = " /_.-%){}";
			
	public static String GROUPPACKAGE				= "com.itc";
	public static String TESTPACKAGE_EXT			= "tests";
	public static String SOURCE_REL_PATH			= "src\\main\\java";
	public static String SOURCE_REL_RESOURCE_PATH	= SOURCE_REL_PATH + "..\\resources";
	public static String TEST_REL_PATH				= "src\\test\\java";
	public static String TEST_REL_RESOURCE_PATH	    = TEST_REL_PATH + "..\\resources";
	
	/**
	 * <p><code>com.itc<solution name>.tests.<module name>.<submodule name>::<Test Method></code>
	 * <p>For Eg: <code>com.itc.amazon.tests.AmazonSearch::getSearchResults</code>
	 */
	public static int SOLUTION_NAME_INDEX_IN_PACKAGE = 2;
	public static int TEST_INDEX_IN_PACKAGE = 3;
	public static int MODULE_NAME_INDEX_IN_PACKAGE = 4;
	public static int SUBMODULE_NAME_INDEX_IN_PACKAGE = 5;
	
	public static String HOME_FRAMEWORKFACTORY	= "framework";
	public static String HOME_UTILITIES			= "utilities";
	public static String HOME_UI				= "ui";
	public static String HOME_SRC				= "src";
	public static String HOME_AMAZON     		= "amazon";
	
}
