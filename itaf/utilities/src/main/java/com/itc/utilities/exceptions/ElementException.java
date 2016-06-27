package com.itc.utilities.exceptions;

/**
 *ElementException class consists of generic exception methods related to element actions.
 *
 * @author Madhusudhan
 */
public class ElementException extends GenericException{
	static final long serialVersionUID = 1L;
	
	public ElementException(String msg){
		super(msg);
	}
	
	public ElementException(Throwable cause) {
		super(cause);
	}

	public ElementException(String message, Throwable cause) {
		super(message, cause);
	}
}