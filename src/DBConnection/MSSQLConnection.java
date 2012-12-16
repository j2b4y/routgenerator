package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

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
	
	public static void insertRout( int userID, int requestID, int routeID, String costs, String distance, String emissions, String traveltime){
		
		conn = getInstance();
		
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
	
	public static void insertSubRoute(int subrouteID, int routeID, String ssubcosts,  String ssubdistance, String ssubemissions, String ssubtraveltime, String ssubtransport, String ssubstart, String ssubstarttime, String ssubend, String ssubendtime ){
		
		conn = getInstance();
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
