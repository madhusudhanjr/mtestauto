package com.itc.utilities.exceptions;


/**
 * GenericException class is the parent class for all types of customized exceptions
 *
 * @author Madhusudhan
 */
public class GenericException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public GenericException(String msg) {
		super(msg);
	}
	
	public GenericException(Throwable cause) {
		super(cause);
	}

	public GenericException(String message, Throwable cause) {
		super(message, cause);
	}

}
