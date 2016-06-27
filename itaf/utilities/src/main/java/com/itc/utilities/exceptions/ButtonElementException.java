package com.itc.utilities.exceptions;


import java.io.IOException;

import org.openqa.selenium.WebDriver;

/**
 * ButtonElementException class consists of generic exception methods related to ButtonElement actions.
 *
 * @author Madhusudhan
 */
public class ButtonElementException extends GenericUIException{
	static final long serialVersionUID = 1L;
	
	public ButtonElementException(String msg){
		super(msg);
	}
	
	public ButtonElementException(String msg,WebDriver driver) throws IOException, Exception{
		super(msg,driver);
	}
	
	public ButtonElementException(Throwable cause) {
		super(cause);
	}

	public ButtonElementException(String message, Throwable cause) {
		super(message, cause);
	}
}