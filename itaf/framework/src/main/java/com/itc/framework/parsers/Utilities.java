package com.itc.framework.parsers;

import java.io.File;
import java.util.ArrayList;

import com.itc.framework.loggers.Log;

/**
 * Utility class with helper methods
 */
public class Utilities {
	
	/**
	 * Recursive Directory traversal method to traverse the given directory and
	 * search for file name containing the string passed
	 *
	 * @param strDirPath Directory path to be recursively traversed
	 * @param strFileNameContains String containing the file name pattern to be searched for
	 * @param listOfFileToParse List of all files that matched the pattern
	 */
	public static void dirTraverse( String strDirPath , String strFileNameContains, ArrayList<String> listOfFileToParse ) {
		
		File rootDir = new File( strDirPath );
		
		if( ! rootDir.exists())
			Log.error("ERROR: Directory " + rootDir.getAbsolutePath() + "\\" + rootDir.getName() + " does not exist");
		
		else { 
			            
			File childNodes[] = rootDir.listFiles();             
			for (File childNode : childNodes){	
				if( childNode.isDirectory() )
					dirTraverse(childNode.getAbsolutePath(), strFileNameContains, listOfFileToParse);
				else
					if( childNode.getName().contains(strFileNameContains) )
						listOfFileToParse.add(childNode.getAbsolutePath() );
							
			}
		} 
		
	}   
		
}

