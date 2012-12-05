import java.sql.ResultSet;

import DBConnection.MSSQLConnection;


public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String query = "SELECT * FROM [JinengoOperationalCRM_Copy].[dbo].[temprouts] WHERE userID =123";

    	ResultSet res = MSSQLConnection.selectSomething(query);
    	
    	try{
			while (res.next())
	        {			
				System.out.println("UserID: "+res.getInt("UserID")+" - RequestID: "+res.getInt("RequestID")+" - TripID: "+res.getInt("TripID"));
	        }
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
