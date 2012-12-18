package com.jinengo.routengenerator.controller;

import java.util.ArrayList;

import org.w3c.dom.Document;

import com.jinengo.routengenerator.api.ApiRequest;
import com.jinengo.routengenerator.infrastructure.DestinationGenerator;
import com.jinengo.routengenerator.infrastructure.RouteProcessor;
import com.jinengo.routengenerator.infrastructure.SpecificRouteGenerator;
import com.jinengo.routengenerator.model.UserModel;
import com.jinengo.routengenerator.service.MSSQLConnectionHandler;
import com.jinengo.routengenerator.service.UserHandler;

/**
 * Main Controller to handle the data generation process
 * 
 * @author lars & christopher
 *
 */
public class MainController {
	private UserHandler userHandler;
	
	/**
	 * default constructor
	 * initialize user handler to create user object list
	 */
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
		
		// generate routes for a given userList
		generateRoutes(userList);
		
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
	 * generate specific routes for a given user list
	 */
	private void generateRoutes(ArrayList<UserModel> userList) {
    	SpecificRouteGenerator routeGen = new SpecificRouteGenerator(); 
    	
    	for (UserModel userModel : userList) {
    		routeGen.generateSpecificRoute(userModel);
		}
		
	};
	
}
