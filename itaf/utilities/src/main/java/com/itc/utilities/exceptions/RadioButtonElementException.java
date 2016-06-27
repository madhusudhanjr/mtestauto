package com.itc.utilities.exceptions;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

/**
 * RadioButtonElementException class consists of generic exception methods related to RadioButton actions.
 *
 * @author Madhusudhan
 */
public class RadioButtonElementException extends GenericUIException{
	static final long serialVersionUID = 1L;
	
	public RadioButtonElementException(String msg){
		super(msg);
	}
	
	public RadioButtonElementException(String msg,WebDriver driver) throws DataSourceException, IOException, Exception{
		super(msg,driver);
	}
	
	public RadioButtonElementException(Throwable cause) {
		super(cause);
	}

	public RadioButtonElementException(String message, Throwable cause) {
		super(message, cause);
	}
}