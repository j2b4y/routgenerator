package com.jinengo.routengenerator.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jinengo.routengenerator.model.UserModel;
import com.jinengo.routengenerator.service.helper.QueryHandler;

/**
 * Data Access Object for User Details
 * 
 * @author lars & christopher
 */
public class UserDAO {
	private QueryHandler queryHandler;
	private String tableUserName;
	
	/**
	 * Default Constructor
	 * Initialize query handler and default table name
	 * 
	 * @param queryHandler - to handle database queries
	 */
	public UserDAO(QueryHandler queryHandler) {
		this.queryHandler = queryHandler;
		this.tableUserName = "A_SOURCE_JinengoUser";
	}
	
	/**
	 * Get a list of users who drive most often
	 * 
	 * @return user list
	 * @throws SQLException
	 */
	public ArrayList<String> getMostActiveUserIds(int maxCnt) throws SQLException {
		
		ArrayList<String> userIds = new ArrayList<String>();
		
		String sqlQuery = 	"SELECT TOP " + maxCnt + " ID, (SELECT COUNT(1) FROM Route WHERE userID = u.ID ) RouteCount "
						 	+ "FROM " + this.tableUserName + " u "
						 	+ "ORDER BY RouteCount DESC";
		
		ResultSet res = this.queryHandler.selectSomething(sqlQuery);
			
		while (res.next()) {
			userIds.add(res.getString("ID"));
		}
		
		return userIds;
	}
	
	/**
	 * Get a list of all user ids
	 * 
	 * @return user list
	 * @throws SQLException
	 */
	public ArrayList<String> getAllUserIds(int maxCnt) throws SQLException {
		
		ArrayList<String> userIds = new ArrayList<String>();
		
		String sqlQuery = 	"SELECT TOP " + maxCnt + " ID "
						 	+ "FROM " + this.tableUserName;
		
		ResultSet res = this.queryHandler.selectSomething(sqlQuery);
			
		while (res.next()) {
			userIds.add(res.getString("ID"));
		}
		
		return userIds;
	}
	
	/**
	 * get the car sharing id for a given user id
	 * 
	 * @param userId
	 * @return car sharing id
	 * @throws SQLException
	 */
	public int getCarSharingId(String userId) throws SQLException {
		int carSharingId = -1;
		
		String sqlQuery = "SELECT carSharingProviderID FROM CarSharingMembership "
						+ "WHERE userID=" + userId;
		
		ResultSet res = this.queryHandler.selectSomething(sqlQuery);
		if(res.next()) {
			carSharingId = res.getInt("carSharingProviderID");
		}
		return carSharingId;
	}
	
	/**
	 * get the user preference for a given user id
	 * 
	 * @param userId
	 * @return ResultSet with user preferences
	 */
	private ResultSet getUserPreferences(String userId) {
		String sqlQuery = "SELECT sustainabilityPreference, comfortPreference, costsPreference, timePreference " 
						+ "FROM preferences " 
						+ "WHERE userID = " + userId;
		
		return this.queryHandler.selectSomething(sqlQuery);
	}
	
	/**
	 * get the basic user data for a given user id
	 * 
	 * @param userId
	 * @return ResultSet with basic user data
	 */
	private ResultSet getBasicUserData(String userId) {
		String sqlQuery = "SELECT ID, incomeRangeID, familyStatusID, ownsPEV, ownsGasCar, ownsEbike, publicTransportMember, railMembershipID, maxDistanceToWalk, maxDistanceToBike "
						+ "FROM " + this.tableUserName + " "
						+ "WHERE ID = " + userId;
		
		return this.queryHandler.selectSomething(sqlQuery);
	}
	
	/**
	 * Construct a detailed UserModel
	 * 
	 * @param userId
	 * @return UserModel
	 * @throws SQLException
	 */
	public UserModel getUserData(String userId, boolean isActive) throws SQLException {
		
		// create new UserModel
		UserModel userModel = new UserModel();
		
		// set basic user data to UserModel
		ResultSet res = getBasicUserData(userId);
		if(res.next()){
			userModel.setID(res.getInt("ID"));
			userModel.setIncomeRange(res.getInt("incomeRangeID"));
			userModel.setFamilyStatus(res.getInt("familyStatusID"));
			userModel.setOwnsPEV(res.getBoolean("ownsPEV"));
			userModel.setOwnsGasCar(res.getBoolean("ownsGasCar"));
			userModel.setOwnsEbike(res.getBoolean("ownsEbike"));
			userModel.setPublicTransportMember(res.getBoolean("publicTransportMember"));
			userModel.setRailMembership(res.getInt("railMembershipID"));
			userModel.setMaxDistanceToWalk(res.getInt("maxDistanceToWalk"));
			userModel.setMaxDistanceToBike(res.getInt("maxDistanceToBike"));
			userModel.setActiveUser(isActive);
			System.out.println("daten generiert für user id: " + userModel.getID());
		} else {
			System.out.println("Keine User Daten gefunden für User: " + userId);
		}
		
		// set car sharing membership to UserModel
		userModel.setCarSharingMember(getCarSharingId(userId));
		
		// set preferences to UserModel
		res = getUserPreferences(userId);
		if(res.next()){
			userModel.setSustainabilityPreference(res.getFloat("sustainabilityPreference"));
			userModel.setComfortPreference(res.getFloat("comfortPreference"));
			userModel.setCostsPreference(res.getFloat("costsPreference"));
			userModel.setTimePreference(res.getFloat("timePreference"));
		} else {
			System.out.println("Keine User Preferenzen gefunden für User: " + userId);
		}
		
		return userModel;
	}
}
