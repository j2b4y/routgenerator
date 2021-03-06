package com.jinengo.routengenerator.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jinengo.routengenerator.model.UserModel;
import com.jinengo.routengenerator.service.helper.QueryHandler;

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
	 * Query User details for every given ID and return them as list
	 * 
	 * @return list with user details
	 */
	public ArrayList<UserModel> generateUserList(int activeUser, int maxUser) {
		ArrayList<UserModel> userList = new ArrayList<UserModel>();
		boolean isActive = false;
		
		try {
			// generate list of user id's for calculating route
			ArrayList<String> userIds = this.userDao.getAllUserIds(maxUser);
			
			// generate list of active user id's to get more route for active user
			ArrayList<String> activeUserIds = this.userDao.getMostActiveUserIds(activeUser);
			
			for (String userId : userIds) {
				isActive = activeUserIds.indexOf(userId) != -1;
				userList.add(this.userDao.getUserData(userId, isActive));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userList;
	}
	
}
