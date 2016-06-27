package com.itc.framework.helpers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Additional details required for iTAF test classes & Methods
 *
 * @author ITC Infotech 14 June 2013
 */

@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.TYPE,ElementType.METHOD}) 
public @interface TestDetail {
	
	/**
	 * This applies to the test method only. Refers to the test case ID on ALM
	 * which this method implements
	 */
	public String testCaseID() default "";
	
	/**
	 * This applies to the test method only. Refers to the "Test Description" on ALM
	 * which this method implements
	 */
	public String testCaseName() default "";
	
	/**
	 * This applies to test method only. Author of the test method
	 */
	public String author() default "";
	
	/**
	 * This applies to the Test class only. Name of the submodule to 
	 * which all the test methods in this class belong to
	 */
	public String submodule() default "";


}
