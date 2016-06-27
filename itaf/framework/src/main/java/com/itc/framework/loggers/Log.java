package com.itc.framework.loggers;

import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.testng.Reporter;

/**
 * Log Class contains various levels of log methods used for Logging.
 * 
 * @author ITC Infotech
 * 
 */
public class Log {

	/**
	 * Create a log file for each test method and add it to logger id which is
	 * identified by the current thread id
	 * So for example there are 3 threads ( T1, T2, T3) executing one test method each
	 * (T1-> M1 , T2 -> M2 , T3 -> M3 ) we will be able to get the log file to which
	 * the message needs to be logged too. 
	 * @param dirPath
	 * @param methodName
	 */
	public static void setLog( String dirPath , String methodName  ){
		
		Logger detailedLogger = Logger.getLogger("DetailedLog_" + Thread.currentThread().getId() );

		try {
			PatternLayout patternLayout = new PatternLayout("%d{HH:mm:ss}  %-5.5p  %m%n"); 
			
			FileAppender iTAFLogAppender = new FileAppender(patternLayout,dirPath + "/" + methodName + ".log" ,false);
			
			detailedLogger.addAppender(iTAFLogAppender);			
			detailedLogger.setLevel(Level.DEBUG);	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    

		
	}
	
	public static void flushLog(){
		Logger detailedLogger = Logger.getLogger("DetailedLog_" + Thread.currentThread().getId() );
		detailedLogger.removeAllAppenders();
	}
	
	public static Logger getLog() {
		
		return Logger.getLogger("DetailedLog_" + Thread.currentThread().getId());
		
	}

	/**
	 * This method gets the class name at the runtime for all the log level methods of the Log
	 * Class from the StackTrace.
	 * 
	 * @return ClassName
	 */
	public static String getClassName() {

		return new Throwable().getStackTrace()[3].getClassName();
	}
	
	/**
	 * This method gets the name of the method which is calling the other method at the runtime.
	 * 
	 * @return ClassName
	 */
	public static int getLineNumber() {

		return new Throwable().fillInStackTrace().getStackTrace()[2].getLineNumber();

	}
	
	/**
	 * Method returns the information the class:method & and line number from where the
	 * logging method ( info(),debug().. ) is called. This is not the best way.. but
	 * had to get it to work
	 * 
	 * @return Returns call information in <ClassName>:<MethodName> <Line Number> format
	 */
	public static String getCallInfo(){
		
		String callInfo;
        String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
		
        callInfo = getClassName() + ":" + methodName + " " + lineNumber + " ";
		return callInfo;
		
	}
	
	/**
	 * This Method designates finer-grained informational events than the DEBUG.
	 * 
	 * @param message
	 */
	public static void trace(Object message) {
		getLog().trace(message);
	}

	/**
	 * This Method designates finer-grained informational events than the DEBUG.
	 * 
	 * @param message
	 * @param t
	 */
	public static void trace(Object message, Throwable t) {
		getLog().trace(message, t);
	}

	/**
	 * This Method is used to check whether the trace level log is enabled.
	 * 
	 * @return boolean
	 */
	public static boolean isTraceEnabled() {
		return getLog().isTraceEnabled();
	}

	/**
	 * This Method is used to log breakpoint logs in breakpoint log file.
	 * 
	 * @param printMessage
	 */
	public static void breakPointLog(String printMessage) {

		Reporter.log(printMessage);
	}

	/**
	 * This Method designates fine-grained informational events that are most
	 * useful to debug an application.
	 * 
	 * @param message
	 */
	public static void debug(Object message) {
		
		getLog().debug( getCallInfo() + message);
	}

	/**
	 * This Method designates fine-grained informational events that are most
	 * useful to debug an application.
	 * 
	 * @param message
	 * @param t
	 */
	public static void debug(Object message, Throwable t) {
		getLog().debug(getCallInfo() + message, t);
	}

	/**
	 * This Method designates error events that might still allow the
	 * application to continue running.
	 * 
	 * @param message
	 */
	public static void error(Object message) {
		
		getLog().error(getCallInfo() + message);
	}

	/**
	 * This Method designates error events that might still allow the
	 * application to continue running.
	 * 
	 * @param message
	 * @param t
	 */
	public static void error(Object message, Throwable t) {
		getLog().error(getCallInfo() + message, t);
	}

	/**
	 * This Method designates very severe error events that will presumably lead
	 * the application to abort.
	 * 
	 * @param message
	 */
	public static void fatal(Object message) {
		getLog().fatal(getCallInfo() + message);
	}

	/**
	 * This Method designates very severe error events that will presumably lead
	 * the application to abort.
	 * 
	 * @param message
	 * @param t
	 */
	public static void fatal(Object message, Throwable t) {
		getLog().fatal(getCallInfo() + message, t);
	}

	/**
	 * This Method designates informational messages that highlight the progress
	 * of the application at coarse-grained level.
	 * 
	 * @param message
	 */
	public static void info(Object message) {
		
		getLog().info(getCallInfo() + message);
	}

	/**
	 * This Method designates informational messages that highlight the progress
	 * of the application at coarse-grained level.
	 * 
	 * @param message
	 * @param t
	 */
	public static void info(Object message, Throwable t) {
		getLog().info(getCallInfo() + message, t);
	}

	/**
	 * This Method is used to check whether the debug level log is enabled.
	 * 
	 * @return boolean
	 */
	public static boolean isDebugEnabled() {
		return getLog().isDebugEnabled();
	}

	/**
	 * This Method is used to check whether the log level log is enabled for
	 * Priority levels.
	 * 
	 * @return boolean
	 */
	public static boolean isEnabledFor(Priority level) {
		return getLog().isEnabledFor(level);
	}

	/**
	 * This Method is used to check whether the Info level log is enabled.
	 * 
	 * @return boolean
	 */
	public static boolean isInfoEnabled() {
		return getLog().isInfoEnabled();
	}

	/**
	 * This Method is used to set the level for Prioritizing the logs.
	 * 
	 * @param level
	 */
	public static void setLevel(Level level) {
		getLog().setLevel(level);
	}

	/**
	 * This Method designates potentially harmful situations.
	 * 
	 * @param message
	 */
	public static void warn(Object message) {
		getLog().warn(getCallInfo() + message);
	}

	/**
	 * This Method designates potentially harmful situations.
	 * 
	 * @param message
	 * @param t
	 */
	public static void warn(Object message, Throwable t) {
		getLog().warn(getCallInfo() + message, t);
	}
}