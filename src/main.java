import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.List;
import java.util.Random;

import javax.xml.stream.XMLStreamException;

import DBConnection.MSSQLConnection;
import ReadRESTAPI.csvreader;
import ReadRESTAPI.htmlreader;
import ReadRESTAPI.staxxmlreader;


public class main {

	/**
	 * @param args
	 */
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Starten sie eine Aktion mittels Zahl:");
		System.out.println("1: Routen generieren und in DB schreiben! ");
		System.out.println("2: User-Preferences auslesen und Routen zuordnen");
		
		try{
			InputStreamReader input  = new InputStreamReader(System.in);
		    BufferedReader keyboardInput = new BufferedReader(input);
		    String eingabe;
		    eingabe = keyboardInput.readLine();
		    System.out.println("Sie haben "+Integer.parseInt(eingabe)+" gewaehlt!");
		    
		    if(Integer.parseInt(eingabe) == 1){
		
				String token = "7a2ba89a-787f-4b42-83cf-f990497e82c8";
				int userID = 123;
				
				csvreader destinations = new csvreader();
				List destinationresult = destinations.csvhandler("destinations.csv");
				
				int entrys = destinationresult.size();
				
				java.util.Random random = new java.util.Random();
				
				int start = random.nextInt(entrys);
				int end = random.nextInt(entrys);
				
				while(start==end){
					end = random.nextInt(entrys);
		    	}
				
				System.out.println("Start: "+destinationresult.get(start).toString());
				System.out.println("Ziel: "+destinationresult.get(end).toString());
				
				htmlreader readpage = new htmlreader();
				
				InputStream is = readpage.getRouteInfo(token, destinationresult.get(start).toString(), destinationresult.get(end).toString());
				
				staxxmlreader reader = new staxxmlreader();
				try{
					reader.streamreader(is, userID);
				}
				catch(XMLStreamException xmle){
					System.out.println(xmle);
				}
		    }
		    if(Integer.parseInt(eingabe) == 2){
		    	int userID = 123;
		    	
		    	routselector nutzer = new routselector();
		    	//Preferences & Memberships laden
		    	nutzer.getUserPref(userID);
		    	nutzer.getUserMemberships(userID);
		    	nutzer.routeassign(userID);
		    	
		    	
		    
		    }
		    if(Integer.parseInt(eingabe) == 3){
		    	
		    }
		}
		catch(IOException e){
			System.out.println(e);
		}
		
		
		
		

		
		
		
//		String query = "SELECT * FROM [JinengoOperationalCRM_Copy].[dbo].[temprouts] WHERE userID =123";
//
//    	ResultSet res = MSSQLConnection.selectSomething(query);
//    	
//    	try{
//			while (res.next())
//	        {			
//				System.out.println("UserID: "+res.getInt("UserID")+" - RequestID: "+res.getInt("RequestID")+" - TripID: "+res.getInt("TripID"));
//	        }
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
	}

}
