package com.itc.utilities.exceptions;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

/**
 * LinkElementException class consists of generic exception methods related to LinkElement actions.
 *
 * @author Madhusudhan
 */
public class LinkElementException extends GenericUIException{
	static final long serialVersionUID = 1L;
	
	public LinkElementException(String msg){
		super(msg);
	}
	
	public LinkElementException(String msg,WebDriver driver) throws DataSourceException, IOException, Exception{
		super(msg,driver);
	}
	
	public LinkElementException(Throwable cause) {
		super(cause);
	}

	public LinkElementException(String message, Throwable cause) {
		super(message, cause);
	}
}