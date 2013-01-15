package com.jinengo.routengenerator.service.helper;

import java.util.ArrayList;


/**
 * Generate random start and end destination from csv list
 * 
 * @author lars & christopher
 *
 */
public class DestinationGenerator {
	
	public static ArrayList<String> getRandomDestinations() {
		ArrayList<String> res = new ArrayList<String>();
    	java.util.Random random = new java.util.Random();
    	
    	// read destinations from CSV file
		CSVReader destinations = new CSVReader();
		ArrayList<String> destinationresult = destinations.csvhandler("destinations.csv");
		
		// get random number for start and end position
		int entrys = destinationresult.size();
		int start = random.nextInt(entrys);
		int end = random.nextInt(entrys);
		
		// verify that start and end position are not equal
		while(start==end){
			end = random.nextInt(entrys);
    	}
		
		// get start and end address and save to result array
		res.add(destinationresult.get(start).toString());
		res.add(destinationresult.get(end).toString());
		
		return res;
	}
}
