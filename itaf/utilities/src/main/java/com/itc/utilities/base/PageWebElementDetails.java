package com.itc.utilities.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.itc.framework.loggers.Log;
import com.itc.framework.parsers.OpenOfficeUtil;

/**
 * Utility Class to read and return the Mapping of Logical name of web elements to its details and Test data
 * <p>
 * First Line/Row of the Open Office spread sheet is treated as header containing the column names.
 * 
 * 
 * Format of Open Office spreadsheet                 
 * 
 * <--------------- Web Object Map ------------------><---------TestData---------->
 * 		----------------------------------------------------------------------------------
 *ROW 0	LogicalName | LocatorType | Locator | ElementType | Customer1 | Customer2 | ...
 *  	---------------------------------------------------------------------------------
 *ROW 1  TestData  	|             |         |             | DataSetId | DataSetId |
 *  	---------------------------------------------------------------------------------
 *ROW 2	  xxxx      |      ID     | Submit  |   Button    |           |           |	
 *ROW 3	  yyyy      |      XPATH  | Approve |   Button    |           |           |	    
 *.........................
 *
 * @author ITC Infotech
 * 
 */
public class PageWebElementDetails {
	
	public static final int LOGICAL_NAME_COL_NO	= 0;
	public static final int LOCATOR_TYPE_COL_NO	= 1;
	public static final int ELEMENT_TYPE_COL_NO	= 2;
	
	public static final int LOCATOR_WEB_BROWSER_COL_NO = 3;
	public static final int LOCATOR_MOBILE_BROWSER_COL_NO = 4;
	public static final int LOCATOR_ANDROID_NATIVE_COL_NO = 5;
	
	public static final int TESTDATA_START_COL_NO = 6;
	
	public static final int OBJECTMAP_START_ROW_NO	= 2;
	
	/**
	 * 	Method to read the data in Web Element Details workbook and create
	 *  a map of <key,value> -> <Logical Name , List of Data in All other columns >. Method also 
	 *  creates a Map of Customer Name : Test Case ID to index of data in the workbook if the testdata
	 *  exists
	 *  
	 * @param workBookName
	 * @param sheetName
	 * @return
	 */
	public static void createObjectMap(String workBookName , String sheetName,
			InputStream thisPageInputStream ,
			Map<String,List<String>> pagewebElementMap , 
			Map<String,Integer> testDataIndex ){
		
		Log.debug("Reading Object Map File : " + workBookName + ":" + sheetName );		
		
		if( null == thisPageInputStream )
			Log.error("Unable to read Workbook " + workBookName , new IOException());
		
		ArrayList<ArrayList<String>> tmpPageList = OpenOfficeUtil.readObjectMap(thisPageInputStream, sheetName);
		
		if( null != tmpPageList ){
			// Read Row 0 & 1 as it contains the Header information
			int nowOfRows = tmpPageList.size();
			int numOfColumns = tmpPageList.get(0).size();
			Log.debug(nowOfRows + " rows and " + numOfColumns + " were read from the web element map file" );
			
			// Create a Map of CustomerName : TestData ID and its corresponding Column Number
			if( null != testDataIndex )
				for ( int x = TESTDATA_START_COL_NO ; x < numOfColumns ; x++ )
					testDataIndex.put( tmpPageList.get(0).get(x) + ":" + tmpPageList.get(1).get(x) , x );
			
			// Now Copy rest of the List into a Map with Value in 0 index as key
			// and rest as value
			for( int y = OBJECTMAP_START_ROW_NO ; y < nowOfRows ; y++ )			
				pagewebElementMap.put(tmpPageList.get(y).get(LOGICAL_NAME_COL_NO), tmpPageList.get(y).subList(1, numOfColumns));
			
		}		
		
	}
	
}
