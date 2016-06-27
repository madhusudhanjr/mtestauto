package com.itc.utilities.exceptions;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

/**
 * TexyBoxElementException class consists of generic exception methods related to textbox actions.
 *
 * @author Madhusudhan
 */
public class TextBoxElementException extends GenericUIException{
	static final long serialVersionUID = 1L;
	
	public TextBoxElementException(String msg){
		super(msg);
	}
	
	public TextBoxElementException(String msg,WebDriver driver) throws DataSourceException, IOException, Exception{
		super(msg,driver);
	}
	
	public TextBoxElementException(Throwable cause) {
		super(cause);
	}

	public TextBoxElementException(String message, Throwable cause) {
		super(message, cause);
	}
}