package ReadRESTAPI;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import DBConnection.MSSQLConnection;

public class idgenerator {
	
	public int genRequestID(){
		
		int reqID = 0;
		
		System.out.println("------------- RequestID machen!");
		
		ResultSet result = MSSQLConnection.selectSomething("SELECT MAX(RequestID) FROM [dbo].[temprouts]");
		
		try {
			result.next();
			if(result.getInt(1)!=0){
				reqID = result.getInt(1);
				reqID ++;
//				System.out.println("RequestID: "+ reqID);
			}
			else{
				System.out.println("Tabelle leer!");
				reqID=1;
//				System.out.println("RequestID: "+ reqID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reqID;
	}

	public int genRoutID(){
		
		int routeID = 0;
		
		System.out.println("------------- RouteID machen!");
	
		ResultSet result = MSSQLConnection.selectSomething("SELECT MAX(TripID) FROM [dbo].[temprouts]");
		
		try {
			result.next();
			if(result.getInt(1)!=0){
				routeID = result.getInt(1);
				routeID ++;
//				System.out.println("RouteID: "+ routeID);
			}
			else{
				System.out.println("Tabelle leer!");
				routeID=1;
//				System.out.println("RouteID: "+ routeID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return routeID;
	}
	
	public int genSubRoutID(){
		
		int subrouteID = 0;
		
		System.out.println("-------------SubRouteID machen!");
		
		ResultSet result = MSSQLConnection.selectSomething("SELECT MAX(SubroutenID) FROM [dbo].[tempsubrouts]");
		
		try {
			result.next();
			if(result.getInt(1)!=0){
				subrouteID = result.getInt(1);
				subrouteID ++;
//				System.out.println("SubRouteID: "+ subrouteID);
			}
			else{
				System.out.println("Tabelle leer!");
				subrouteID=1;
//				System.out.println("SubRouteID: "+ subrouteID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return subrouteID;
	}
}
