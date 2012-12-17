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
	
		String query = "SELECT DISTINCT(RequestID) FROM [JinengoOperationalCRM_Copy].[dbo].[temprouts] WHERE UserID="+userID;
		ResultSet res = MSSQLConnection.selectSomething(query);
		
		try{
			
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
				
				int cheapestroute;
				if(brailMembership){
					//Folgende Zeilen nur fuer den Fall, dass mal mit verschiedenen Rabatten gerechnet wird, diese muessten dann aber 
					//numerisch festgehalten werden!
//					String railquery = "SELECT railMembershiptID FROM [JinengoOperationalCRM_Copy].[dbo].[JinengoUser] WHERE ID="+userID;
//					ResultSet raimember = MSSQLConnection.selectSomething(railquery);
					
					ResultSet cost = MSSQLConnection.selectSomething(costquery);
					cost.next();
					cheapestroute = cost.getInt(1)/2;
				}
				else{
					ResultSet cost = MSSQLConnection.selectSomething(costquery);
					cost.next();
					cheapestroute = cost.getInt(1);
				}
				
				ResultSet comf = MSSQLConnection.selectSomething(comfquery);
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
				
//				ResultSet time = MSSQLConnection.selectSomething(timequery);

				System.out.println("Nachhaltigste Route: "+mostsusroute+" --- Guenstigste Route: "+cheapestroute+" --- Komfortabelste Route: "+mostcomfroute);
				choose(userID, reqID, mostsusroute, cheapestroute, mostcomfroute);
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public void choose(int userID, int reqID, int mostsusroute, int cheapestroute, int mostcomfroute){
		
		float sustainableratio = sustainable/(sustainable+comftable+cost+time);
		float comftableratio = comftable/(sustainable+comftable+cost+time);
		float costratio = cost/(sustainable+comftable+cost+time);
		float timeratio = time/(sustainable+comftable+cost+time);
		System.out.println("Nachhaltigkeit: "+sustainableratio+" --- Komfort: "+comftableratio+" --- Kosten: "+costratio+" --- Zeit: "+timeratio);
		
		Random Random = new Random();
		float random=Random.nextFloat();
		
		System.out.println("Random: "+random);

		if(random<sustainableratio){
			System.out.println("---> Nachhaltige Route!");
			float emission = 0;
			String emissionreq = "SELECT emission FROM [JinengoOperationalCRM_Copy].[dbo].[temprouts] " +
					"WHERE UserID="+userID+" AND RequestID="+reqID+" AND TripID="+mostsusroute;
			ResultSet emissionrequest = MSSQLConnection.selectSomething(emissionreq);
			try{
				emissionrequest.next();
				emission = emissionrequest.getFloat(1);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			//Beispielhafter Aufruf der ersten Advantage/Disadvantage Fkt.
			// fuer Time und Komfort nicht ganz so leicht zu berechnen!
			System.out.println("Advantage: "+advantagecalculator.getEmissionAdv(userID, reqID, emission));
			System.out.println("Disadvantage: "+advantagecalculator.getEmissionDisAdv(userID, reqID, emission));
			
			/* weitere Ausnahmeregelungen wie Einbeziehung von maxDistancetoWalk folgen
			*  Datenbank Insert Statement fehlt noch, da ich noch wissen muss, wie die finalen Tabellen Route und Subroute aussehen
			*  --> Dabei am besten darauf achten, welchen Daten wir von der REST API bekommen
			*/
			
		}
		if(random>sustainableratio&&random<sustainableratio+comftableratio){
			System.out.println("---> Komfortable Route!");
			// Weiteres siehe erstes if
		}
		if(random>sustainableratio+comftableratio&&random<sustainableratio+comftableratio+costratio){
			System.out.println("---> Guenstige Route!");
			// Weiteres siehe erstes if
		}
		if(random>sustainableratio+comftableratio+costratio){
			System.out.println("---> Schnelle Route!");
			// Weiteres siehe erstes if
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
	
	// Platzhalter fuer kompliziertere Rabattberechnungen
	public float getCostRating(int routeID){
		float price=0;

		/* ToDo:
		 * Code
		 */
		
		return price;
	}
	

}
