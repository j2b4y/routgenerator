package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	public static void insertRout( String costs, String distance, String emissions, String traveltime){
		
		conn = getInstance();
		
		int userID = 1234;
		int requestID = 4567;
		int TripID = 98710;
		int needID = 1;
		
		traveltime = "";
	 
		
		if(conn!=null){
			Statement query;
			try{
				query = conn.createStatement();
                
                String sql = "insert into [JinengoOperationalCRM_Copy].[dbo].[temprouts] ([UserID], [TripID], [RequestID],[cost], [distance], [emission], " +
                		"[traveltime], [NeedID]) values ( '"+userID+"','"+requestID+"','"+TripID+"','"+Float.valueOf(costs)+"'," +
                				"'"+Float.valueOf(distance)+"','"+Float.valueOf(emissions)+"','"+traveltime+"','"+needID+"')";
               
                query.executeUpdate(sql);
			}
			catch (SQLException e){
				System.out.println("SQL-Fehler: " + e);
                e.printStackTrace();
			}
			
			
			
		}
		
	}
	
	public static void insertSubRoute(){
		
		conn = getInstance();
		
		if(conn!=null){
			Statement query;
			
			try{
				query = conn.createStatement();
				
				String sql = "insert into [JinengoOperationalCRM_Copy].[dbo].[tempsubrouts]";
				
				query.executeUpdate(sql);
			}
			catch (SQLException e){
				System.out.println("SQL-Fehler: " + e);
                e.printStackTrace();
			}
			
		}
	}
	
}
