package com.jinengo.routengenerator.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jinengo.routengenerator.model.RouteModel;
import com.jinengo.routengenerator.model.SubrouteModel;
import com.jinengo.routengenerator.service.helper.MSSQLConnectionHandler;
import com.jinengo.routengenerator.service.helper.QueryHandler;

public class RouteDAO {
	private QueryHandler queryHandler;
	private String tableRoute;
	private String tableSubRoute;
	
	public RouteDAO(QueryHandler queryHandler) {
		this.queryHandler = queryHandler;
		this.tableRoute = "A_SOURCE_Route";
		this.tableSubRoute = "A_SOURCE_Subroute";
	}
	
	/**
	 * get max route id from database
	 * 
	 * @return max route id
	 * @throws SQLException
	 */
	public int getMaxRouteId() throws SQLException {
		int maxId = 0;
		
		String sqlQuery = "SELECT MAX(ROUTE_ID) maxID FROM " + this.tableRoute + " ";
		
		ResultSet res = this.queryHandler.selectSomething(sqlQuery);
		if(res.next()) {
			maxId = res.getInt("maxID");
		}
		return maxId;
	}
	
	/**
	 * get max subroute id from database
	 * 
	 * @return max subroute id
	 * @throws SQLException
	 */
	public int getMaxSubRouteId() throws SQLException {
		int maxId = 0;
		
		String sqlQuery = "SELECT MAX(SUBROUTE_ID) maxID FROM " + this.tableSubRoute + " ";
		
		ResultSet res = this.queryHandler.selectSomething(sqlQuery);
		if(res.next()) {
			maxId = res.getInt("maxID");
		}
		return maxId;
	}
	
	public float getComfortRating(int id) throws SQLException {
		float comfRating = 0;
		
		String sqlQuery = "SELECT comfortRating FROM Transportation WHERE id = " + id;
		
		ResultSet res = this.queryHandler.selectSomething(sqlQuery);
		if(res.next()) {
			comfRating = res.getFloat("comfortRating");
		}

		return comfRating;
	}
	
	public void insertRoute(RouteModel routeModel) throws SQLException{
		
		Connection conn = MSSQLConnectionHandler.getInstance();
		Statement query;
		query = conn.createStatement();
                
        String sql = "INSERT INTO " + this.tableRoute + " VALUES ("
            		+ routeModel.getID() + ", "
            		+ routeModel.getUserID() + ", "
            		+ routeModel.getDepartureAddress() + ", "
            		+ routeModel.getDestinationAddress() + ", "
            		+ routeModel.getDepartureTime() + ", "
            		+ routeModel.getDestinationTime() + ", "
            		+ routeModel.getTotalDistance() + ", "
            		+ routeModel.getTotalCost() + ", "
            		+ routeModel.getTotalEmission() + ", "
            		+ 0 + ", "
            		+ 0 + ", "
            		+ 0 + ", "
            		+ 0 + ", "
            		+ 0 + ", "
            		+ 0 + ", "
            		+ 0 + ", "
            		+ 0 + ", "
            		+ 0 + ", "
            		+ routeModel.getTotalTime() + ", "
            		+ routeModel.getTotalTime() + ", "
            		+ ");";
                
        query.executeUpdate(sql);
		
	}
	
	public void insertSubRoute(SubrouteModel subrouteModel) throws SQLException{
		
		Connection conn = MSSQLConnectionHandler.getInstance();
		Statement query;
		query = conn.createStatement();
                
        String sql = "INSERT INTO " + this.tableRoute + " VALUES ("
        		+ subrouteModel.getID() + ", "
        		+ ");";
            
         query.executeUpdate(sql);
		
	}
}
