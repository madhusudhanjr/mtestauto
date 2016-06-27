package com.itc.framework.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;

import com.itc.framework.loggers.Log;


/**
 * The Class OpenOfficeUtil, is a generic open office file parser that parses the data in the spreadsheet
 * based on rows and columns.
 * This contains the methods for obtaining ObjectLocator Map ,Test data Map and Test Data for 
 * various tabs(items,services..etc) 
 * <p>
 * First Line/Row of the Open Office spread sheet is treated as header containing the column names.
 * 
 * 
 * Format of Open Office spreadsheet                 
 * 
 * <--------------- Web Object Map ------------------><---------TestData---------->
 * ----------------------------------------------------------------------------------
 *  LogicalName | LocatorType | Locator | ElementType | Customer1 | Customer2 | ...
 *  ---------------------------------------------------------------------------------
 *              |             |         |             | DataSetId | DataSetId |
 *  ---------------------------------------------------------------------------------
 *    xxxx      |      ID     | Submit  |   Button    |           |           |	
 *    
 *    
 *    
 * 
 * @author ITC Infotech
 * 
 */
public class OpenOfficeUtil {

	/**
	 * This method returns the object map Map<String,String>. 
	 * Key is the logical name 
	 * Value is locator type and locator separated with comma.
	 * @param workBook
	 * @param sheetName
	 * @return Map<String,String>
	 */
	public static ArrayList<ArrayList<String>> readObjectMap (InputStream workSheetStream , String sheetName) {

		ArrayList<ArrayList<String>> listOfRows = null;

		try{

			SpreadsheetDocument document = SpreadsheetDocument.loadDocument(workSheetStream);
			Table 				sheet = document.getTableByName(sheetName);

			if(sheet != null){

				listOfRows = new ArrayList<ArrayList<String>>();
				int columnCount = 0;
				for (int x = 0 ; 
						!sheet.getRowByIndex(x).getCellByIndex(0).getStringValue().isEmpty();
						x++ ){
					
					Row row = sheet.getRowByIndex(x);
					
					// Get the number of used columns in the workbook
					if( 0 == x ){
						for( ; !row.getCellByIndex(columnCount).getStringValue().isEmpty(); columnCount++ );		
					}
				
					String key = row.getCellByIndex(0).getStringValue();
					if( null == key ){
						Log.error("No Data Found at Row Number " + x + " column 0");
						continue;
					}
						
					ArrayList<String> rowValues = new ArrayList<String>();
					
					for ( int y = 0 ; y < columnCount ;	y++ ){
						rowValues.add(row.getCellByIndex(y).getStringValue());					
					}
					listOfRows.add(rowValues);

				}

			}
			else{
				Log.error("Work sheet is not loaded properly", new IOException());
			}


		}
		catch(Exception e){
			e.printStackTrace();
		}
		return listOfRows;
	}
}