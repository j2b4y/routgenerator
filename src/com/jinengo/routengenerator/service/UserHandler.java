package com.jinengo.routengenerator.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jinengo.routengenerator.model.UserModel;

/**
 * Handle Database Request for User Details
 * 
 * @author lars & christopher
 *
 */
public class UserHandler {
	private UserDAO userDao;
	
	/**
	 * default constructor
	 * init user data access object and query handler
	 */
	public UserHandler() {
		this.userDao = new UserDAO(new QueryHandler());
	}
	
	/**
	 * generate a list of UserID's
	 * 
	 * @return List of UserID's
	 */
	public ArrayList<String> generateUserIds() {
		ArrayList<String> userList = new ArrayList<String>();

		try {
			userList = this.userDao.getMostActiveUser();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userList;
	}
	
	/**
	 * Query User details for every given ID and return them as list
	 * 
	 * @param userIds - List of user ids
	 * @return list with user details
	 */
	public ArrayList<UserModel> generateUserList(ArrayList<String> userIds) {
		ArrayList<UserModel> userList = new ArrayList<UserModel>();

		try {
			for (String userId : userIds) {
				userList.add(this.userDao.getUserData(userId));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userList;
	}
	
}
