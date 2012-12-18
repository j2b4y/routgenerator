package com.jinengo.routengenerator.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The MSSQL connection to the jinengo database
 * 
 * @author lars & christopher
 *
 */
public class MSSQLConnectionHandler {
	private static Connection conn = null;
    private static String dbHost = "134.106.13.63";
    private static String dbPort = "1433";
    private static String database = "JinengoOperationalCRM";
    private static String dbUser = "jinengo";
    private static String dbPassword = "pgbi32!";
	
    /**
     * Initialize Database Connection
     */
	private MSSQLConnectionHandler() {
		
		try{	
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			conn = DriverManager.getConnection("jdbc:sqlserver://" + dbHost + ":"
                    + dbPort + ";databaseName=" + database + ";user=" +  dbUser + ";password="
                    + dbPassword);
			System.out.println("connection opened");
		}
		catch(ClassNotFoundException e){
			System.out.println("Treiber nicht gefunden");
		}
		catch(SQLException e){
			System.out.println("Connect nicht moeglich");
		}
	}
	
	/**
	 * Singleton Connection Factory
	 * 
	 * @return MSSQLConnection Instance
	 */
	public static Connection getInstance() {
		if(conn == null){
			new MSSQLConnectionHandler();
		}	
		return conn;
	}
	
	/**
	 * Close Connection Instance
	 */
	public static void closeInstance() {
		if(conn != null){
			try {
				conn.close();
				System.out.println("Connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Connection close Error");
			}
			conn = null;
		}	
	}
}
