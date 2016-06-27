package com.itc.utilities.exceptions;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

/**
 * ListElementException class consists of generic exception methods related to ListElement actions.
 *
 * @author Madhusudhan
 */
public class ListElementException extends GenericUIException{
	static final long serialVersionUID = 1L;
	
	public ListElementException(String msg){
		super(msg);
	}
	
	public ListElementException(String msg,WebDriver driver) throws DataSourceException, IOException, Exception{
		super(msg,driver);
	}
	
	public ListElementException(Throwable cause) {
		super(cause);
	}

	public ListElementException(String message, Throwable cause) {
		super(message, cause);
	}
}