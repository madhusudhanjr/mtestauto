package com.itc.framework.reporters;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * Resulter Class consists of methods related to Reporting.
 * 
 * @author ITC Infotech
 */
public class Resulter {
	private static Logger LOGGER = Logger.getLogger(Resulter.class);
	
	/**
	 * This method is used to log the info in Reporter
	 * 
	 * @param s1
	 */
	public static void log(String[] s1) {
		//do nothing
	}

	/**
	 * This method is used to log the info in Reporter
	 * 
	 * @param arrResult
	 */
	public static void log(List<String> arrResult) {
		if(arrResult!=null){
			LOGGER.info(arrResult);
			}
	}
}