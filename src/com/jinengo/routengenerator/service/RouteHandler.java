package com.jinengo.routengenerator.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RouteHandler {
	
	
	
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
	
	public void insertSubRoute(int subrouteID, int routeID, String ssubcosts,  String ssubdistance, String ssubemissions, String ssubtraveltime, String ssubtransport, String ssubstart, String ssubstarttime, String ssubend, String ssubendtime ){
		
		Connection conn = MSSQLConnectionHandler.getInstance();
		//Problem mit ssubtraveltime!
		ssubtraveltime = "";
		ssubstarttime = "";
		ssubendtime = "";
		
//		DateTime startdate = new DateTime(Long.parseLong(ssubstarttime));
//		DateTime enddate = new DateTime(Long.parseLong(ssubendtime));
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(conn!=null){
			Statement query;
			
			try{
				query = conn.createStatement();
				
				String sql = "insert into [JinengoOperationalCRM_Copy].[dbo].[tempsubrouts] ([SubroutenID], [TripID], [cost], [distance], [emission], [traveltime], [transpor" +
						"tation], [start], [starttime], [end], [endtime]) values ('"+subrouteID+"','"+routeID+"','"+Float.valueOf(ssubcosts)+"','"+Float.valueOf(ssubdistance)+"'" +
								",'"+Float.valueOf(ssubemissions)+"','"+ssubtraveltime+"','"+ssubtransport+"','"+ssubstart+"','"+ssubstarttime+"','"+ssubend+"','"+ssubendtime+"')";
				
				query.executeUpdate(sql);
			}
			catch (SQLException e){
				System.out.println("SQL-Fehler: " + e);
                e.printStackTrace();
			}
			
		}
	}
}
