package com.jinengo.routengenerator.service.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Standard CSV Reader
 * 
 * @author lars & christopher
 *
 */
public class CSVReader {
	
	/**
	 * Handle CSV
	 * 
	 * @param file - csv filename
	 * @return csvl list
	 */
	public ArrayList<String> csvhandler(String file){
	
	    FileReader myFile = null;
	    BufferedReader buff = null;
	    ArrayList<String> lines = new ArrayList<String>();
		
	    try {
	        myFile = new FileReader(file);
	        buff = new BufferedReader(myFile);
	        String line;
	        while ((line = buff.readLine()) != null) {
	            lines.add(line);
	        }
	    } catch (IOException e) {
	        System.err.println("Read CSV Error: " + e);
	    } finally {
	        try {
	            buff.close();
	            myFile.close();
	        } catch (IOException e) {
	            System.err.println("Close Connection Error: " + e);
	        }
	    }
	 
		return lines;
	}

}
