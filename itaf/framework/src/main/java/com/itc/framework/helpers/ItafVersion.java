package com.itc.framework.helpers;

/**
 * Class to fetch iTAF version. iTAF version can be fetched from the command
 * prompt by executing command "--version" to the itaf.jar
 * 
 * @author ITC Infotech
 * 
 */
public class ItafVersion {

	/**
	 * Default ItafVersion Constructor
	 */
	public ItafVersion() {

	}

	/**
	 * This method is used to set and print the iTAF version in the command
	 * prompt
	 */
	public static String printVersion() {

		String version = "ITC Infotech Test Automation Framework (iTAF) version "
				+ FrameworkConstants.ITAFVERSION;

		System.out.println(version);

		return version;
	}

}
