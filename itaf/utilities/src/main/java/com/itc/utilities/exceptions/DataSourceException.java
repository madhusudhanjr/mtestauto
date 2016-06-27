/**
 * 
 */
package com.itc.utilities.exceptions;

/**
 * DataSourceException class consists of generic exception methods related to datasource.
 *
 * @author Madhusudhan
 */
public class DataSourceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DataSourceException() {
		
	}

	/**
	 * @param message
	 */
	public DataSourceException(String message) {
		super(message);
		
	}

	/**
	 * @param cause
	 */
	public DataSourceException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DataSourceException(String message, Throwable cause) {
		super(message, cause);
		
	}

}
