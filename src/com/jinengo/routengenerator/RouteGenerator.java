package com.jinengo.routengenerator;
import com.jinengo.routengenerator.controller.MainController;

/**
 * Main Class to start route generation
 * 
 * @author lars & christopher
 *
 */
public class RouteGenerator {

	/**
	 * main function
	 * 
	 * @param args
	 */	
	public static void main(String[] args) {
		
		// initialize main controller an generate data
		MainController mc = new MainController();
		mc.generateData();
		
	}
}
