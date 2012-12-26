package com.jinengo.routengenerator.controller;

import java.util.ArrayList;
import java.util.Random;

import com.jinengo.routengenerator.model.UserModel;
import com.jinengo.routengenerator.service.UserHandler;
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
	 * main function to generate Routes query user ids and user details select
	 * routes depending on user preferences and save to db
	 */
	public void generateData() {
		// get a list of detailed users
		ArrayList<UserModel> userList = generateUserList();

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
	private ArrayList<UserModel> generateUserList() {
		return this.userHandler.generateUserList();
	}

	/**
	 * generate specific routes for a given user list
	 */
	private void generateRoutes(ArrayList<UserModel> userList) {
		RouteController routeController = new RouteController();
		Random rnd = new Random();

		for (UserModel userModel : userList) {

			if (userModel.isActiveUser()) {
				// Drive max 3 times a day if active
				int routesPerDay = rnd.nextInt(3) + 1;
				for (int i = 0; i < routesPerDay; i++) {
					routeController.generateSpecificRoute(userModel);
				}
			} else {
				// Drive only every third day if not active
				int prblty = rnd.nextInt(3) + 1;
				if (prblty == 3) {
					routeController.generateSpecificRoute(userModel);
				}
			}
		}
	}
}
