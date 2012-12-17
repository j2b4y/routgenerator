import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import DBConnection.MSSQLConnection;


class routselector {
	
	private float sustainable;
	private float comftable;
	private float cost;
	private float time;
	private boolean brailMembership;
	private boolean bpublicTransportMember;
	private boolean bownsSpecialVehicle;
	private boolean bcarsharingmember;
	private int imaxDistanceToWalk;
	private int imaxDistanceToBike;
	
	public void getUserPref(int userID){
		
		String query = "SELECT sustainabilityPreference, comfortPreference, costsPreference, timePreference FROM [JinengoOperationalCRM_Copy].[dbo].[Preferences] WHERE userID ="+userID;
		
		ResultSet res = MSSQLConnection.selectSomething(query);
		try{
			while (res.next())
	        {
				sustainable = res.getFloat("sustainabilityPreference");
				comftable = res.getFloat("comfortPreference");
				cost = res.getFloat("costsPreference");
				time = res.getFloat("timePreference");
				
				System.out.println("Nachhaltigkeit: "+sustainable+" - Komfort: "+comftable+" - Kosten: "+cost+" -Time: "+time);
	        }
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public void getUserMemberships(int userID){
		
		String query = "SELECT ownsPEV, ownsGasCar, ownsEbike, publicTransportMember, railMembershipID,  " +
				"maxDistanceToWalk, maxDistanceToBike FROM [JinengoOperationalCRM_Copy].[dbo].[JinengoUser] " +
				"WHERE ID ="+userID;
		String query2 = "SELECT carSharingProviderID FROM [JinengoOperationalCRM_Copy].[dbo].[CarSharingMembership]" +
				" WHERE userID="+userID; 
		
		ResultSet res = MSSQLConnection.selectSomething(query);
		ResultSet res2 = MSSQLConnection.selectSomething(query2);
		try{
			
			while(res.next()){
				
				boolean PEV = res.getBoolean("ownsPEV");
				boolean GasCar = res.getBoolean("ownsGasCar");
				boolean Ebike = res.getBoolean("ownsEbike");
				
				if(PEV | GasCar | Ebike){
					bownsSpecialVehicle = true;
				}
				
				bpublicTransportMember = res.getBoolean("publicTransportMember");

				if(res.getInt("railMembershipID")!=0){
					brailMembership = true;
				}
				
				imaxDistanceToWalk = res.getInt("maxDistanceToWalk");
				imaxDistanceToBike = res.getInt("maxDistanceToBike");
				bcarsharingmember= res2.wasNull();

				System.out.println("SpecialVehicle: "+bownsSpecialVehicle+" - PublicTransport: "+bpublicTransportMember+" - railMemeber: "+brailMembership+" - max Walkdistance: "+imaxDistanceToWalk+" - max Bikedistance: "+imaxDistanceToBike+" - CarSharingMember: "+bcarsharingmember );
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void routeassign(int userID){
		
		float sustainableratio = sustainable/(sustainable+comftable+cost+time);
		float comftableratio = comftable/(sustainable+comftable+cost+time);
		float costratio = cost/(sustainable+comftable+cost+time);
		float timeratio = time/(sustainable+comftable+cost+time);
		
		String query = "SELECT DISTINCT(RequestID) FROM [JinengoOperationalCRM_Copy].[dbo].[temprouts] WHERE UserID="+userID;
		ResultSet res = MSSQLConnection.selectSomething(query);
		
		try{
			
			boolean choosen = false;
//			float random = Random.nextFloat();
			
			while(res.next()){
				int reqID = res.getInt(1);
				
				String susquery = "SELECT TripID FROM [JinengoOperationalCRM_Copy].[dbo].[temprouts] where" +
						" UserId ="+userID+" AND RequestID="+reqID+" AND Emission=(SELECT MIN(Emission) FROM[JinengoOperationalCRM_Copy]" +
								".[dbo].[temprouts] where UserId ="+userID+" AND RequestID="+reqID+")";
				String costquery = "SELECT TripID FROM [JinengoOperationalCRM_Copy].[dbo].[temprouts] where" +
						" UserId ="+userID+" AND RequestID="+reqID+" AND Emission=(SELECT MIN(Cost) FROM[JinengoOperationalCRM_Copy]" +
						".[dbo].[temprouts] where UserId ="+userID+" AND RequestID="+reqID+")";
				String comfquery = "SELECT DISTINCT(TripID) FROM [JinengoOperationalCRM_Copy].[dbo].[temprouts] WHERE UserID="+userID+" AND RequestID="+reqID;
//				String timequery = "";
				
				ResultSet sus = MSSQLConnection.selectSomething(susquery);
				sus.next();
				int mostsusroute = sus.getInt(1);
				
				ResultSet cost = MSSQLConnection.selectSomething(costquery);
				cost.next();
				int cheapestroute = cost.getInt(1);
						
				ResultSet comf = MSSQLConnection.selectSomething(comfquery);
//				ResultSet time = MSSQLConnection.selectSomething(timequery);
				
				float rating = 0;
				int mostcomfroute = 0;
				
				while(comf.next()){
					int routeID = comf.getInt(1);
					if(rating<getComfRating(routeID)){
						rating = getComfRating(routeID);
						System.out.println("Komfort: "+rating);
						mostcomfroute = routeID;
					}
				}

				System.out.println("Nachhaltigste Route: "+mostsusroute+" --- Guenstigste Route: "+cheapestroute+" --- Komfortabelste Route: "+mostcomfroute);
				
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public float getComfRating(int routeID){
		float comfrating = 0;
		
		String transportations = "SELECT Transportation FROM [JinengoOperationalCRM_Copy].[dbo].[tempsubrouts] WHERE TripID ="+routeID;
		ResultSet transports = MSSQLConnection.selectSomething(transportations);
		
		try{
			while(transports.next()){
				String vehicle = transports.getString(1);
				
				String comfort = "SELECT comfortRating FROM [JinengoOperationalCRM_Copy].[dbo].[Transportation] WHERE classOrProviderName='"+vehicle+"'";
				ResultSet comf = MSSQLConnection.selectSomething(comfort);
				
				int counter = 0;
				
				while(comf.next()){
					comfrating += comf.getFloat(1);
					counter++;
				}
				float rating = comfrating/counter;
				return rating;
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return (Float)null;
		
	}
	

}
