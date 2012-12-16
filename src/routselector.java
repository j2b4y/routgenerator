import java.sql.ResultSet;
import java.util.ArrayList;

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
	
	
	

}
