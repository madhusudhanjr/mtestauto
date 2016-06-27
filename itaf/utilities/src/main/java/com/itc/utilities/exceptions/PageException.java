package com.itc.utilities.exceptions;

public class PageException extends GenericException {

	static final long serialVersionUID = 1L;
	
	public PageException(String page, String msg){
		super("Exception thrown by "+page+"--> "+msg);
	}
	
	public PageException(String msg){
		super(msg);
	}
}
