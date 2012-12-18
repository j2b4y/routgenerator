package com.jinengo.routengenerator.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jinengo.routengenerator.model.UserModel;

public class UserDAO {
	private QueryHandler queryHandler;
	private String tableUserName;
	
	public UserDAO(QueryHandler queryHandler) {
		this.queryHandler = queryHandler;
		this.tableUserName = "A_SOURCE_JinengoUser";
	}
	
	public ArrayList<String> getMostActiveUser() throws SQLException {
		
		ArrayList<String> userIds = new ArrayList<String>();
		
		String sqlQuery = 	"SELECT TOP 5 ID, (SELECT COUNT(1) FROM Route WHERE userID = u.ID ) RouteCount "
						 	+ "FROM " + this.tableUserName + " u "
						 	+ "ORDER BY RouteCount DESC";
		
		ResultSet res = this.queryHandler.selectSomething(sqlQuery);
		
		
		while (res.next()) {
			userIds.add(res.getString("ID"));
		}
		
		return userIds;
	}
	
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
	
	private ResultSet getUserPreferences(String userId) {
		String sqlQuery = "SELECT sustainabilityPreference, comfortPreference, costsPreference, timePreference " 
						+ "FROM preferences " 
						+ "WHERE userID = " + userId;
		
		return this.queryHandler.selectSomething(sqlQuery);
		
	}
	
	private ResultSet getBasicUserData(String userId) {
		String sqlQuery = "SELECT ID, incomeRangeID, familyStatusID, ownsPEV, ownsGasCar, ownsEbike, publicTransportMember, railMembershipID, maxDistanceToWalk, maxDistanceToBike "
						+ "FROM " + this.tableUserName + " "
						+ "WHERE ID = " + userId;
		
		return this.queryHandler.selectSomething(sqlQuery);
		
	}
	
	public UserModel getUserData(String userId) throws SQLException {
		
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
