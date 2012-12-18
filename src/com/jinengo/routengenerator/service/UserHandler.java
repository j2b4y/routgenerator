package com.jinengo.routengenerator.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jinengo.routengenerator.model.UserModel;

public class UserHandler {
	private UserDAO userDao;
	
	public UserHandler() {
		this.userDao = new UserDAO(new QueryHandler());
	}
	
	public ArrayList<String> generateUserIds() {
		ArrayList<String> userList = new ArrayList<String>();

		try {
			userList = this.userDao.getMostActiveUser();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userList;
	}
	
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
