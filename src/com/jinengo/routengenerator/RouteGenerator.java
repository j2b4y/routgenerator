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
		int activeUser = 10; 
		int maxUser = 10;
		
		// initialize main controller an generate data
		MainController mc = new MainController();
		
		// generate Data for maxUser count, active user drive more often
		mc.generateData(activeUser, maxUser);

	}
}
