package com.jinengo.routengenerator.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryHandler {
	public ResultSet selectSomething(String expression){
		
    	Connection conn = MSSQLConnectionHandler.getInstance();
    	ResultSet result = null;
    	
    	if (conn != null){
    		Statement query;
    		try{
    			query=conn.createStatement();
    			
    			result = query.executeQuery(expression);
    			
    		}
    		catch (SQLException e){
    			System.out.println("SQL-Fehler: " + e);
                e.printStackTrace();
    		}
    	}
		return result;
    }
}
