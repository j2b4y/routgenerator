import java.sql.ResultSet;

import DBConnection.MSSQLConnection;


public class advantagecalculator {
	
	public static float getEmissionAdv(int userID, int requestID, float emission){
		
		float advantage = 0;
		
		String query = "SELECT MAX(Emission) FROM dbo.temprouts where UserID="+userID+" AND RequestID="+requestID;
		ResultSet advantagereq = MSSQLConnection.selectSomething(query);
		float anothervalue=0;
		
		try{
			advantagereq.next();
			anothervalue = advantagereq.getFloat(1);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		advantage = anothervalue-emission;
		
		return advantage;
	}
	
	public static float getEmissionDisAdv(int userID, int requestID, float emission){
		
		float disadvantage = 0;
		
		String query = "SELECT MIN(Emission) FROM dbo.temprouts where UserID="+userID+" AND RequestID="+requestID;
		ResultSet disadvantagereq = MSSQLConnection.selectSomething(query);
		
		float anothervalue=0;
		
		try{
			disadvantagereq.next();
			anothervalue = disadvantagereq.getFloat(1);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		disadvantage = anothervalue-emission;
		
		return disadvantage;
	}
}
