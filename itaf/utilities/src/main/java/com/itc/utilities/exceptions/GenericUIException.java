package com.itc.utilities.exceptions;


import java.io.IOException;

import org.openqa.selenium.WebDriver;


/**
 * GenericException class consists of generic exception methods related to UI.
 *
 * @author Madhusudhan
 */
public class GenericUIException extends GenericException {
	static final long serialVersionUID = 1L;
	
	public GenericUIException(String message, WebDriver driver) throws DataSourceException, IOException, Exception {
		super(message);
		//CommonHelper.takeScreenShot(driver);@somu
	}
	
	public GenericUIException(String msg) {
		super(msg);	
	}
	
	public GenericUIException(Throwable cause) {
		super(cause);
	}

	public GenericUIException(String message, Throwable cause) {
		super(message, cause);
	}
}