package com.itc.utilities.exceptions;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

/**
 * CheckBoxElementException class consists of generic exception methods related to CheckBox actions.
 *
 * @author Madhusudhan
 */
public class CheckBoxElementException extends GenericUIException{
	static final long serialVersionUID = 1L;
	
	public CheckBoxElementException(String msg){
		super(msg);
	}
	
	public CheckBoxElementException(String msg,WebDriver driver) throws DataSourceException, IOException, Exception{
		super(msg,driver);
	}
	
	public CheckBoxElementException(Throwable cause) {
		super(cause);
	}

	public CheckBoxElementException(String message, Throwable cause) {
		super(message, cause);
	}
}