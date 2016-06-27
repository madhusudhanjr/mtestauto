package com.itc.utilities.exceptions;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

/**
 * ComboBoxElementException class consists of generic exception methods related to ComboBox actions.
 *
 * @author Madhusudhan
 */
public class ComboBoxElementException extends GenericUIException{
	static final long serialVersionUID = 1L;
	
	public ComboBoxElementException(String msg){
		super(msg);
	}
	
	public ComboBoxElementException(String msg,WebDriver driver) throws DataSourceException, IOException, Exception{
		super(msg,driver);
	}
	
	public ComboBoxElementException(Throwable cause) {
		super(cause);
	}

	public ComboBoxElementException(String message, Throwable cause) {
		super(message, cause);
	}
}