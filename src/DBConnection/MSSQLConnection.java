package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MSSQLConnection {
	
	private static Connection conn = null;
    private static String dbHost = "134.106.13.63";
    private static String dbPort = "1433";
    private static String database = "JinengoOperationalCRM_Copy";
    private static String dbUser = "jinengo";
    private static String dbPassword = "pgbi32!";
	
	private MSSQLConnection(){
		
		try{
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			
			conn = DriverManager.getConnection("jdbc:sqlserver://" + dbHost + ":"
                    + dbPort + ";databaseName=" + database + ";user=" +  dbUser + ";password="
                    + dbPassword);
			
		}
		catch(ClassNotFoundException e){
			System.out.println("Treiber nicht gefunden");
		}
		catch(SQLException e){
			System.out.println("Connect nicht moeglich");
		}
		
	}
	
	private static Connection getInstance()
	{
		if(conn == null){
			new MSSQLConnection();
		}	
		return conn;
	}
	
	public static ResultSet selectSomething(String expression){
    	
    	conn = getInstance();
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
