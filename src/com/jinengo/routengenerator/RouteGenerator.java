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
		MainController mainController = new MainController();
		
		// max User count
		int maxUser = 1000;
		// active user drive more often
		int activeUser = 30; 
		
		// start data generating process 
		mainController.generateData(activeUser, maxUser);

	}
}
