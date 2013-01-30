package com.jinengo.routengenerator.controller;

import java.util.ArrayList;
import java.util.Random;

import com.jinengo.routengenerator.model.UserModel;
import com.jinengo.routengenerator.service.UserHandler;
import com.jinengo.routengenerator.service.helper.ApiErrorException;
import com.jinengo.routengenerator.service.helper.MSSQLConnectionHandler;

/**
 * Main Controller to handle the data generation process
 * 
 * @author lars & christopher
 * 
 */
public class MainController {
	private UserHandler userHandler;

	/**
	 * default constructor initialize user handler to create user object list
	 */
	public MainController() {
		this.userHandler = new UserHandler();
	}

	/**
	 * Main function to generate Routes query user ids and user details select
	 * Generate routes depending on user preferences and save to db
	 * 
	 * @param activeUser
	 * @param maxUser
	 */
	public void generateData(int activeUser, int maxUser) {
		// get a list of detailed users
		ArrayList<UserModel> userList = generateUserList(activeUser, maxUser);

		// generate routes for a given userList
		generateRoutes(userList);

		// close sql connection if open
		MSSQLConnectionHandler.closeInstance();
	}

	/**
	 * generate list of user details depending in given user ID
	 * 
	 * @return list of user
	 */
	private ArrayList<UserModel> generateUserList(int activeUser, int maxUser) {
		return this.userHandler.generateUserList(activeUser, maxUser);
	}

	/**
	 * Generate specific routes for a given user list
	 * 
	 * @param userList
	 */
	private void generateRoutes(ArrayList<UserModel> userList) {
		RouteController routeController = new RouteController();
		Random rnd = new Random();

		// generate userspecific routes
		try {
			for (UserModel userModel : userList) {
				if (userModel.isActiveUser()) {
					// Drive max 3 times a day if active
					int routesPerDay = rnd.nextInt(3) + 1;
					for (int i = 0; i < routesPerDay; i++) {
						routeController.generateSpecificRoute(userModel);
					}
				} else {
					// Drive only every third day if not active
					int prblty = rnd.nextInt(2) + 1;
					if (prblty == 2) {
						routeController.generateSpecificRoute(userModel);
					}
				}
			}
		} catch (ApiErrorException e) {
			// Error at Jinengo API
			e.printStackTrace();
		}
	}
}
