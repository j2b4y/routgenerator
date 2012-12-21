package com.jinengo.routengenerator.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
			maxId = res.getInt("carSharingProviderID");
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
			maxId = res.getInt("carSharingProviderID");
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
	
	public void insertRout( int userID, int requestID, int routeID, String costs, String distance, String emissions, String traveltime){
		
		Connection conn = MSSQLConnectionHandler.getInstance();
		
		int needID = 1;
		
		//Problem mit traveltime
		traveltime = "";
	 
		if(conn!=null){
			Statement query;
			try{
				query = conn.createStatement();
                
                String sql = "insert into [JinengoOperationalCRM_Copy].[dbo].[temprouts] ([UserID], [RequestID], [TripID], [cost], [distance], [emission], " +
                		"[traveltime], [NeedID]) values ( '"+userID+"','"+requestID+"','"+routeID+"','"+Float.valueOf(costs)+"'," +
                				"'"+Float.valueOf(distance)+"','"+Float.valueOf(emissions)+"','"+traveltime+"','"+needID+"')";
               
                query.executeUpdate(sql);
			}
			catch (SQLException e){
				System.out.println("SQL-Fehler: " + e);
                e.printStackTrace();
			}
			
		}
		
	}
}
