package com.jinengo.routengenerator.controller;

import java.util.ArrayList;

import org.w3c.dom.Document;

import com.jinengo.routengenerator.api.ApiRequest;
import com.jinengo.routengenerator.infrastructure.DestinationGenerator;
import com.jinengo.routengenerator.infrastructure.RouteProcessor;
import com.jinengo.routengenerator.model.UserModel;
import com.jinengo.routengenerator.service.MSSQLConnectionHandler;
import com.jinengo.routengenerator.service.UserHandler;

public class MainController {
	private UserHandler userHandler;
	
	public MainController() {
		this.userHandler = new UserHandler();
	}
	
	/**
	 * main function to generate Routes
	 * query user ids and user details
	 * select routes depending on user preferences and save to db
	 */
	public void generateData() {
		// generate list of user id's for calculating route
		ArrayList<String> userIds = generateUserIds();
		
		//get detailed user information for user id's
		ArrayList<UserModel> userList = generateUserList(userIds);
		
		
		// generate route for specific user
		// generateRoute();
		
		// close sql connection if open
		MSSQLConnectionHandler.closeInstance();
	}
	
	/**
	 * generate list of user ID's from active user
	 * 
	 * @return list of user ID's
	 */
	private ArrayList<String> generateUserIds() {
		return this.userHandler.generateUserIds();
	}
	
	/**
	 * generate list of user details depending in given user ID
	 * 
	 * @param userIds - List of user ID's
	 * @return list of user
	 */
	private ArrayList<UserModel> generateUserList(ArrayList<String> userIds) {
		return this.userHandler.generateUserList(userIds);
	}
	
	/**
	 * generate routes depending on selected user from user list
	 */
	private void generateRoute() {
    	
    	// generate random destinations
		DestinationGenerator dm = new DestinationGenerator(); 
		ArrayList<String> destinations = dm.getRandomDestinations();
		
		// generate route depending on destinations
		ApiRequest ar = new ApiRequest();
		Document doc = ar.getXmlRouteDocument(destinations.get(0), destinations.get(1), "0");
		
		// process xml result and save it in route object
		RouteProcessor rp = new RouteProcessor();
		rp.processXmlDocument(doc);
	};
}
